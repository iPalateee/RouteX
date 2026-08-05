package it.web.routex.controller.applicativo;

import it.web.routex.bean.CityBean;
import it.web.routex.bean.PrezzoTotaleBean;
import it.web.routex.bean.TicketBean;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.InvalidCityDataExceptionBrondi;
import it.web.routex.exception.InvalidPriceCalculationExceptionBrondi;
import it.web.routex.model.City;
import it.web.routex.utility.factory.LayerPersistenza;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller applicativo responsabile della logica legata alle città.
 * Si occupa sia del recupero delle informazioni dal DAO che del calcolo del prezzo totale.
 */
public class BuyTicketControllerApplicativo {

    public List<CityBean> getAllCities() throws InvalidCityDataExceptionBrondi, DAOExceptionBrondi {

        it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
        List<City> cities = layer.listCitiesRAM();

        if (cities == null) {
            throw new InvalidCityDataExceptionBrondi(
                    "Nessun dato disponibile al momento.",
                    "DAO ha restituito null nella lista delle città.",
                    InvalidCityDataExceptionBrondi.Severity.CRITICAL
            );
        }

        if (cities.isEmpty()) {
            throw new InvalidCityDataExceptionBrondi(
                    "Nessuna città disponibile per l'acquisto al momento.",
                    "La lista delle città è vuota.",
                    InvalidCityDataExceptionBrondi.Severity.MEDIUM
            );
        }

        for (City c : cities) {
            if (c == null || !c.isValid()) {
                throw new InvalidCityDataExceptionBrondi(
                        "Sono stati trovati dati città non validi.",
                        "Oggetto City non valido secondo il dominio.",
                        InvalidCityDataExceptionBrondi.Severity.HIGH
                );
            }
        }

        List<CityBean> cityBeans = new ArrayList<>();
        for (City c : cities) {
            cityBeans.add(new CityBean(c));
        }

        return cityBeans;
    }

    public PrezzoTotaleBean ottieniPrezzoTotale(TicketBean ticket)
            throws DAOExceptionBrondi, InvalidPriceCalculationExceptionBrondi {

        it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
        City city = layer.getCityByName(ticket.getCity());

        if (city == null) {
            throw new InvalidPriceCalculationExceptionBrondi(
                    "La città selezionata non è disponibile.",
                    "Nessuna corrispondenza per city='" + ticket.getCity() + "'",
                    InvalidPriceCalculationExceptionBrondi.Severity.HIGH
            );
        }

        double totale = city.calcolaPrezzoTotale(ticket.getQuantity());
        return new PrezzoTotaleBean(totale);
    }
}