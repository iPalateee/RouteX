package it.web.routex.bean;

import it.web.routex.enumerator.Ruolo;
import it.web.routex.exception.InvalidLoginInputExceptionRemoli;
import it.web.routex.exception.InvalidLoginInputExceptionRemoli.Severity;

import static it.web.routex.utility.text.TextUtils.sanitize;

public class UtenteBeanGenerico {

    private String nome;
    private String cognome;
    private String codiceFiscale;
    private boolean isDisable;
    private Ruolo ruolo;

    public boolean isDisable() { return isDisable; }
    public String getCodicefiscale() { return codiceFiscale; }
    public Ruolo getRuolo() { return ruolo; }
    public String getCognome() { return cognome; }
    public String getNome() { return nome; }

    public void setDisable(boolean disable) {
        this.isDisable = disable;
    }

    public void setRuolo(Ruolo ruolo) throws InvalidLoginInputExceptionRemoli {
        if (ruolo == null) {
            throw new InvalidLoginInputExceptionRemoli(
                    "Errore: Il ruolo dell'utente non può essere mancante.",
                    "Tentativo di assegnare null a Ruolo",
                    Severity.CRITICAL
            );
        }
        this.ruolo = ruolo;
    }

    public void setNome(String rawNome) throws InvalidLoginInputExceptionRemoli {
        String pulito = sanitize(rawNome);
        if (pulito == null || pulito.isEmpty()) {
            throw new InvalidLoginInputExceptionRemoli(
                    "Errore di sintassi: Il nome non può essere vuoto.",
                    "Parametro Nome nullo o blank dopo sanitizzazione",
                    Severity.HIGH
            );
        }
        this.nome = pulito;
    }

    public void setCognome(String rawCognome) throws InvalidLoginInputExceptionRemoli {
        String pulito = sanitize(rawCognome);
        if (pulito == null || pulito.isEmpty()) {
            throw new InvalidLoginInputExceptionRemoli(
                    "Errore di sintassi: Il cognome non può essere vuoto.",
                    "Parametro Cognome nullo o blank dopo sanitizzazione",
                    Severity.HIGH
            );
        }
        this.cognome = pulito;
    }

    public void setCodiceFiscale(String rawCF) throws InvalidLoginInputExceptionRemoli {
        String pulito = sanitize(rawCF);
        if (pulito == null || pulito.isEmpty()) {
            throw new InvalidLoginInputExceptionRemoli(
                    "Errore di sintassi: Il Codice Fiscale non può essere vuoto.",
                    "Parametro Codice Fiscale nullo o blank",
                    Severity.HIGH
            );
        }

        if (pulito.length() != 16) {
            throw new InvalidLoginInputExceptionRemoli(
                    "Errore di sintassi: Il Codice Fiscale deve contenere esattamente 16 caratteri.",
                    "Lunghezza Codice Fiscale non valida: trovati " + pulito.length() + " caratteri",
                    Severity.HIGH
            );
        }

        this.codiceFiscale = pulito;
    }
}