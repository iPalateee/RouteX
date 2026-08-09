package it.web.routex.bean;

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
                    InvalidModeExceptionBrondi.Severity.HIGH
            );
        }

        String mode = sanitize(rawMode);

        if (!mode.equals("DEMO") && !mode.equals("FULL")) {
            throw new InvalidModeExceptionBrondi(
                    "Errore modalità non valida.",
                    "Valore non valido per mode: " + mode,
                    InvalidModeExceptionBrondi.Severity.HIGH
            );
        }
        this.mode = mode;

    }
}
