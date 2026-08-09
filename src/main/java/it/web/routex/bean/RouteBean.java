package it.web.routex.bean;

import it.web.routex.exception.InvalidRouteInputExceptionRemoli;
import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;

import static it.web.routex.utility.text.TextUtils.sanitize;
import static it.web.routex.utility.text.TextUtils.validateCitySyntax;

public class RouteBean {

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

    public String getCitta() { return citta; }
    public Double getPercTerrenoUtilizzato() { return percTerrenoUtilizzato; }
    public Double getTempoDiArrivo() { return tempoDiArrivo; }
    public int getnCambi() { return nCambi; }
    public int getnStazAttraversate() { return nStazAttraversate; }
    public int getnStazioniCitta() { return nStazioniCitta; }
    public String getArrivo() { return arrivo; }
    public String getListaCambi() { return listaCambi; }
    public String getPartenza() { return partenza; }
    public String getStazInterscambio() { return stazInterscambio; }

    public void setArrivo(String a) throws InvalidRouteInputExceptionRemoli {
        String arrivoo = sanitize(a);
        if (arrivoo == null || arrivoo.isEmpty()) {
            throw new InvalidRouteInputExceptionRemoli("arrivo", "Il parametro di arrivo è mancante o vuoto.");
        }
        this.arrivo = arrivoo;
    }

    public void setCitta(String citta) throws InvalidBuyTicketInputExceptionBrondi {
        this.citta = validateCitySyntax(citta);
    }

    public void setPartenza(String p) throws InvalidRouteInputExceptionRemoli {
        String partenzaa = sanitize(p);
        if (partenzaa == null || partenzaa.isEmpty()) {
            throw new InvalidRouteInputExceptionRemoli("partenza", "Il parametro di partenza è mancante o vuoto.");
        }
        this.partenza = partenzaa;
    }

    public void setListaCambi(String rawListaCambi) throws InvalidRouteInputExceptionRemoli {
        String pulito = sanitize(rawListaCambi);
        if (pulito == null) {
            throw new InvalidRouteInputExceptionRemoli("listaCambi", "La lista cambi non può essere nulla (al massimo stringa vuota).");
        }
        this.listaCambi = pulito;
    }

    public void setStazInterscambio(String rawStazInterscambio) throws InvalidRouteInputExceptionRemoli {
        String pulito = sanitize(rawStazInterscambio);
        if (pulito == null) {
            throw new InvalidRouteInputExceptionRemoli("stazInterscambio", "La stazione di interscambio non può essere nulla.");
        }
        this.stazInterscambio = pulito;
    }

    public void setnCambi(int nCambi) throws InvalidRouteInputExceptionRemoli {
        if (nCambi < 0) {
            throw new InvalidRouteInputExceptionRemoli("nCambi", "Il numero di cambi non può essere negativo.");
        }
        this.nCambi = nCambi;
    }

    public void setnStazAttraversate(int nStazAttraversate) throws InvalidRouteInputExceptionRemoli {
        if (nStazAttraversate < 0) {
            throw new InvalidRouteInputExceptionRemoli("nStazAttraversate", "Il numero di stazioni attraversate non può essere negativo.");
        }
        this.nStazAttraversate = nStazAttraversate;
    }

    public void setnStazioniCitta(int nStazioniCitta) throws InvalidRouteInputExceptionRemoli {
        if (nStazioniCitta < 0) {
            throw new InvalidRouteInputExceptionRemoli("nStazioniCitta", "Il numero di stazioni in città non può essere negativo.");
        }
        this.nStazioniCitta = nStazioniCitta;
    }

    public void setTempoDiArrivo(Double tempoDiArrivo) throws InvalidRouteInputExceptionRemoli {
        if (tempoDiArrivo == null) {
            throw new InvalidRouteInputExceptionRemoli("tempoDiArrivo", "Il tempo di arrivo non può essere nullo.");
        }
        if (tempoDiArrivo < 0) {
            throw new InvalidRouteInputExceptionRemoli("tempoDiArrivo", "Il tempo di arrivo non può essere negativo.");
        }
        this.tempoDiArrivo = tempoDiArrivo;
    }

    public void setPercTerrenoUtilizzato(Double percTerrenoUtilizzato) throws InvalidRouteInputExceptionRemoli {
        if (percTerrenoUtilizzato == null) {
            throw new InvalidRouteInputExceptionRemoli("percTerrenoUtilizzato", "La percentuale non può essere nulla.");
        }
        if (percTerrenoUtilizzato < 0 || percTerrenoUtilizzato > 100) {
            throw new InvalidRouteInputExceptionRemoli("percTerrenoUtilizzato", "La percentuale di terreno utilizzato deve essere tra 0 e 100.");
        }
        this.percTerrenoUtilizzato = percTerrenoUtilizzato;
    }
}