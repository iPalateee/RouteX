package it.web.routex.controller.grafico;

import it.web.routex.bean.CityBean;
import it.web.routex.bean.PaymentResultBean;
import it.web.routex.bean.PrezzoTotaleBean;
import it.web.routex.controller.applicativo.CityController;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.domain.LoggedHttpServlet;
import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;
import it.web.routex.record.BuyTicketRecord;
import it.web.routex.exception.InvalidPriceCalculationExceptionBrondi;
import it.web.routex.exception.InvalidCityDataExceptionBrondi;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.List;

/**
 * Controller grafico per la gestione del flusso "Buy Ticket".
 * Gestisce sia la visualizzazione della pagina di acquisto (GET)
 * che l'elaborazione dei dati di acquisto (POST).
 * @author Lorenzo Brondi
 */
@WebServlet("/buyTicket")
public class BuyTicketControllerGrafico extends LoggedHttpServlet {

    private static final String ATTR_ERRORE = "errore";
    private static final String PAGE_ERROR = "/error.jsp";
    private static final String MSG_ERR_FORWARD = "Errore durante il forward alla pagina di errore";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            CityController cityController = new CityController();
            List<CityBean> cities = cityController.getAllCities();

            request.setAttribute("cities", cities);
            forwardToBuyTicket(request, response, cities.size());

        } catch (DAOExceptionBrondi e) {
            request.setAttribute(ATTR_ERRORE, "Errore nel caricamento delle città: " + e.getMessage());
            try {
                request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
            } catch (Exception forwardEx) {
                logger.error(MSG_ERR_FORWARD, forwardEx);
            }
        } catch (InvalidCityDataExceptionBrondi e) {
            logger.error("Errore nei dati delle città: {}", e.getMessage(), e);
            request.setAttribute(ATTR_ERRORE, e.getUserMessage());
            try {
                request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
            } catch (Exception forwardEx) {
                logger.error(MSG_ERR_FORWARD, forwardEx);
            }
        }
    }

    /**
     * Gestisce la richiesta di acquisto di uno o più biglietti.
     * Calcola il prezzo totale e inoltra alla pagina di conferma pagamento.
     * @author Lorenzo Brondi
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        final HttpSession session = request.getSession(false);
        if (session == null) {
            try {
                response.sendRedirect("login.jsp");
            } catch (Exception e) {
                logger.error("Errore durante il redirect alla login", e);
            }
            return;
        }

        BuyTicketRecord buyTicket = estraiBuyTicket(request, response);

        if (buyTicket == null) {
            logger.warn("Impossibile procedere: l'estrazione di buyTicket ha restituito null.");
            return;
        }

        logger.info("Elaborazione richiesta acquisto biglietti per città='{}', quantità={}",
                buyTicket.city(), buyTicket.quantity());

        try {
            CityController cityController = new CityController();
            PrezzoTotaleBean prezzo = cityController.ottieniPrezzoTotale(buyTicket.city(), buyTicket.quantity());

            logger.info("Elaborazione prezzo conclusa: prezzo={}", prezzo.getPrezzoTotale());

            request.setAttribute("city", buyTicket.city());
            request.setAttribute("quantity", String.valueOf(buyTicket.quantity()));
            request.setAttribute("prezzo", prezzo.getPrezzoTotale());

            forwardingConferma(request, response);

        } catch (DAOExceptionBrondi e) {
            logger.error("Errore nella DAO. Messaggio: {}", e.getMessage(), e);
            request.setAttribute(ATTR_ERRORE, "Errore durante l'elaborazione dell'acquisto: " + e.getMessage());
            try {
                request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
            } catch (Exception forwardEx) {
                logger.error(MSG_ERR_FORWARD, forwardEx);
            }
        } catch (InvalidPriceCalculationExceptionBrondi e) {
            logger.error("Errore nei dati inseriti: {}", e.getMessage(), e);
            request.setAttribute(ATTR_ERRORE, e.getUserMessage());
            try {
                request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
            } catch (Exception forwardEx) {
                logger.error(MSG_ERR_FORWARD, forwardEx);
            }
        }
    }

    private void forwardToBuyTicket(HttpServletRequest request, HttpServletResponse response, int size) {
        try {
            request.getRequestDispatcher("buyTicket.jsp").forward(request, response);
            logger.info("Visualizzata la pagina di acquisto biglietti con size={} città disponibili.", size);
        } catch (Exception e) {
            logger.error("Errore nella visualizzazione della pagina di acquisto biglietti.", e);
        }
    }

    private BuyTicketRecord estraiBuyTicket(HttpServletRequest request, HttpServletResponse response) {
        try {
            PaymentResultBean prb = new PaymentResultBean();

            prb.setCity(request.getParameter("city"));
            prb.setQuantity(request.getParameter("quantity"));

            return new BuyTicketRecord(prb.getCity(), prb.getQuantity());

        } catch (InvalidBuyTicketInputExceptionBrondi e) {
            logger.error("Errore di validazione input nell'acquisto biglietti", e);
            request.setAttribute(ATTR_ERRORE, e.getUserMessage());
            try {
                request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
            } catch (Exception forwardEx) {
                logger.error(MSG_ERR_FORWARD, forwardEx);
            }
            return null;
        }
    }

    private void forwardingConferma(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/confermaPagamento.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Errore durante il forward alla pagina di conferma pagamento", e);
        }
    }
}