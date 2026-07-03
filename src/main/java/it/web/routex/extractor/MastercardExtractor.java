package it.web.routex.extractor;
import javax.servlet.http.HttpServletRequest;
import it.web.routex.exception.InvalidCardInputExceptionRemoli;
import it.web.routex.record.MastercardRecord;

import static it.web.routex.utility.text.TextUtils.sanitize;


public final class MastercardExtractor {

    private MastercardExtractor() {
        throw new AssertionError("Classe di estrazione dati, non si creano new");
    }

    public static MastercardRecord from(HttpServletRequest request)
            throws InvalidCardInputExceptionRemoli {

        String numero = sanitizeParam(request, "numeroCarta");
        String scadenza = sanitizeParam(request, "scadenza");
        String cvv = sanitizeParam(request, "cvv");

        MastercardValidator.validate(numero, scadenza, cvv);

        return new MastercardRecord(numero, scadenza, cvv);
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
