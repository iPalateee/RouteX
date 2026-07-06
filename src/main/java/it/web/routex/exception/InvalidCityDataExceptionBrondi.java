package it.web.routex.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class InvalidCityDataExceptionBrondi extends Exception {

    private final String errorCode;
    private final String userMessage;
    private final String technicalMessage;
    private final Severity severity;
    private final LocalDateTime timestamp;

    public enum Severity { LOW, MEDIUM, HIGH, CRITICAL }

    public InvalidCityDataExceptionBrondi(String userMessage,
                                          String technicalMessage,
                                          Severity severity) {
        super(technicalMessage);
        this.errorCode = "ERR-CITY-DATA";
        this.userMessage = userMessage;
        this.technicalMessage = technicalMessage;
        this.severity = severity;
        this.timestamp = LocalDateTime.now(ZoneId.systemDefault());
    }

    public String getUserMessage() {
        return userMessage;
    }

    @Override
    public String toString() {
        return "InvalidCityDataExceptionBrondi {" +
                "errorCode='" + errorCode + '\'' +
                ", userMessage='" + userMessage + '\'' +
                ", technicalMessage='" + technicalMessage + '\'' +
                ", severity=" + severity +
                ", timestamp=" + timestamp +
                '}';
    }
}
