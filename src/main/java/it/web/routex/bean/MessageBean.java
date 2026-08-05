package it.web.routex.bean;

import it.web.routex.exception.RemoliInvalidCommunicationInputException;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class MessageBean
{
    private static final Set<String> FORBIDDEN_WORDS = loadForbiddenWords();
    private String message;
    private Timestamp date;
    private Boolean risolto;

    public MessageBean(){}

    public MessageBean(String m, Timestamp d)
    {
        setMessage(m);
        this.date = d;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {

        if (message == null || message.trim().isEmpty()) {
            throw new RemoliInvalidCommunicationInputException(
                    "Il messaggio della comunicazione non può essere vuoto."
            );
        }

        String cleanText = message.trim();
        String normalized = cleanText.toLowerCase().replaceAll("[^a-zàèéìòù]", " ");

        for (String forbidden : FORBIDDEN_WORDS) {
            if (normalized.contains(forbidden)) {
                throw new RemoliInvalidCommunicationInputException(
                        "Il messaggio contiene linguaggio non consentito."
                );
            }
        }

        this.message = cleanText;
    }

    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setRisolto(Boolean risolto) {
        this.risolto = risolto;
    }

    private static Set<String> loadForbiddenWords() {

        Properties props = new Properties();

        try (InputStream is = MessageBean.class.getClassLoader().getResourceAsStream("forbiddenwords.properties")) {

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