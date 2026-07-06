package it.web.routex.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class InvalidPriceCalculationExceptionBrondi extends Exception {

    private final String errorCode;
    private final String userMessage;
    private final String technicalMessage;
    private final Severity severity;
    private final LocalDateTime timestamp;

    public enum Severity { LOW, MEDIUM, HIGH, CRITICAL }

    public InvalidPriceCalculationExceptionBrondi(String userMessage,
                                                  String technicalMessage,
                                                  Severity severity) {
        super(technicalMessage);
        this.errorCode = "ERR-PRICE-CALCULATION";
        this.userMessage = userMessage;
        this.technicalMessage = technicalMessage;
        this.severity = severity;
        this.timestamp = LocalDateTime.now(ZoneId.systemDefault());
    }
    @Override
    public String toString() {
        return "InvalidPriceCalculationExceptionBrondi {" +
                "errorCode='" + errorCode + '\'' +
                ", userMessage='" + userMessage + '\'' +
                ", technicalMessage='" + technicalMessage + '\'' +
                ", severity=" + severity +
                ", timestamp=" + timestamp +
                '}';
    }

    public String getUserMessage() {
        return userMessage;
    }
}
