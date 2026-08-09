package it.web.routex.bean;

import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;

// Ottima l'importazione statica di TextUtils!
import static it.web.routex.utility.text.TextUtils.*;

public class TicketBean {

    private String codice;
    private String citta;
    private int quantity;
    private String dataAcquisto;

    public String getCodice() { return codice; }
    public String getCity() { return citta; }
    public int getQuantity() { return quantity; }
    public String getDataAcquisto() { return dataAcquisto; }

    public void setCity(String rawCity) throws InvalidBuyTicketInputExceptionBrondi {
        this.citta = validateCitySyntax(rawCity);
    }

    public void setQuantity(String rawQuantity) throws InvalidBuyTicketInputExceptionBrondi {
        this.quantity = validateQuantity(rawQuantity);
    }

    public void setCodice(String rawCodice) throws InvalidBuyTicketInputExceptionBrondi {
        String pulito = sanitize(rawCodice);
        if (pulito == null || pulito.isEmpty()) {
            throw errorTicket("Errore di sintassi: Il codice del biglietto non può essere vuoto o mancante.");
        }
        this.codice = pulito;
    }

    public void setDataAcquisto(String rawDataAcquisto) throws InvalidBuyTicketInputExceptionBrondi {
        String pulito = sanitize(rawDataAcquisto);
        if (pulito == null || pulito.isEmpty()) {
            throw errorTicket("Errore di sintassi: La data di acquisto non può essere vuota o mancante.");
        }
        this.dataAcquisto = pulito;
    }
}