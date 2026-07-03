package it.web.routex.extractor;

import it.web.routex.record.BuyTicketRecord;

import javax.servlet.http.HttpServletRequest;
import it.web.routex.exception.InvalidBuyTicketInputExceptionRemoli;
import it.web.routex.validator.BuyTicketValidator;

import static it.web.routex.utility.text.TextUtils.sanitize;


public final class BuyTicketExtractor {

    private BuyTicketExtractor()
    {
        throw new AssertionError("Classe di estrazione dati, non si creano new");
    }

    public static BuyTicketRecord from(HttpServletRequest request)
            throws InvalidBuyTicketInputExceptionRemoli {

        String rawCity = request.getParameter("city");
        String rawQuantity = request.getParameter("quantity");

        if (rawCity == null || rawQuantity == null)
            throw new InvalidBuyTicketInputExceptionRemoli(
                    "Parametri mancanti.",
                    "Request incompleta.",
                    InvalidBuyTicketInputExceptionRemoli.Severity.LOW
            );

        String city = sanitize(rawCity);
        String quantity = sanitize(rawQuantity);

        int quantita = BuyTicketValidator.validate(city, quantity);

        return new BuyTicketRecord(city, quantita);
    }

}
