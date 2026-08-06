package it.web.routex.exception;

public class BrondiException extends Exception {

    private final String codiceDiErrore;
    private final String details;

    public BrondiException(String message,
                           String codiceDiErrore,
                           String details) {
        super(message);
        this.codiceDiErrore = codiceDiErrore;
        this.details = details;
    }

    public String getCodiceDiErrore() {
        return codiceDiErrore;
    }

    public String getDetails() {
        return details;
    }
}
