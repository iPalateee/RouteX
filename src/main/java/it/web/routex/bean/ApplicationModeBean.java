package it.web.routex.bean;

import it.web.routex.exception.BrondiValidationException;
import it.web.routex.exception.InvalidModeExceptionBrondi;

import static it.web.routex.utility.text.TextUtils.sanitize;

public class ApplicationModeBean {

    private String mode;

    public String getMode() {
        return mode;
    }

    public void setMode(String rawMode) throws InvalidModeExceptionBrondi {

        if (rawMode == null || rawMode.isBlank()) {
            throw new InvalidModeExceptionBrondi(
                    "Errore: modalità non specificata.",
                    "Parametro rawMode null o blank.",
                    BrondiValidationException.Severity.HIGH
            );
        }

        String modalita = sanitize(rawMode);

        if (!modalita.equals("DEMO") && !modalita.equals("FULL")) {
            throw new InvalidModeExceptionBrondi(
                    "Errore modalità non valida.",
                    "Valore non valido per mode: " + modalita,
                    BrondiValidationException.Severity.HIGH
            );
        }
        this.mode = modalita;

    }
}
