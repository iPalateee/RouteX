package it.web.routex.boundary.cli.controller.grafico;

import it.web.routex.bean.CityBean;
import it.web.routex.bean.PrezzoTotaleBean;
import it.web.routex.boundary.cli.LoggedCLI;
import it.web.routex.boundary.cli.extractor.BuyTicketExtractorCLI;
import it.web.routex.boundary.cli.view.BuyTicketCLI;
import it.web.routex.boundary.cli.view.ConfermaPagamentoCLI;
import it.web.routex.boundary.cli.view.GenericErrorCLI;
import it.web.routex.controller.applicativo.CityController;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.InvalidBuyTicketInputExceptionRemoli;
import it.web.routex.exception.InvalidCityDataExceptionBrondi;
import it.web.routex.exception.InvalidPriceCalculationExceptionRemoli;
import it.web.routex.record.BuyTicketRecord;

import java.util.List;

public class BuyTicketControllerGraficoCLI extends LoggedCLI
{
    public void doGet()
    {

        try {
            CityController cityController = new CityController();
            List<CityBean> cities = cityController.getAllCities();

            forwardToBuyTicket(cities);

        } catch (DAOExceptionBrondi e) {
            GenericErrorCLI.mostraErrore(e.getMessage());
        } catch (InvalidCityDataExceptionBrondi e) {
            GenericErrorCLI.mostraErrore(e.getUserMessage());
            logger.error("Errore nei dati delle città: {}", e.toString());
        }

    }

    public void doPost(String city, String quantity) {

        BuyTicketRecord buyTicket = estraiBuyTicket(city,quantity);

        if (buyTicket == null) {
            logger.warn("Impossibile estrarre i dati del biglietto: city='{}', quantity='{}'", city, quantity);
            GenericErrorCLI.mostraErrore("I dati inseriti per l'acquisto non sono validi.");
            return;
        }

        logger.info("Elaborazione richiesta acquisto biglietti per città='{}', quantità={}",
                buyTicket.city(), buyTicket.quantity());
        try {
            CityController cityController = new CityController();
            PrezzoTotaleBean prezzo = cityController.ottieniPrezzoTotale(buyTicket.city(), buyTicket.quantity());
            logger.info("Elaborazione prezzo: prezzo={}",prezzo.getPrezzoTotale());

            ConfermaPagamentoCLI.init(buyTicket.city(),String.valueOf(buyTicket.quantity()),prezzo.getPrezzoTotale());
            forwardingConferma();

        } catch (DAOExceptionBrondi e) {
            GenericErrorCLI.mostraErrore("Errore durante l'elaborazione dell'acquisto: " + e.getMessage());
            logger.error("Errore nella DAO {}. ", e.getMessage());
        } catch (InvalidPriceCalculationExceptionRemoli e) {
            GenericErrorCLI.mostraErrore("Errore nei dati inseriti " + e.getUserMessage());
        }
    }

    private BuyTicketRecord estraiBuyTicket(String city, String quantity)
    {
        try {
            return BuyTicketExtractorCLI.from(city,quantity);
        } catch (InvalidBuyTicketInputExceptionRemoli e) {
            logger.error("Errore di validazione input nell'acquisto biglietti", e);
            GenericErrorCLI.mostraErrore("Errore di validazione input nell'acquisto biglietti" + e.getUserMessage());
            return null;
        }
    }
    private void forwardingConferma()
    {
        ConfermaPagamentoCLI.stampa();
    }

    private void forwardToBuyTicket(List<CityBean> cities) {
        try {
            BuyTicketCLI.mostraAcquisto(cities);
            logger.info("[CLI]Visualizzata la pagina di acquisto biglietti con size={} città disponibili.", cities.size());
        } catch (Exception e) {
            logger.error("[CLI]Errore nella visualizzazione della pagina di acquisto biglietti.", e);
        }
    }
}
