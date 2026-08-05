package it.web.routex.utility.text;

import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;
import it.web.routex.exception.InvalidCardInputExceptionBrondi;


public final class TextUtils {

    private TextUtils() {}

    public static String sanitize(String s) {
        return (s == null) ? null : s.trim();
    }

    public static String sanitizeParam(String name)
            throws InvalidCardInputExceptionBrondi {

        if (name == null) {
            throw new InvalidCardInputExceptionBrondi(
                    "Errore",
                    "Parametro non presente nella request",
                    InvalidCardInputExceptionBrondi.Severity.HIGH
            );
        }

        return sanitize(name);
    }

    public static InvalidCardInputExceptionBrondi error(String message){

        return new InvalidCardInputExceptionBrondi(
                message,
                message,
                InvalidCardInputExceptionBrondi.Severity.MEDIUM
        );
    }

    public static InvalidBuyTicketInputExceptionBrondi errorTicket(String message) {

        return new InvalidBuyTicketInputExceptionBrondi(
                message,
                message,
                InvalidBuyTicketInputExceptionBrondi.Severity.MEDIUM
        );
    }


    public static String validateCitySyntax(String rawCity) throws InvalidBuyTicketInputExceptionBrondi {

        if (rawCity == null)
            throw new InvalidBuyTicketInputExceptionBrondi(
                    "Il campo città non è stato inviato dal form.",
                    "Parametro 'Città' null.",
                    InvalidBuyTicketInputExceptionBrondi.Severity.LOW
            );

        String city = sanitize(rawCity);

        if (city.isBlank())
            throw new InvalidBuyTicketInputExceptionBrondi(
                    "Città mancante.",
                    "Città è blank dopo sanitizzazione.",
                    InvalidBuyTicketInputExceptionBrondi.Severity.MEDIUM
            );

        if (!city.matches("^[\\p{L}\\s\\-']{2,50}$")) {
            throw new InvalidBuyTicketInputExceptionBrondi(
                    "Inserisci una città valida.",
                    "Regex città non rispettata: " + city,
                    InvalidBuyTicketInputExceptionBrondi.Severity.MEDIUM
            );
        }

        return city;
    }

    public static int validateQuantity(String rawQuantity) throws InvalidBuyTicketInputExceptionBrondi {
        if (rawQuantity == null) {
            throw errorTicket("Il campo quantità non può essere vuoto.");
        }

        String sQuantita = sanitize(rawQuantity);
        int quantita;

        try {
            quantita = Integer.parseInt(sQuantita);
        } catch (NumberFormatException e) {
            throw errorTicket("La quantità inserita non è un numero valido.");
        }

        if (quantita <= 0)
            throw errorTicket("La quantità deve essere maggiore di zero.");

        if (quantita > 10)
            throw errorTicket("Puoi acquistare un massimo di 10 biglietti alla volta.");

        return quantita;

    }

}
