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
        this.quantity = validateQuantity(rawQuantity);
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
