package it.web.routex.exception;

public class InvalidCardInputExceptionBrondi extends BrondiValidationException {

    public InvalidCardInputExceptionBrondi(String userMessage,
                                           String technicalMessage,
                                           Severity severity)
    {
        super("ERR-CARD-INPUT", userMessage, technicalMessage, severity);
    }

}