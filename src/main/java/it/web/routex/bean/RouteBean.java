package it.web.routex.bean;

import it.web.routex.exception.InvalidRouteInputExceptionRemoli;

import static it.web.routex.utility.text.TextUtils.sanitize;

public class RouteBean
{
    private String partenza;
    private String arrivo;
    private String citta;
    private int nCambi;
    private String listaCambi;
    private String stazInterscambio;
    private int nStazAttraversate;
    private Double tempoDiArrivo;
    private int nStazioniCitta;
    private Double percTerrenoUtilizzato;

    public String getCitta() {
        return citta;
    }

    public Double getPercTerrenoUtilizzato() {
        return percTerrenoUtilizzato;
    }

    public Double getTempoDiArrivo() {
        return tempoDiArrivo;
    }

    public int getnCambi() {
        return nCambi;
    }

    public int getnStazAttraversate() {
        return nStazAttraversate;
    }

    public int getnStazioniCitta() {
        return nStazioniCitta;
    }

    public String getArrivo() {
        return arrivo;
    }

    public String getListaCambi() {
        return listaCambi;
    }

    public String getPartenza() {
        return partenza;
    }

    public String getStazInterscambio() {
        return stazInterscambio;
    }

    public void setArrivo(String a) throws InvalidRouteInputExceptionRemoli {

        String arrivo = sanitize(a);

        if (arrivo == null || arrivo.isEmpty())
            throw new InvalidRouteInputExceptionRemoli("city", "City parameter is missing");


        this.arrivo = arrivo;
    }

    public void setCitta(String citta) throws InvalidRouteInputExceptionRemoli {
        String city = sanitize(citta);

        if (city == null || city.isEmpty())
            throw new InvalidRouteInputExceptionRemoli("city", "City parameter is missing");

        this.citta = city;
    }

    public void setListaCambi(String listaCambi) {
        this.listaCambi = listaCambi;
    }

    public void setnCambi(int nCambi) {
        this.nCambi = nCambi;
    }

    public void setnStazAttraversate(int nStazAttraversate) {
        this.nStazAttraversate = nStazAttraversate;
    }

    public void setPartenza(String p) throws InvalidRouteInputExceptionRemoli {

        String partenza = sanitize(p);

        if (partenza == null || partenza.isEmpty())
            throw new InvalidRouteInputExceptionRemoli("city", "City parameter is missing");

        this.partenza = partenza;
    }

    public void setPercTerrenoUtilizzato(Double percTerrenoUtilizzato) {
        this.percTerrenoUtilizzato = percTerrenoUtilizzato;
    }

    public void setStazInterscambio(String stazInterscambio) {
        this.stazInterscambio = stazInterscambio;
    }

    public void setnStazioniCitta(int nStazioniCitta) {
        this.nStazioniCitta = nStazioniCitta;
    }

    public void setTempoDiArrivo(Double tempoDiArrivo) {
        this.tempoDiArrivo = tempoDiArrivo;
    }
}
