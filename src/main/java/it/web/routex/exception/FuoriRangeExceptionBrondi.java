package it.web.routex.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Eccezione lanciata quando un indice (ID stazione, nodo, ecc.)
 * è fuori dal range della matrice di adiacenza.
 */
public class FuoriRangeExceptionBrondi extends Exception {

    private final String errorCode;
    private final String userMessage;
    private final String technicalMessage;
    private final LocalDateTime timestamp;
    private final Severity severity;

    public enum Severity {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    public FuoriRangeExceptionBrondi(String technicalMessage, Severity severity) {
        super(technicalMessage);
        this.errorCode = "ERR-RANGE-BRONDI";
        this.userMessage = "Parametro fuori dal range consentito. Verifica la stazione selezionata.";
        this.technicalMessage = technicalMessage;
        this.timestamp = LocalDateTime.now(ZoneId.systemDefault());
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "FuoriRangeExceptionBrondi {" +
                "errorCode='" + errorCode + '\'' +
                ", userMessage='" + userMessage + '\'' +
                ", technicalMessage='" + technicalMessage + '\'' +
                ", severity=" + severity +
                ", timestamp=" + timestamp +
                '}';
    }
}
