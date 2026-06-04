package it.web.routex.exception;
import java.time.LocalDateTime;

public class CFIsNullRemoli extends Exception {

    private final String errorCode;          // codice errore interno
    private final String userMessage;        // messaggio per l'utente
    private final String technicalMessage;   // messaggio tecnico per log
    private final LocalDateTime timestamp;   // quando è accaduto
    private final Severity severity;         // livello di gravità

    public enum Severity {
        LOW, MEDIUM, HIGH, CRITICAL
    }
    public CFIsNullRemoli(String userMessage, String technicalMessage, String errorCode, Severity severity)
    {
        super(technicalMessage);
        this.userMessage = userMessage;
        this.technicalMessage = technicalMessage;
        this.errorCode = errorCode;
        this.severity = severity;
        this.timestamp = LocalDateTime.now();
    }
    @Override
    public String toString() {
        return "CFIsNullRemoliException {" +
                "errorCode='" + errorCode + '\'' +
                ", userMessage='" + userMessage + '\'' +
                ", technicalMessage='" + technicalMessage + '\'' +
                ", severity=" + severity +
                ", timestamp=" + timestamp +
                '}';
    }
}

