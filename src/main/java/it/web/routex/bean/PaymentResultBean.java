package it.web.routex.bean;

import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;
import it.web.routex.exception.InvalidCardInputExceptionBrondi;

import java.util.List;

import static it.web.routex.utility.text.TextUtils.*;

public class PaymentResultBean {

    private String city;
    private double total;
    private String paymentMethod;
    private final List<String> ticketCodes;
    private int quantity;

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
}