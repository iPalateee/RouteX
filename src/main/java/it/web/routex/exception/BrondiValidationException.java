package it.web.routex.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;

public abstract class BrondiValidationException extends Exception {

    private final String errorCode;
    private final String userMessage;
    private final String technicalMessage;
    private final Severity severity;
    private final LocalDateTime timestamp;

    public enum Severity { LOW, MEDIUM, HIGH, CRITICAL }

    protected BrondiValidationException(String errorCode,
                                        String userMessage,
                                        String technicalMessage,
                                        Severity severity) {
        super(technicalMessage);
        this.errorCode = errorCode;
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
        return getClass().getSimpleName() + " {" +
                "errorCode='" + errorCode + '\'' +
                ", userMessage='" + userMessage + '\'' +
                ", technicalMessage='" + technicalMessage + '\'' +
                ", severity=" + severity +
                ", timestamp=" + timestamp +
                '}';
    }
}