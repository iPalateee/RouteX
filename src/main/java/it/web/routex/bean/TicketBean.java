package it.web.routex.bean;

import it.web.routex.exception.InvalidBuyTicketInputExceptionRemoli;

import static it.web.routex.utility.text.TextUtils.*;

public class TicketBean
{
    private String codice;
    private String citta;
    private String dataAcquisto;


    public void setCodice(String codice) {
        this.codice = codice;
    }

    public void setCitta(String rawCity) throws InvalidBuyTicketInputExceptionRemoli {

        if (rawCity == null)
            throw new InvalidBuyTicketInputExceptionRemoli(
                    "Parametro mancante.",
                    "Request incompleta.",
                    InvalidBuyTicketInputExceptionRemoli.Severity.LOW
            );

        String city = sanitize(rawCity);

        if (city.isBlank())
            errorTicket("Il campo città non può essere vuoto.", InvalidBuyTicketInputExceptionRemoli.Severity.MEDIUM);

        if (!city.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]+$"))
            errorTicket("La città inserita non è valida.", InvalidBuyTicketInputExceptionRemoli.Severity.MEDIUM);

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
