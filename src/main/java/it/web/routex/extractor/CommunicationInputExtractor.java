package it.web.routex.extractor;

import it.web.routex.exception.BrondiInvalidCommunicationInputException;
import it.web.routex.record.CommunicationInput;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public final class CommunicationInputExtractor {

    private static final Set<String> FORBIDDEN_WORDS = loadForbiddenWords();

    private CommunicationInputExtractor() {

    }

    public static CommunicationInput extract(HttpServletRequest request)
            throws BrondiInvalidCommunicationInputException {

        String testo = request.getParameter("message");

        // Controllo input vuoto
        if (testo == null || testo.trim().isEmpty()) {
            throw new BrondiInvalidCommunicationInputException(
                    "Il messaggio della comunicazione non può essere vuoto."
            );
        }

        String cleanText = testo.trim();

        String normalized = cleanText.toLowerCase().replaceAll("[^a-zàèéìòù]", " ");

        // Controllo parole proibite
        for (String forbidden : FORBIDDEN_WORDS) {
            if (normalized.contains(forbidden)) {
                throw new BrondiInvalidCommunicationInputException(
                        "Il messaggio contiene linguaggio non consentito."
                );
            }
        }

        // Input valido
        return new CommunicationInput(
                cleanText,
                new Timestamp(System.currentTimeMillis())
        );
    }


    private static Set<String> loadForbiddenWords() {

        Properties props = new Properties();

        try (InputStream is = CommunicationInputExtractor.class.getClassLoader().getResourceAsStream("forbiddenwords.properties")) {

            if (is == null) {
                return Set.of();
            }

            props.load(is);

            String raw = props.getProperty("forbidden.words");

            if (raw == null || raw.isBlank()) {
                return Set.of();
            }

            return new HashSet<>(
                    Arrays.asList(raw.toLowerCase().split(","))
            );

        } catch (Exception e) {
            return Set.of();
        }
    }
}
