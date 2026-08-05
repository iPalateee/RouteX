package it.web.routex.boundary.cli.controller.grafico;

import it.web.routex.bean.CityBean;
import it.web.routex.bean.PrezzoTotaleBean;
import it.web.routex.bean.TicketBean;
import it.web.routex.boundary.cli.LoggedCLI;
import it.web.routex.boundary.cli.view.BuyTicketCLI;
import it.web.routex.boundary.cli.view.ConfermaPagamentoCLI;
import it.web.routex.boundary.cli.view.GenericErrorCLI;
import it.web.routex.controller.applicativo.BuyTicketControllerApplicativo;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;
import it.web.routex.exception.InvalidCityDataExceptionBrondi;
import it.web.routex.exception.InvalidPriceCalculationExceptionBrondi;

import java.util.List;

public class BuyTicketControllerGraficoCLI extends LoggedCLI
{
    public void doGet()
    {
        try {
            BuyTicketControllerApplicativo buyTicketControllerApplicativo = new BuyTicketControllerApplicativo();
            List<CityBean> cities = buyTicketControllerApplicativo.getAllCities();

            try {
                BuyTicketCLI.mostraAcquisto(cities);
                logger.info("[CLI]Visualizzata la pagina di acquisto biglietti con size={} città disponibili.", cities.size());
            } catch (Exception e) {
                logger.error("[CLI]Errore nella visualizzazione della pagina di acquisto biglietti.", e);
            }

        } catch (DAOExceptionBrondi e) {
            GenericErrorCLI.mostraErrore(e.getMessage());
        } catch (InvalidCityDataExceptionBrondi e) {
            GenericErrorCLI.mostraErrore(e.getUserMessage());
            logger.error("Errore nei dati delle città: {}", e.toString());
        }
    }

    public void doPost(String city, String quantity) {

        TicketBean ticket = estraiBuyTicket(city, quantity);

        if (ticket == null) {
            logger.warn("Impossibile estrarre i dati del biglietto: city='{}', quantity='{}'", city, quantity);
            GenericErrorCLI.mostraErrore("I dati inseriti per l'acquisto non sono validi.");
            return;
        }

        logger.info("Elaborazione richiesta acquisto biglietti per città='{}', quantità={}",
                ticket.getCity(), ticket.getQuantity());
        try {
            BuyTicketControllerApplicativo buyTicketControllerApplicativo = new BuyTicketControllerApplicativo();
            PrezzoTotaleBean prezzo = buyTicketControllerApplicativo.ottieniPrezzoTotale(ticket);
            logger.info("Elaborazione prezzo: prezzo={}", prezzo.getPrezzoTotale());

            ConfermaPagamentoCLI.init(ticket.getCity(), String.valueOf(ticket.getQuantity()), prezzo.getPrezzoTotale());
            ConfermaPagamentoCLI.stampa();

        } catch (DAOExceptionBrondi e) {
            GenericErrorCLI.mostraErrore("Errore durante l'elaborazione dell'acquisto: " + e.getMessage());
            logger.error("Errore nella DAO {}. ", e.getMessage());
        } catch (InvalidPriceCalculationExceptionBrondi e) {
            GenericErrorCLI.mostraErrore("Errore nei dati inseriti " + e.getUserMessage());
        }
    }

    private TicketBean estraiBuyTicket(String city, String quantity)
    {
        try {
            TicketBean ticket = new TicketBean();
            ticket.setCity(city);
            ticket.setQuantity(quantity);
            return ticket;

        } catch (InvalidBuyTicketInputExceptionBrondi e) {
            logger.error("Errore di validazione input nell'acquisto biglietti", e);
            GenericErrorCLI.mostraErrore("Errore di validazione input nell'acquisto biglietti" + e.getUserMessage());
            return null;
        }
    }

}