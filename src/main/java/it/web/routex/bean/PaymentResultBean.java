package it.web.routex.bean;

import it.web.routex.exception.InvalidBuyTicketInputExceptionRemoli;
import it.web.routex.exception.InvalidCardInputExceptionRemoli;

import java.util.List;

import static it.web.routex.utility.text.TextUtils.*;

public class PaymentResultBean {

    private String city;
    private int quantity;
    private double total;
    private String paymentMethod;
    private final List<String> ticketCodes;

    public PaymentResultBean() {
        this.city = "";
        this.quantity = 0;
        this.total = 0.0;
        this.paymentMethod = "";
        this.ticketCodes = null;
    }

    public PaymentResultBean(
            String city,
            int quantity,
            double total,
            String paymentMethod,
            List<String> ticketCodes
    ) {
        this.city = city;
        this.quantity = quantity;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.ticketCodes = ticketCodes;
    }

    // --- GETTERS ---
    public String getCity() { return city; }
    public int getQuantity() { return quantity; }
    public double getTotal() { return total; }
    public String getPaymentMethod() { return paymentMethod; }
    public List<String> getTicketCodes() { return ticketCodes; }

    // --- SETTERS ---
    public void setCity(String c) throws InvalidBuyTicketInputExceptionRemoli {
        if (c == null)
            errorTicket("Il campo città non può essere nullo.", InvalidBuyTicketInputExceptionRemoli.Severity.MEDIUM);

        String citta = sanitize(c);

        if (citta.isBlank())
            errorTicket("Il campo città non può essere vuoto.", InvalidBuyTicketInputExceptionRemoli.Severity.MEDIUM);

        if (!citta.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]+$"))
            errorTicket("La città inserita non è valida.", InvalidBuyTicketInputExceptionRemoli.Severity.MEDIUM);

        this.city = citta;
    }

    public void setQuantity(String rawQuantity) throws InvalidBuyTicketInputExceptionRemoli {
        if (rawQuantity == null) {
            errorTicket("Il campo quantità non può essere vuoto.", InvalidBuyTicketInputExceptionRemoli.Severity.MEDIUM);
            return;
        }

        String sQuantita = sanitize(rawQuantity);
        int quantita;

        try {
            quantita = Integer.parseInt(sQuantita);
        } catch (NumberFormatException e) {
            errorTicket("La quantità inserita non è un numero valido.", InvalidBuyTicketInputExceptionRemoli.Severity.MEDIUM);
            return;
        }

        if (quantita <= 0)
            errorTicket("La quantità deve essere maggiore di zero.", InvalidBuyTicketInputExceptionRemoli.Severity.MEDIUM);

        if (quantita > 10)
            errorTicket("Puoi acquistare un massimo di 10 biglietti alla volta.", InvalidBuyTicketInputExceptionRemoli.Severity.HIGH);

        this.quantity = quantita;
    }

    public void setTotale(String rawTotale) throws InvalidBuyTicketInputExceptionRemoli {
        if (rawTotale == null || rawTotale.isBlank()) {
            errorTicket("Il totale non può essere nullo o vuoto.", InvalidBuyTicketInputExceptionRemoli.Severity.MEDIUM);
            return;
        }

        double parsedTotale;

        try {
            parsedTotale = Double.parseDouble(sanitize(rawTotale));
        } catch (NumberFormatException e) {
            errorTicket("Il totale inserito non è valido.", InvalidBuyTicketInputExceptionRemoli.Severity.MEDIUM);
            return;
        }

        if (parsedTotale < 0) {
            errorTicket("Il totale non può essere negativo.", InvalidBuyTicketInputExceptionRemoli.Severity.MEDIUM);
        }

        this.total = parsedTotale;
    }

    public void setMetodoPagamento(String rawMetodo) throws InvalidCardInputExceptionRemoli {
        if (rawMetodo == null || rawMetodo.isBlank()) {
            error("Metodo di pagamento vuoto.");
            return;
        }
        this.paymentMethod = sanitize(rawMetodo);
    }
}