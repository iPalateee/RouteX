package it.web.routex.extractor;

import it.web.routex.record.CommunicationInput;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

public final class CommunicationInputExtractor {

    private CommunicationInputExtractor() {
    }

    public static CommunicationInput extract(HttpServletRequest request) {

        String testo = request.getParameter("message");

        return new CommunicationInput(
                testo,
                new Timestamp(System.currentTimeMillis())
        );
    }
}