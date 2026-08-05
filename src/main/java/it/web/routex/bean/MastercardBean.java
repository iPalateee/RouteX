package it.web.routex.bean;

import it.web.routex.exception.InvalidCardInputExceptionBrondi;

import java.time.LocalDate;
import java.time.ZoneId;

import static it.web.routex.utility.text.TextUtils.*;

public class MastercardBean {
    private String numero;
    private String scadenza;
    private String cvv;

    public String getNumero(){
        return this.numero;
    }

    public String getScadenza(){
        return this.scadenza;
    }

    public String getCvv(){
        return this.cvv;
    }

    public void setNumero(String rawNumero) throws InvalidCardInputExceptionBrondi {

        String numeroo = sanitizeParam(rawNumero);

        if (!numeroo.isBlank() && !numeroo.matches("^\\d{16}$")) {
            throw error("Numero carta non valido.");
        }

        this.numero = numeroo;

    }

    public void setScadenza(String rawScadenza) throws InvalidCardInputExceptionBrondi {

        String scadenzaa = sanitizeParam(rawScadenza);

        if (!scadenzaa.isBlank() && !scadenzaa.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            throw error("Scadenza non valida.");
        }

        String[] parts = scadenzaa.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);

        if (month < 1 || month > 12) {
            throw error("Mese della carta non valido.");
        }

        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate expiry = LocalDate.of(year, month, 1)
                .plusMonths(1)
                .minusDays(1);

        if (expiry.isBefore(today)) {
            throw new InvalidCardInputExceptionBrondi(
                    "La carta è scaduta.",
                    "Data scadenza " + scadenza,
                    InvalidCardInputExceptionBrondi.Severity.HIGH
            );
        }

        this.scadenza = scadenzaa;
    }

    public void setCvv(String rawCvv) throws InvalidCardInputExceptionBrondi {

        String cvvv = sanitizeParam(rawCvv);

        if (!cvvv.matches("^\\d{3}$")) {
            throw error("Il codice CVV non è valido.");
        }
        if (!cvvv.isBlank() && !cvvv.matches("^\\d{3}$")) {
            throw error("CVV non valido.");
        }

        this.cvv = cvvv;
    }

}
