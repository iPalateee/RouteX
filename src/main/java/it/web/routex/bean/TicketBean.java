package it.web.routex.bean;

import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;

import static it.web.routex.utility.text.TextUtils.*;

public class TicketBean
{
    private String codice;
    private String citta;
    private String dataAcquisto;


    public void setCodice(String codice) {
        this.codice = codice;
    }

    public void setCitta(String rawCity) throws InvalidBuyTicketInputExceptionBrondi {

        if (rawCity == null)
            throw new InvalidBuyTicketInputExceptionBrondi(
                    "Parametro mancante.",
                    "Request incompleta.",
                    InvalidBuyTicketInputExceptionBrondi.Severity.LOW
            );

        String city = sanitize(rawCity);

        if (city.isBlank())
            errorTicket("Il campo città non può essere vuoto.", InvalidBuyTicketInputExceptionBrondi.Severity.MEDIUM);

        if (!city.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]+$"))
            errorTicket("La città inserita non è valida.", InvalidBuyTicketInputExceptionBrondi.Severity.MEDIUM);

        this.citta = city;
    }

    public void setDataAcquisto(String dataAcquisto) {
        this.dataAcquisto = dataAcquisto;
    }

    public String getCodice() {
        return codice;
    }

    public String getCitta() {
        return citta;
    }

    public String getDataAcquisto() {
        return dataAcquisto;
    }
}
