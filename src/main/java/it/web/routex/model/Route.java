package it.web.routex.model;
import it.web.routex.bean.InformazioniPercorsoBean;
import it.web.routex.utility.builder.data.RouteData;
import it.web.routex.exception.InvalidRouteException;
import it.web.routex.record.RouteRecord;
import it.web.routex.utility.decorator.decoratorchange.BaseComponent;
import it.web.routex.utility.decorator.decoratorchange.CheckCambiamentiDecorator;
import it.web.routex.utility.decorator.decoratorchange.Component;
import it.web.routex.utility.singleton.Credentials;
import java.util.ArrayList;
import java.util.List;

public class Route {

    private String partenza;
    private String arrivo;
    private String citta;
    private String tipoViaggiatore;
    private int nCambi;
    private String listaCambi;
    private String stazInterscambio;
    private int nStazAttraversate;
    private Double tempoDiArrivo;
    private int nStazioniCitta;
    private Double percTerrenoUtilizzato;
    private String utente;

    public Route(RouteData data)
    {
        this.partenza = data.getStartStation();
        this.arrivo = data.getEndStation();
        this.citta = data.getCity();
        this.tipoViaggiatore = data.getTipoViaggiatore();
        this.nCambi = data.getnCambi();
        this.listaCambi = data.getListaCambi();
        this.stazInterscambio = data.getStazioneDiInterscambio();
        this.nStazAttraversate = data.getnStazioniAttraversate();
        this.tempoDiArrivo = data.getTempoDiArrivo();
        this.nStazioniCitta = data.getnStazioniCitta();
        this.percTerrenoUtilizzato = data.getPercTerrenoUtilizzato();
        this.utente = data.getUtente();
    }


    public Route(InformazioniPercorsoBean dto, RouteRecord route, String status) throws InvalidRouteException {

        Credentials cred = Credentials.getInstanceSingleton();
        Component c = new CheckCambiamentiDecorator(new BaseComponent());


        try {
            //conversione listacambi
            Object objListaCambi = c.getChanges(dto.getCityLife().getSequenzeDiCambiamento());
            List<String> listaCambiList = new ArrayList<>();

            if (objListaCambi instanceof List) {
                listaCambiList = (List<String>) objListaCambi;
            } else if (objListaCambi instanceof String string) {
                listaCambiList.add(string);
            }
            this.listaCambi = String.join(", ", listaCambiList);

            //conversione nodicruciali
            Object objStazInterscambio = c.getChanges(dto.getCityLife().getSequenzeNodiCruciali());
            List<String> stazInterList = new ArrayList<>();
            if (objStazInterscambio instanceof List) {
                stazInterList = (List<String>) objStazInterscambio;
            } else if (objStazInterscambio instanceof String string) {
                stazInterList.add(string);
            }

            this.stazInterscambio = String.join(", ", stazInterList);
            this.partenza = route.start();
            this.arrivo =  route.end();
            this.citta =  route.city();
            this.tipoViaggiatore = status;
            this.nCambi = dto.getCityLife().getNumeroCambi();
            this.nStazAttraversate = dto.getNumeroStazioniUsate();
            this.tempoDiArrivo = dto.getMinutaggio();
            this.nStazioniCitta = dto.getCityLife().getNumeroStazioniTotali();
            this.percTerrenoUtilizzato = dto.getPercentualeStazioniUsate();
            this.utente = cred.getCodiceFiscale();

        } catch (NullPointerException | ClassCastException e) {
            throw new InvalidRouteException(
                    "Errore nei dati della richiesta per la creazione della Route", e
            );
        }
    }

    public Route() {

    }

    public String getPartenza() {
        return partenza;
    }

    public void setPartenza(String partenza) {
        this.partenza = partenza;
    }

    public String getArrivo() {
        return arrivo;
    }

    public void setArrivo(String arrivo) {
        this.arrivo = arrivo;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getTipoViaggiatore() {
        return tipoViaggiatore;
    }

    public void setTipoViaggiatore(String tipoViaggiatore) {
        this.tipoViaggiatore = tipoViaggiatore;
    }

    public int getnCambi() {
        return nCambi;
    }

    public void setnCambi(int nCambi) {
        this.nCambi = nCambi;
    }

    public String getListaCambi() {
        return listaCambi;
    }

    public void setListaCambi(String listaCambi) {
        this.listaCambi = listaCambi;
    }

    public String getStazInterscambio() {
        return stazInterscambio;
    }

    public void setStazInterscambio(String stazInterscambio) {
        this.stazInterscambio = stazInterscambio;
    }

    public int getnStazAttraversate() {
        return nStazAttraversate;
    }

    public void setnStazAttraversate(int nStazAttraversate) {
        this.nStazAttraversate = nStazAttraversate;
    }

    public Double getTempoDiArrivo() {
        return tempoDiArrivo;
    }

    public void setTempoDiArrivo(Double tempoDiArrivo) {
        this.tempoDiArrivo = tempoDiArrivo;
    }

    public int getnStazioniCitta() {
        return nStazioniCitta;
    }

    public void setnStazioniCitta(int nStazioniCitta) {
        this.nStazioniCitta = nStazioniCitta;
    }

    public Double getPercTerrenoUtilizzato() {
        return percTerrenoUtilizzato;
    }

    public void setPercTerrenoUtilizzato(Double percTerrenoUtilizzato) {
        this.percTerrenoUtilizzato = percTerrenoUtilizzato;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }
}


