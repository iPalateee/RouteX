package it.web.routex.exception;

/**
 * Eccezione applicativa che rappresenta una condizione di warning
 * non ci sono notifiche da visualizzare perché risultano tutte risolte.
 * @LorenzoBrondi
 */
public class BrondiNoNotificationsWarningException extends Exception {

    private final String details;

    public BrondiNoNotificationsWarningException(String message, String details) {
        super(message);
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
