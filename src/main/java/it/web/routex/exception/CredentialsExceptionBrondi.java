package it.web.routex.exception;

public class CredentialsExceptionBrondi extends Exception {

    final String details;

    public CredentialsExceptionBrondi(String message, String details) {
        super(message);
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
