package it.web.routex.extractor;

import it.web.routex.record.PaypalRecord;
import it.web.routex.exception.InvalidCardInputExceptionRemoli;
import it.web.routex.validator.PaypalValidator;

import javax.servlet.http.HttpServletRequest;

import static it.web.routex.utility.text.TextUtils.sanitize;


public final class PaypalExtractor {

    private PaypalExtractor() {
        throw new AssertionError("Classe di estrazione dati, non si creano new");
    }

    public static PaypalRecord from(HttpServletRequest request)
            throws InvalidCardInputExceptionRemoli {

        String email = sanitizeParam(request, "emailPaypal");
        String codice = sanitizeParam(request, "codiceTransazione");

        PaypalValidator.validate(email, codice);

        return new PaypalRecord(email, codice);
    }


    private static String sanitizeParam(HttpServletRequest request, String name)
            throws InvalidCardInputExceptionRemoli {

        String value = request.getParameter(name);

        if (value == null) {
            throw new InvalidCardInputExceptionRemoli(
                    "Parametro mancante: " + name,
                    "Parametro " + name + " non presente nella request",
                    InvalidCardInputExceptionRemoli.Severity.HIGH
            );
        }

        return sanitize(value);
    }
}
