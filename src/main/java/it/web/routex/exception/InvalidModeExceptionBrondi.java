package it.web.routex.exception;

public class InvalidModeExceptionBrondi extends BrondiValidationException {

    public InvalidModeExceptionBrondi(String userMessage, String technicalMessage, Severity severity) {
        super("ERR_APP_MODE", userMessage, technicalMessage, severity);
    }

}