package it.web.routex.exception;

public class InvalidPaymentInputExceptionBrondi extends BrondiValidationException {

    public InvalidPaymentInputExceptionBrondi(String userMessage,
                                              String technicalMessage,
                                              Severity severity)
    {
        super("ERR-PAYMENT-INPUT", userMessage, technicalMessage, severity);
    }

}