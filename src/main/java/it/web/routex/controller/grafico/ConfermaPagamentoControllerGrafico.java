package it.web.routex.controller.grafico;
import it.web.routex.bean.MastercardBean;
import it.web.routex.bean.PaymentResultBean;
import it.web.routex.bean.PaypalBean;
import it.web.routex.controller.applicativo.PagamentoMastercard;
import it.web.routex.controller.applicativo.PagamentoPaypal;
import it.web.routex.controller.applicativo.RegistrazionePagamentoController;
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
        logUtente(cred);

        PaymentResultBean paymentRecord = estraiPagamento(request, response);
        if (paymentRecord == null) return;

        RegistrazionePagamentoController controllerPagamento = creaControllerPagamento(paymentRecord, request, response, cred);

        if (controllerPagamento == null) return;

        PaymentResultBean result = eseguiPagamento(controllerPagamento, request, response);

        if (result == null) return;

        mostraSuccesso(request, response, result);
    }

    private PaymentResultBean estraiPagamento(HttpServletRequest request, HttpServletResponse response) {
        try {

            return new PaymentBuilder()
                    .withCity(request.getParameter("city"))
                    .withQuantity(request.getParameter("quantity"))
                    .withTotale(request.getParameter("totale"))
                    .withMetodoPagamento(request.getParameter("metodoPagamento"))
                    .withPersistenza(request.getParameter("persistence"))
                    .build();

        } catch (InvalidPaymentInputExceptionBrondi e) {
            logger.error("Errore nell'input del pagamento: {}", e.toString());
            request.setAttribute(ATTR_MESSAGGIO_ERRORE, e.getUserMessage());
            try {
                request.getRequestDispatcher(PAGE_ERRORE_PAGAMENTO).forward(request, response);
            } catch (Exception a) {
                logger.error("Errore durante il forward alla pagina di errore", a);
            }
            return null;
        } catch (InvalidBuyTicketInputExceptionBrondi | InvalidCardInputExceptionBrondi e) {

            logger.error("Errore di validazione input: {}", e.getMessage());
            request.setAttribute(ATTR_MESSAGGIO_ERRORE, e.getMessage()); // o e.getUserMessage() se lo hai implementato
            try {
                request.getRequestDispatcher(PAGE_ERRORE_PAGAMENTO).forward(request, response);
            } catch (Exception a) {
                logger.error("Errore durante il forward alla pagina di errore", a);
            }
            return null;
        }
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
    private void logUtente(Credentials cred) {
        logger.info("[PROCESSAMENTO PAGAMENTO] Utente loggato: nome={}, cognome={}, ruolo={}",
                cred.getNome(), cred.getCognome(), cred.getRuolo());
    }

    private RegistrazionePagamentoController creaControllerPagamento(
            PaymentResultBean paymentRecord,
            HttpServletRequest request,
            HttpServletResponse response,
            Credentials cred) {

        String metodo = paymentRecord.getPaymentMethod().toLowerCase();

        return switch (metodo) {
            case "mastercard" -> creaPagamentoMastercard(paymentRecord, request, response, cred);
            case "paypal"     -> creaPagamentoPaypal(paymentRecord, request, response, cred);
            default           -> gestisciMetodoNonValido(response);
        };
    }
    private RegistrazionePagamentoController creaPagamentoMastercard(
            PaymentResultBean paymentRecord,
            HttpServletRequest request,
            HttpServletResponse response,
            Credentials cred) {

        try {

            String rawNumero = request.getParameter("numeroCarta");
            String rawScadenza = request.getParameter("scadenza");
            String rawCvv = request.getParameter("cvv");

            MastercardBean mb = new MastercardBean();

            mb.setNumero(rawNumero);
            mb.setScadenza(rawScadenza);
            mb.setCvv(rawCvv);

            return new PagamentoMastercard(
                    mb.getNumero(),
                    mb.getScadenza(),
                    mb.getCvv(),
                    cred,
                    paymentRecord.getTotal(),
                    paymentRecord.getQuantity(),
                    paymentRecord.getCity()
            );
        } catch (InvalidCardInputExceptionBrondi e) {
            gestisciErroreInput(request, response, e.getUserMessage(), "Errore nei dati Mastercard", e);
            return null;
        }
    }
    private RegistrazionePagamentoController creaPagamentoPaypal(
            PaymentResultBean paymentRecord,
            HttpServletRequest request,
            HttpServletResponse response,
            Credentials cred) {

        try {

            String email = request.getParameter("emailPaypal");
            String codice = request.getParameter("codiceTransazione");

            PaypalBean pb = new PaypalBean();

            pb.setEmail(email);
            pb.setCodice(codice);

            return new PagamentoPaypal(
                    pb.getEmail(),
                    pb.getCodice(),
                    cred,
                    paymentRecord.getTotal(),
                    paymentRecord.getQuantity(),
                    paymentRecord.getCity()
            );
        } catch (InvalidCardInputExceptionBrondi e) {
            gestisciErroreInput(request, response, e.getUserMessage(), "Errore nei dati Paypal", e);
            return null;
        }
    }
    private RegistrazionePagamentoController gestisciMetodoNonValido(HttpServletResponse response) {
        try {
            response.sendRedirect("errorePagamento.jsp");
        } catch (IOException e) {
            logger.error("Errore nel redirect metodo non valido", e);
        }
        return null;
    }
    private PaymentResultBean eseguiPagamento(
            RegistrazionePagamentoController controllerPagamento,
            HttpServletRequest request,
            HttpServletResponse response) {

        try {
            return controllerPagamento.run();

        } catch (PaymentValidationExceptionBrondi | DAOExceptionBrondi | CredentialsExceptionBrondi e) {

            request.setAttribute(ATTR_MESSAGGIO_ERRORE, e.getMessage());
            try {
                request.getRequestDispatcher(PAGE_ERRORE_PAGAMENTO).forward(request, response);
            } catch (Exception ex) {
                logger.error(FORWARDING, ex);
            }

            logger.error("Errore durante il pagamento", e);
            return null;

        } catch (Exception e) {
            request.setAttribute(ATTR_MESSAGGIO_ERRORE, e.getMessage());
            try {
                request.getRequestDispatcher(PAGE_ERRORE_PAGAMENTO)
                        .forward(request, response);
            } catch (Exception ex) {
                logger.error(FORWARDING, ex);
            }

            logger.error("Errore generico conferma pagamento", e);
            return null;
        }
    }
    private void mostraSuccesso(
            HttpServletRequest request,
            HttpServletResponse response,
            PaymentResultBean pay) {

        request.setAttribute("city", pay.getCity());
        request.setAttribute("quantity", String.valueOf(pay.getQuantity()));
        request.setAttribute("totale", pay.getTotal());
        request.setAttribute("metodo", pay.getPaymentMethod());
        request.setAttribute("messaggio", "Pagamento completato");
        request.setAttribute("codiciBiglietti", pay.getTicketCodes());

        try {
            request.getRequestDispatcher("/successoPagamento.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            logger.error(FORWARDING, e);
        }
    }
    private void gestisciErroreInput(
            HttpServletRequest request,
            HttpServletResponse response,
            String messaggio,
            String log,
            Exception e) {

        logger.error(log, e);
        request.setAttribute(ATTR_MESSAGGIO_ERRORE, messaggio);

        try {
            request.getRequestDispatcher(PAGE_ERRORE_PAGAMENTO)
                    .forward(request, response);
        } catch (Exception ex) {
            logger.error(FORWARDING, ex);
        }
    }
}
