package it.web.routex.exception;

public class InvalidPriceCalculationExceptionBrondi extends BrondiValidationException {

    public InvalidPriceCalculationExceptionBrondi(String userMessage,
                                                  String technicalMessage,
                                                  Severity severity)
    {
        super("ERR-PRICE-CALCULATION", userMessage, technicalMessage, severity);
    }

}