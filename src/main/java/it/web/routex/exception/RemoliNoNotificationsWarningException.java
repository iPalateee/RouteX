package it.web.routex.exception;

/**
 * Eccezione applicativa che rappresenta una condizione di warning
 * non ci sono notifiche da visualizzare perché risultano tutte risolte.
 */
public class RemoliNoNotificationsWarningException extends Exception {

    private final String details;

    public RemoliNoNotificationsWarningException(String message, String details) {
        super(message);
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
