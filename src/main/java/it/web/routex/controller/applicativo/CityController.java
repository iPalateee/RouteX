package it.web.routex.controller.applicativo;
import it.web.routex.bean.CityBean;
import it.web.routex.bean.PrezzoTotaleBean;
import it.web.routex.model.City;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.InvalidPriceCalculationExceptionRemoli;
import it.web.routex.exception.InvalidCityDataExceptionBrondi;
import it.web.routex.utility.factory.LayerPersistenza;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller applicativo responsabile della logica legata alle città.
 * Si occupa sia del recupero delle informazioni dal DAO che del calcolo del prezzo totale.
 * @author Lorenzo Brondi
 */
public class CityController {

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


    /**
     * Calcola il prezzo totale per l'acquisto di uno o più biglietti
     * in base alla città selezionata e alla quantità indicata.
     *
     * @param city     nome della città selezionata
     * @param quantity quantità di biglietti richiesta
     * @return Bean contenente il prezzo totale
     * @throws DAOExceptionBrondi in caso di errore logico o di accesso ai dati
     */
    public PrezzoTotaleBean ottieniPrezzoTotale(String city, int quantity) throws DAOExceptionBrondi, InvalidPriceCalculationExceptionRemoli {

        it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
        List<City> cities = layer.listCitiesRAM();
        for (City a : cities) {
            if (a.getName().equalsIgnoreCase(city)) {
                double totale = a.calcolaPrezzoTotale(quantity);
                return new PrezzoTotaleBean(totale);
            }
        }
        throw new InvalidPriceCalculationExceptionRemoli(
                "La città selezionata non è disponibile nel database.",
                "Nessuna corrispondenza per la city='" + city + "'",
                InvalidPriceCalculationExceptionRemoli.Severity.HIGH
        );
    }
}
