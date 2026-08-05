package it.web.routex.bean;

import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;

import static it.web.routex.utility.text.TextUtils.*;

public class TicketBean
{
    private String codice;
    private String citta;
    private int quantity;
    private String dataAcquisto;

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public void setCity(String rawCity) throws InvalidBuyTicketInputExceptionBrondi {

        this.citta = validateCitySyntax(rawCity);
    }

    public void setQuantity(String rawQuantity) throws InvalidBuyTicketInputExceptionBrondi {
        if (rawQuantity == null) {
            errorTicket("Il campo quantità non può essere vuoto.", InvalidBuyTicketInputExceptionBrondi.Severity.MEDIUM);
            return;
        }

        String sQuantita = sanitize(rawQuantity);
        int quantita;

        try {
            quantita = Integer.parseInt(sQuantita);
        } catch (NumberFormatException e) {
            errorTicket("La quantità inserita non è un numero valido.", InvalidBuyTicketInputExceptionBrondi.Severity.MEDIUM);
            return;
        }

        if (quantita <= 0)
            errorTicket("La quantità deve essere maggiore di zero.", InvalidBuyTicketInputExceptionBrondi.Severity.MEDIUM);

        if (quantita > 10)
            errorTicket("Puoi acquistare un massimo di 10 biglietti alla volta.", InvalidBuyTicketInputExceptionBrondi.Severity.HIGH);

        this.quantity = quantita;
    }

    public void setDataAcquisto(String dataAcquisto) {
        this.dataAcquisto = dataAcquisto;
    }

    public String getCodice() {
        return codice;
    }

    public String getCity() {
        return citta;
    }

    public int getQuantity() { return quantity; }

    public String getDataAcquisto() {
        return dataAcquisto;
    }
}
