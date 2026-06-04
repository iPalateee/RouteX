package it.web.routex.model;

import java.sql.Timestamp;

/**
 * Model di dominio che rappresenta una notifica del sistema.
 * Incapsula i dati persistenti associati a una comunicazione
 * (contenuto del messaggio, data di creazione e stato di risoluzione),
 * ed è utilizzato dallo strato applicativo per applicare la logica di business.
 * @LorenzoBrondi
 */


public class Notification {

    private final String message;
    private final Timestamp date;
    private boolean risolto;

    public Notification(String message, Timestamp date, boolean risolto) {
        this.message = message;
        this.date = date;
        this.risolto = risolto;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getDate() {
        return date;
    }

    public boolean isRisolto() {
        return risolto;
    }
    public void setRisolto(boolean ris)
    {
        this.risolto = ris;
    }
}
