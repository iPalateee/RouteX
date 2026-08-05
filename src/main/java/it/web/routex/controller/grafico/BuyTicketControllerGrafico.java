package it.web.routex.controller.grafico;

import it.web.routex.bean.CityBean;
import it.web.routex.bean.PrezzoTotaleBean;
import it.web.routex.bean.TicketBean;
import it.web.routex.controller.applicativo.BuyTicketControllerApplicativo;
import it.web.routex.domain.LoggedHttpServlet;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;
import it.web.routex.exception.InvalidCityDataExceptionBrondi;
import it.web.routex.exception.InvalidPriceCalculationExceptionBrondi;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Controller grafico per la gestione del caso d'uso "Buy Ticket".
 * Gestisce sia la visualizzazione della pagina di acquisto (GET)
 * che l'elaborazione dei dati di acquisto (POST).
 */
@WebServlet("/buyTicket")
public class BuyTicketControllerGrafico extends LoggedHttpServlet {

    private static final String ATTR_ERRORE = "errore";
    private static final String PAGE_ERROR = "/error.jsp";
    private static final String MSG_ERR_FORWARD = "Errore durante il forward alla pagina di errore";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            BuyTicketControllerApplicativo buyTicketControllerApplicativo = new BuyTicketControllerApplicativo();
            List<CityBean> cities = buyTicketControllerApplicativo.getAllCities();

            request.setAttribute("cities", cities);

            request.getRequestDispatcher("buyTicket.jsp").forward(request, response);

            logger.info("Visualizzata la pagina di acquisto biglietti con size={} città disponibili.", cities.size());

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
        } catch (Exception e) {
            logger.error("Errore nella visualizzazione della pagina di acquisto biglietti.", e);
        }
    }

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

        try {
            BuyTicketControllerApplicativo buyTicketControllerApplicativo = new BuyTicketControllerApplicativo();
            TicketBean ticket = new TicketBean();

            ticket.setCity(request.getParameter("city"));
            ticket.setQuantity(request.getParameter("quantity"));

            PrezzoTotaleBean prezzo = buyTicketControllerApplicativo.ottieniPrezzoTotale(ticket);

            request.setAttribute("city", ticket.getCity());
            request.setAttribute("quantity", String.valueOf(ticket.getQuantity()));
            request.setAttribute("prezzo", prezzo.getPrezzoTotale());

            request.getRequestDispatcher("/confermaPagamento.jsp").forward(request, response);

        } catch (InvalidBuyTicketInputExceptionBrondi e) {
            logger.error("Errore di validazione input nell'acquisto biglietti", e);
            request.setAttribute(ATTR_ERRORE, e.getUserMessage());
            try {
                request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
            } catch (Exception forwardEx) {
                logger.error(MSG_ERR_FORWARD, forwardEx);
            }
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
        } catch (Exception e) {
            logger.error("Errore durante il forward alla pagina di conferma pagamento", e);
        }
    }

}