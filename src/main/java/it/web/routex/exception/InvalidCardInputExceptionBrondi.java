package it.web.routex.exception;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class InvalidCardInputExceptionBrondi extends Exception {

    private final String errorCode;
    private final String userMessage;
    private final String technicalMessage;
    private final Severity severity;
    private final LocalDateTime timestamp;

    public enum Severity { LOW, MEDIUM, HIGH, CRITICAL }

    public InvalidCardInputExceptionBrondi(String userMessage,
                                           String technicalMessage,
                                           Severity severity) {
        super(technicalMessage);

        this.errorCode = "ERR-CARD-INPUT";
        this.userMessage = userMessage;
        this.technicalMessage = technicalMessage;
        this.severity = severity;
        this.timestamp = LocalDateTime.now(ZoneId.systemDefault());
    }

    public String getUserMessage() { return userMessage; }


    @Override
    public String toString() {
        return "InvalidCardInputExceptionBrondi {" +
                "errorCode='" + errorCode + '\'' +
                ", userMessage='" + userMessage + '\'' +
                ", technicalMessage='" + technicalMessage + '\'' +
                ", severity=" + severity +
                ", timestamp=" + timestamp +
                '}';
    }
}

