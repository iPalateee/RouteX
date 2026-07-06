package it.web.routex.bean;

import it.web.routex.exception.InvalidCardInputExceptionRemoli;

import java.time.LocalDate;

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

    public void setNumero(String rawNumero) throws InvalidCardInputExceptionRemoli {

        String numero = sanitizeParam(rawNumero);

        if (!numero.isBlank() && !numero.matches("^\\d{16}$")) {
            error("Numero carta non valido.");
        }

        this.numero = numero;

    }

    public void setScadenza(String rawScadenza) throws InvalidCardInputExceptionRemoli {

        String scadenza = sanitizeParam(rawScadenza);

        if (!scadenza.isBlank() && !scadenza.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            error("Scadenza non valida.");
        }

        String[] parts = scadenza.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);

        if (month < 1 || month > 12) {
            error("Mese della carta non valido.");
        }

        LocalDate today = LocalDate.now();
        LocalDate expiry = LocalDate.of(year, month, 1)
                .plusMonths(1)
                .minusDays(1);

        if (expiry.isBefore(today)) {
            throw new InvalidCardInputExceptionRemoli(
                    "La carta è scaduta.",
                    "Data scadenza " + scadenza,
                    InvalidCardInputExceptionRemoli.Severity.HIGH
            );
        }

        this.scadenza = scadenza;
    }

    public void setCvv(String rawCvv) throws InvalidCardInputExceptionRemoli {

        String cvv = sanitizeParam(rawCvv);

        if (!cvv.matches("^\\d{3}$")) {
            error("Il codice CVV non è valido.");
        }
        if (!cvv.isBlank() && !cvv.matches("^\\d{3}$")) {
            error("CVV non valido.");
        }

        this.cvv = cvv;
    }

}
