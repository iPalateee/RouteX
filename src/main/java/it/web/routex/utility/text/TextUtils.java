package it.web.routex.utility.text;

import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;
import it.web.routex.exception.InvalidCardInputExceptionRemoli;


public final class TextUtils {

    private TextUtils() {}

    public static String sanitize(String s) {
        return (s == null) ? null : s.trim();
    }

    public static String sanitizeParam(String name)
            throws InvalidCardInputExceptionRemoli {

        if (name == null) {
            throw new InvalidCardInputExceptionRemoli(
                    "Errore",
                    "Parametro non presente nella request",
                    InvalidCardInputExceptionRemoli.Severity.HIGH
            );
        }

        return sanitize(name);
    }

    public static void error(String message)
            throws InvalidCardInputExceptionRemoli {

        throw new InvalidCardInputExceptionRemoli(
                message,
                message,
                InvalidCardInputExceptionRemoli.Severity.MEDIUM
        );
    }

    public static void errorTicket(String msg, InvalidBuyTicketInputExceptionBrondi.Severity severity) throws InvalidBuyTicketInputExceptionBrondi {
        throw new InvalidBuyTicketInputExceptionBrondi(msg, msg, severity);
    }

}
