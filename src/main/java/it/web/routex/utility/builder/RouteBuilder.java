package it.web.routex.utility.builder;

import it.web.routex.utility.builder.data.RouteData;
import it.web.routex.model.Route;

public class RouteBuilder {

    private final RouteData data = new RouteData();

    public RouteBuilder(String startStation) {
        data.setStartStation(startStation);
    }

    public RouteBuilder endStation(String endStation) {
        data.setEndStation(endStation);
        return this;
    }

    public RouteBuilder city(String city) {
        data.setCity(city);
        return this;
    }

    public RouteBuilder tipoViaggiatore(String tipoViaggiatore) {
        data.setTipoViaggiatore(tipoViaggiatore);
        return this;
    }

    public RouteBuilder nCambi(int nCambi) {
        data.setnCambi(nCambi);
        return this;
    }

    public RouteBuilder listaCambi(String listaCambi) {
        data.setListaCambi(listaCambi);
        return this;
    }

    public RouteBuilder stazioneDiInterscambio(String stazioneDiInterscambio) {
        data.setStazioneDiInterscambio(stazioneDiInterscambio);
        return this;
    }

    public RouteBuilder nStazioniAttraversate(int nStazioniAttraversate) {
        data.setnStazioniAttraversate(nStazioniAttraversate);
        return this;
    }

    public RouteBuilder tempoDiArrivo(double tempoDiArrivo) {
        data.setTempoDiArrivo(tempoDiArrivo);
        return this;
    }

    public RouteBuilder nStazioniCitta(int nStazioniCitta) {
        data.setnStazioniCitta(nStazioniCitta);
        return this;
    }

    public RouteBuilder percTerrenoUtilizzato(double percTerrenoUtilizzato) {
        data.setPercTerrenoUtilizzato(percTerrenoUtilizzato);
        return this;
    }

    public RouteBuilder utente(String utente) {
        data.setUtente(utente);
        return this;
    }

    public Route build() {
        return new Route(data);
    }
}