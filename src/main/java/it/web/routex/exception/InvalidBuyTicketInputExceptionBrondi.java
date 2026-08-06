package it.web.routex.exception;

public class InvalidBuyTicketInputExceptionBrondi extends BrondiValidationException {

    public InvalidBuyTicketInputExceptionBrondi(String userMessage,
                                                String technicalMessage,
                                                Severity severity)
    {
        super("ERR-BUY-TICKET-INPUT", userMessage, technicalMessage, severity);
    }
}