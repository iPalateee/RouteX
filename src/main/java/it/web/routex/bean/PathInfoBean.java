package it.web.routex.bean;

import it.web.routex.exception.InvalidRouteInputExceptionRemoli;
import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;

import static it.web.routex.utility.text.TextUtils.sanitize;
import static it.web.routex.utility.text.TextUtils.validateCitySyntax;

public class PathInfoBean {
    private String startStation;
    private String endStation;
    private String city;
    private String tipoViaggiatore;
    private String listaCambi;
    private String stazioneDiInterscambio;
    private String utente;
    private int nCambi;
    private int nStazioniAttraversate;
    private int nStazioniCitta;
    private double tempoDiArrivo;
    private double percTerrenoUtilizzato;

    public String getStartStation() { return startStation; }
    public String getEndStation() { return endStation; }
    public String getCity() { return city; }
    public String getTipoViaggiatore() { return tipoViaggiatore; }
    public int getNCambi() { return nCambi; }
    public String getListaCambi() { return listaCambi; }
    public String getStazioneDiInterscambio() { return stazioneDiInterscambio; }
    public int getNStazioniAttraversate() { return nStazioniAttraversate; }
    public double getTempoDiArrivo() { return tempoDiArrivo; }
    public int getNStazioniCitta() { return nStazioniCitta; }
    public double getPercTerrenoUtilizzato() { return percTerrenoUtilizzato; }
    public String getUtente() { return utente; }


    public void setCity(String city) throws InvalidBuyTicketInputExceptionBrondi {
        this.city = validateCitySyntax(city);
    }

    public void setStartStation(String rawStartStation) throws InvalidRouteInputExceptionRemoli {
        String pulito = sanitize(rawStartStation);
        if (pulito == null || pulito.isEmpty()) {
            throw new InvalidRouteInputExceptionRemoli("startStation", "La stazione di partenza non può essere nulla o vuota.");
        }
        this.startStation = pulito;
    }

    public void setEndStation(String rawEndStation) throws InvalidRouteInputExceptionRemoli {
        String pulito = sanitize(rawEndStation);
        if (pulito == null || pulito.isEmpty()) {
            throw new InvalidRouteInputExceptionRemoli("endStation", "La stazione di destinazione non può essere nulla o vuota.");
        }
        this.endStation = pulito;
    }

    public void setTipoViaggiatore(String rawTipoViaggiatore) throws InvalidRouteInputExceptionRemoli {
        String pulito = sanitize(rawTipoViaggiatore);
        if (pulito == null || pulito.isEmpty()) {
            throw new InvalidRouteInputExceptionRemoli("tipoViaggiatore", "Il tipo di viaggiatore non può essere nullo o vuoto.");
        }
        this.tipoViaggiatore = pulito;
    }

    public void setUtente(String rawUtente) throws InvalidRouteInputExceptionRemoli {
        String pulito = sanitize(rawUtente);
        if (pulito == null || pulito.isEmpty()) {
            throw new InvalidRouteInputExceptionRemoli("utente", "L'utente non può essere nullo o vuoto.");
        }
        this.utente = pulito;
    }

    public void setListaCambi(String rawListaCambi) throws InvalidRouteInputExceptionRemoli {
        String pulito = sanitize(rawListaCambi);
        if (pulito == null) {
            throw new InvalidRouteInputExceptionRemoli("listaCambi", "La lista cambi non può essere nulla (al massimo stringa vuota).");
        }
        this.listaCambi = pulito;
    }

    public void setStazioneDiInterscambio(String rawStazioneDiInterscambio) throws InvalidRouteInputExceptionRemoli {
        String pulito = sanitize(rawStazioneDiInterscambio);
        if (pulito == null) {
            throw new InvalidRouteInputExceptionRemoli("stazioneDiInterscambio", "La stazione di interscambio non può essere nulla (al massimo stringa vuota).");
        }
        this.stazioneDiInterscambio = pulito;
    }

    public void setNCambi(int nCambi) throws InvalidRouteInputExceptionRemoli {
        if (nCambi < 0) {
            throw new InvalidRouteInputExceptionRemoli("nCambi", "Il numero di cambi non può essere negativo.");
        }
        this.nCambi = nCambi;
    }

    public void setNStazioniAttraversate(int nStazioniAttraversate) throws InvalidRouteInputExceptionRemoli {
        if (nStazioniAttraversate < 0) {
            throw new InvalidRouteInputExceptionRemoli("nStazioniAttraversate", "Il numero di stazioni attraversate non può essere negativo.");
        }
        this.nStazioniAttraversate = nStazioniAttraversate;
    }

    public void setNStazioniCitta(int nStazioniCitta) throws InvalidRouteInputExceptionRemoli {
        if (nStazioniCitta < 0) {
            throw new InvalidRouteInputExceptionRemoli("nStazioniCitta", "Il numero di stazioni in città non può essere negativo.");
        }
        this.nStazioniCitta = nStazioniCitta;
    }

    public void setTempoDiArrivo(double tempoDiArrivo) throws InvalidRouteInputExceptionRemoli {
        if (tempoDiArrivo < 0) {
            throw new InvalidRouteInputExceptionRemoli("tempoDiArrivo", "Il tempo di arrivo non può essere negativo.");
        }
        this.tempoDiArrivo = tempoDiArrivo;
    }

    public void setPercTerrenoUtilizzato(double percTerrenoUtilizzato) throws InvalidRouteInputExceptionRemoli {
        if (percTerrenoUtilizzato < 0 || percTerrenoUtilizzato > 100) {
            throw new InvalidRouteInputExceptionRemoli("percTerrenoUtilizzato", "La percentuale di terreno utilizzato deve essere tra 0 e 100.");
        }
        this.percTerrenoUtilizzato = percTerrenoUtilizzato;
    }
}