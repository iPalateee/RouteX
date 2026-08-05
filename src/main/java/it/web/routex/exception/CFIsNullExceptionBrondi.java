package it.web.routex.exception;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CFIsNullExceptionBrondi extends Exception {

    private final String errorCode;
    private final String userMessage;
    private final String technicalMessage;
    private final LocalDateTime timestamp;
    private final Severity severity;

    public enum Severity {
        LOW, MEDIUM, HIGH, CRITICAL
    }
    public CFIsNullExceptionBrondi(String userMessage, String technicalMessage, String errorCode, Severity severity)
    {
        super(technicalMessage);
        this.userMessage = userMessage;
        this.technicalMessage = technicalMessage;
        this.errorCode = errorCode;
        this.severity = severity;
        this.timestamp = LocalDateTime.now(ZoneId.systemDefault());
    }
    @Override
    public String toString() {
        return "CFIsNullExceptionBrondi {" +
                "errorCode='" + errorCode + '\'' +
                ", userMessage='" + userMessage + '\'' +
                ", technicalMessage='" + technicalMessage + '\'' +
                ", severity=" + severity +
                ", timestamp=" + timestamp +
                '}';
    }
}

