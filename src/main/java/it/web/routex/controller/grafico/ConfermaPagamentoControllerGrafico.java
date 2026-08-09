package it.web.routex.controller.grafico;

import it.web.routex.bean.PaymentResultBean;
import it.web.routex.controller.applicativo.PagamentoControllerApplicativo;
import it.web.routex.domain.LoggedHttpServlet;
import it.web.routex.exception.*;
import it.web.routex.utility.builder.PaymentBuilder;
import it.web.routex.utility.singleton.Credentials;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/confermaPagamento")
public class ConfermaPagamentoControllerGrafico extends LoggedHttpServlet {
    private static final String ATTR_MESSAGGIO_ERRORE = "messaggioErrore";
    private static final String PAGE_ERRORE_PAGAMENTO = "/errorePagamento.jsp";
    private static final String FORWARDING = "Errore nel forwarding";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        if (!verificaSessione(session, response)) return;

        Credentials cred = Credentials.getInstanceSingleton();

        logger.info("[PROCESSAMENTO PAGAMENTO] Utente loggato: nome={}, cognome={}, ruolo={}",
                cred.getNome(), cred.getCognome(), cred.getRuolo());

        try {

            PaymentResultBean payment = costruisciBean(request);

            PagamentoControllerApplicativo pagamento = new PagamentoControllerApplicativo();
            PaymentResultBean result = pagamento.eseguiPagamento(payment);

            mostraSuccesso(request, response, result);

        } catch (InvalidPaymentInputExceptionBrondi e) {
            logger.error("Errore nell'input del pagamento: {}", e.toString());
            gestisciErrore(request, response, e.getUserMessage());

        } catch (InvalidBuyTicketInputExceptionBrondi | InvalidCardInputExceptionBrondi e) {
            logger.error("Errore di validazione input: {}", e.getMessage());
            gestisciErrore(request, response, e.getMessage());

        } catch (PaymentValidationExceptionBrondi | DAOExceptionBrondi | CredentialsExceptionBrondi e) {
            logger.error("Errore durante il pagamento", e);
            gestisciErrore(request, response, e.getMessage());

        } catch (Exception e) {
            logger.error("Errore generico conferma pagamento", e);
            gestisciErrore(request, response, e.getMessage());
        }
    }


    private PaymentResultBean costruisciBean(HttpServletRequest request)
            throws InvalidPaymentInputExceptionBrondi, InvalidBuyTicketInputExceptionBrondi, InvalidCardInputExceptionBrondi {

        return new PaymentBuilder()
                .withCity(request.getParameter("city"))
                .withQuantity(request.getParameter("quantity"))
                .withTotale(request.getParameter("totale"))
                .withMetodoPagamento(request.getParameter("metodoPagamento"))
                .withPersistenza(request.getParameter("persistence"))
                .withNumeroCarta(request.getParameter("numeroCarta"))
                .withScadenzaCarta(request.getParameter("scadenza"))
                .withCvvCarta(request.getParameter("cvv"))
                .withEmailPaypal(request.getParameter("emailPaypal"))
                .withCodicePaypal(request.getParameter("codiceTransazione"))
                .build();

    }

    private boolean verificaSessione(HttpSession session, HttpServletResponse response) {
        if (session == null) {
            try {
                response.sendRedirect("login.jsp");
            } catch (IOException e) {
                logger.error("Errore durante il redirect", e);
            }
            return false;
        }
        return true;
    }

    private void mostraSuccesso(HttpServletRequest request, HttpServletResponse response, PaymentResultBean pay) {
        request.setAttribute("city", pay.getCity());
        request.setAttribute("quantity", String.valueOf(pay.getQuantity()));
        request.setAttribute("totale", pay.getTotal());
        request.setAttribute("metodo", pay.getPaymentMethod());
        request.setAttribute("messaggio", "Pagamento completato");
        request.setAttribute("codiciBiglietti", pay.getTicketCodes());

        try {
            request.getRequestDispatcher("/successoPagamento.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error(FORWARDING, e);
        }
    }

    private void gestisciErrore(HttpServletRequest request, HttpServletResponse response, String messaggio) {
        request.setAttribute(ATTR_MESSAGGIO_ERRORE, messaggio);
        try {
            request.getRequestDispatcher(PAGE_ERRORE_PAGAMENTO).forward(request, response);
        } catch (Exception ex) {
            logger.error(FORWARDING, ex);
        }
    }
}