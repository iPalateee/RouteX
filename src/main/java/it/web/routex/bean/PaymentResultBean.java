package it.web.routex.bean;

import it.web.routex.exception.BrondiValidationException;
import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;
import it.web.routex.exception.InvalidCardInputExceptionBrondi;
import it.web.routex.exception.InvalidPaymentInputExceptionBrondi;

import java.util.List;

import static it.web.routex.utility.text.TextUtils.*;

public class PaymentResultBean {

    private String city;
    private double total;
    private String paymentMethod;
    private final List<String> ticketCodes;
    private int quantity;
    private String persistence;
    //se devo fare mastercard
    private String numeroCarta;
    private String scadenzaCarta;
    private String cvv;
    //se devo fare paypal
    private String emailPaypal;
    private String codicePaypal;

    public PaymentResultBean() {
        this.city = "";
        this.total = 0.0;
        this.paymentMethod = "";
        this.ticketCodes = null;
        this.quantity = 0;
    }

    public PaymentResultBean(
            String city,
            double total,
            String paymentMethod,
            List<String> ticketCodes,
            int quantity
    ) {
        this.city = city;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.ticketCodes = ticketCodes;
        this.quantity = quantity;
    }

    public String getCity() { return city; }
    public double getTotal() { return total; }
    public String getPaymentMethod() { return paymentMethod; }
    public List<String> getTicketCodes() { return ticketCodes; }
    public int getQuantity() { return quantity; }
    public String getPersistenza() { return persistence; }
    public String getNumeroCarta() {
        return numeroCarta;
    }
    public String getScadenzaCarta() {
        return scadenzaCarta;
    }
    public String getCvv() {
        return cvv;
    }
    public String getEmailPaypal() {
        return emailPaypal;
    }
    public String getCodicePaypal() {
        return codicePaypal;
    }

    public void setCity(String c) throws InvalidBuyTicketInputExceptionBrondi {
        this.city = validateCitySyntax(c);
    }

    public void setTotale(String rawTotale) throws InvalidBuyTicketInputExceptionBrondi {
        if (rawTotale == null || rawTotale.isBlank()) {
            throw errorTicket("Il totale non può essere nullo o vuoto.");
        }

        double parsedTotale;

        try {
            parsedTotale = Double.parseDouble(sanitize(rawTotale));
        } catch (NumberFormatException e) {
            throw errorTicket("Il totale inserito non è valido.");
        }

        if (parsedTotale < 0) {
            throw errorTicket("Il totale non può essere negativo.");
        }

        this.total = parsedTotale;
    }

    public void setMetodoPagamento(String rawMetodo) throws InvalidCardInputExceptionBrondi {
        if (rawMetodo == null || rawMetodo.isBlank()) {
            throw error("Metodo di pagamento vuoto.");
        }
        this.paymentMethod = sanitize(rawMetodo);
    }

    public void setQuantity(String rawQuantity) throws InvalidBuyTicketInputExceptionBrondi {
        this.quantity = validateQuantity(rawQuantity);
    }

    public void setPersistenza(String rawPersistenza) throws InvalidPaymentInputExceptionBrondi {

        if (rawPersistenza == null || rawPersistenza.isBlank()) {
            throw new InvalidPaymentInputExceptionBrondi(
                    "Campo mancante: persistenza.",
                    "Il parametro persistence è null o blank.",
                    BrondiValidationException.Severity.LOW
            );
        }

        String persistenza = sanitize(rawPersistenza);

        if (!persistenza.equals("JDBC") && !persistenza.equals("FileSystem")) {
            throw new InvalidPaymentInputExceptionBrondi(
                    "Tipo di persistenza non valido.",
                    "Parametro " + persistenza + " non valido.",
                    BrondiValidationException.Severity.HIGH
            );
        }

        this.persistence = persistenza;
    }

    public void setNumeroCarta(String rawNumeroCarta) throws InvalidCardInputExceptionBrondi {

        if (rawNumeroCarta == null || rawNumeroCarta.isBlank()) {
            this.numeroCarta = null;
            return;
        }

        String pulito = sanitize(rawNumeroCarta);

        if (pulito.length() != 16 || !pulito.matches("\\d+")) {
            throw new InvalidCardInputExceptionBrondi(
                    "Formato numero carta non valido.",
                    "Il numero carta deve essere di 16 cifre numeriche.",
                    BrondiValidationException.Severity.HIGH
            );
        }

        this.numeroCarta = pulito;
    }

    public void setScadenzaCarta(String rawScadenza) throws InvalidCardInputExceptionBrondi {

        if (rawScadenza == null || rawScadenza.isBlank()) {
            this.scadenzaCarta = null;
            return;
        }

        String scadenza = sanitize(rawScadenza);

        boolean isFormatoCorto = scadenza.matches("^(0[1-9]|1[0-2])/\\d{2}$");

        boolean isFormatoLungo = scadenza.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$");

        if (!isFormatoCorto && !isFormatoLungo) {
            throw new InvalidCardInputExceptionBrondi(
                    "Formato scadenza carta non valido.",
                    "La scadenza deve essere nel formato MM/YY oppure YYYY-MM-DD.",
                    BrondiValidationException.Severity.HIGH
            );
        }

        this.scadenzaCarta = scadenza;
    }

    public void setCvvCarta(String rawCvv) throws InvalidCardInputExceptionBrondi {

        if (rawCvv == null || rawCvv.isBlank()) {
            this.cvv = null;
            return;
        }

        String cvvPulito = sanitize(rawCvv);

        if (cvvPulito.length() != 3 || !cvvPulito.matches("\\d{3}")) {
            throw new InvalidCardInputExceptionBrondi(
                    "Formato CVV non valido.",
                    "Il CVV deve essere composto da 3 cifre numeriche.",
                    BrondiValidationException.Severity.HIGH
            );
        }
        this.cvv = cvvPulito;
    }

    public void setEmailPaypal(String rawEmail) throws InvalidCardInputExceptionBrondi {

        if (rawEmail == null || rawEmail.isBlank()) {
            this.emailPaypal = null;
            return;
        }

        String emailPaypall = sanitize(rawEmail);

        if (!emailPaypall.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidCardInputExceptionBrondi(
                    "Formato email Paypal non valido.",
                    "Email non valida: " + emailPaypal,
                    BrondiValidationException.Severity.HIGH
            );
        }

        this.emailPaypal = emailPaypall;
    }

    public void setCodicePaypal(String rawCodice) {
        if (rawCodice == null || rawCodice.isBlank()) {
            this.codicePaypal = null;
            return;
        }

        this.codicePaypal = sanitize(rawCodice);
    }

}