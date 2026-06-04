package it.web.routex.controller.grafico;

import it.web.routex.bean.CityBean;
import it.web.routex.bean.PrezzoTotaleBean;
import it.web.routex.controller.applicativo.CityController;
import it.web.routex.exception.DAOExceptionRemoli;
import it.web.routex.domain.LoggedHttpServlet;
import it.web.routex.extractor.BuyTicketExtractor;
import it.web.routex.record.BuyTicketRecord;
import it.web.routex.exception.InvalidBuyTicketInputExceptionRemoli;
import it.web.routex.exception.InvalidPriceCalculationExceptionRemoli;
import it.web.routex.exception.InvalidCityDataExceptionRemoli;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.List;

/**
 * Controller grafico per la gestione del flusso "Buy Ticket".
 * Gestisce sia la visualizzazione della pagina di acquisto (GET)
 * che l'elaborazione dei dati di acquisto (POST).
 * @SimoneRemoli
 */
@WebServlet("/buyTicket")
public class BuyTicketControllerGrafico extends LoggedHttpServlet {


    private static final String ERRORE = "errore";
    private static final String PAGE_ERROR = "error.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        String s = ERRORE;
        String pageErr = PAGE_ERROR;

            try {

                CityController cityController = new CityController();
                List<CityBean> cities = cityController.getAllCities();

                request.setAttribute("cities", cities);

                forwardToBuyTicket(request, response, cities.size());

            } catch (DAOExceptionRemoli e) {
                request.setAttribute(s, "Errore nel caricamento delle città: " + e.getMessage());
                try {
                    request.getRequestDispatcher(pageErr).forward(request, response);
                }catch(Exception a) {
                    logger.error("Errore nella presentazione della view il caricamento delle città: {}", a.toString());
                }
            } catch (InvalidCityDataExceptionRemoli e) {
                request.setAttribute(s, e.getUserMessage());
                try {
                    request.getRequestDispatcher(pageErr).forward(request, response);
                    logger.error("Errore nei dati delle città: {}", e.toString());
                }catch(Exception a) {
                    logger.error("errore nel forwarding");
                }
            }

    }

    /**
      Gestisce la richiesta di acquisto di uno o più biglietti.
      Calcola il prezzo totale e inoltra alla pagina di conferma pagamento.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

            BuyTicketRecord buyTicket;
            final HttpSession session = request.getSession(false);
            if (session == null) {
                try {
                    response.sendRedirect("login.jsp");
                }catch(Exception e){
                    logger.error("Errore durante il redirect", e);
                }
                return;
            }

            buyTicket = estraiBuyTicket(request, response);

            logger.info("Elaborazione richiesta acquisto biglietti per città='{}', quantità={}", buyTicket.city(), buyTicket.quantity());

            try {

                CityController cityController = new CityController();
                PrezzoTotaleBean prezzo = cityController.ottieniPrezzoTotale(buyTicket.city(), buyTicket.quantity());
                logger.info("Elaborazione prezzo: prezzo={}",prezzo.getPrezzoTotale());


                request.setAttribute("city", buyTicket.city());
                request.setAttribute("quantity", String.valueOf(buyTicket.quantity()));
                request.setAttribute("prezzo", prezzo.getPrezzoTotale());

                forwardingConferma(request, response);

            } catch (DAOExceptionRemoli e) {
                request.setAttribute(ERRORE, "Errore durante l'elaborazione dell'acquisto: " + e.getMessage());
                try {
                    request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
                }catch(Exception a){
                    logger.error("Errore nel forwarding",e);
                }
                logger.error("Errore nella DAO {}. ", e.getMessage());
            } catch (InvalidPriceCalculationExceptionRemoli e) {
                logger.error("Errore nei dati inseriti: {}", e.toString());
                request.setAttribute(ERRORE, e.getUserMessage());
                try {
                    request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
                }catch(Exception a) {
                    logger.error("Errore nel forwarding",e);
                }
            }
    }
    private void forwardToBuyTicket(HttpServletRequest request,
                                    HttpServletResponse response,
                                    int size) {
        try {
            request.getRequestDispatcher("buyTicket.jsp").forward(request, response);
            logger.info("Visualizzata la pagina di acquisto biglietti con size={} città disponibili.", size);
        } catch (Exception e) {
            logger.error("Errore nella visualizzazione della pagina di acquisto biglietti.", e);
        }
    }
    private BuyTicketRecord estraiBuyTicket(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            return BuyTicketExtractor.from(request);
        } catch (InvalidBuyTicketInputExceptionRemoli e) {
            logger.error("Errore di validazione input nell'acquisto biglietti", e);
            request.setAttribute(ERRORE, e.getUserMessage());
            try {
                request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
            }catch(Exception a) {
                logger.error("Errore durante il forward alla pagina di errore", a);
            }
            return null;
        }
    }
    private void forwardingConferma(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            request.getRequestDispatcher("/confermaPagamento.jsp").forward(request, response);
        }catch(Exception e){
            logger.error("Errore durante il redirect alla pagina di conferma pagamento",e);
        }
    }
}
