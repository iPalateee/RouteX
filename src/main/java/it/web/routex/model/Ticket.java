package it.web.routex.model;


import it.web.routex.exception.InvalidTicketExceptionRemoli;

import java.time.LocalDateTime;

/**
 * Model di dominio per il Ticket di viaggio.
 * Incapsula i dati essenziali del biglietto (codice, città, data) e fornisce
 * i metodi di validazione ({@link #isValid()}, {@link #validate()}) per
 * consentire al controller di gestire dinamicamente eventuali errori.
 */
public class Ticket {

    private final String codice;
    private final String citta;
    private final LocalDateTime dataAcquisto;

    public Ticket(String codice, String citta, LocalDateTime dataAcquisto) {
        this.codice = codice;
        this.citta = citta;
        this.dataAcquisto = dataAcquisto;
    }

    public LocalDateTime getDataAcquisto() {
        return dataAcquisto;
    }

    public String getCitta() {
        return citta;
    }

    public String getCodice() {
        return codice;
    }
    public boolean isValid() {
        return codice != null && !codice.isBlank()
                && citta != null && !citta.isBlank()
                && dataAcquisto != null;
    }

    public void validate() throws InvalidTicketExceptionRemoli {
        if (!isValid()) {
            throw new InvalidTicketExceptionRemoli(
                    "Ticket non valido",
                    "Invarianti di dominio violate nel Ticket",
                    codice
            );
        }
    }
}


