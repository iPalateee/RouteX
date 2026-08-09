package it.web.routex.utility.builder;

import it.web.routex.bean.PaymentResultBean;
import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;
import it.web.routex.exception.InvalidCardInputExceptionBrondi;
import it.web.routex.exception.InvalidPaymentInputExceptionBrondi;

public class PaymentBuilder {

    private final PaymentResultBean prb;

    public PaymentBuilder() {
        this.prb = new PaymentResultBean();
    }

    public PaymentBuilder withCity(String rawCity) throws InvalidBuyTicketInputExceptionBrondi {
        this.prb.setCity(rawCity);
        return this;
    }

    public PaymentBuilder withQuantity(String rawQuantity) throws InvalidBuyTicketInputExceptionBrondi {
        this.prb.setQuantity(rawQuantity);
        return this;
    }

    public PaymentBuilder withTotale(String rawTotale) throws InvalidBuyTicketInputExceptionBrondi {
        this.prb.setTotale(rawTotale);
        return this;
    }

    public PaymentBuilder withMetodoPagamento(String rawMetodoPagamento) throws InvalidCardInputExceptionBrondi {
        this.prb.setMetodoPagamento(rawMetodoPagamento);
        return this;
    }

    public PaymentBuilder withPersistenza(String rawPersistenza) throws InvalidPaymentInputExceptionBrondi {
        this.prb.setPersistenza(rawPersistenza);
        return this;
    }

    public PaymentBuilder withNumeroCarta(String rawNumeroCarta) throws InvalidCardInputExceptionBrondi {
        this.prb.setNumeroCarta(rawNumeroCarta);
        return this;
    }

    public PaymentBuilder withScadenzaCarta(String rawScadenza) throws InvalidCardInputExceptionBrondi {
        this.prb.setScadenzaCarta(rawScadenza);
        return this;
    }

    public PaymentBuilder withCvvCarta(String rawCvv) throws InvalidCardInputExceptionBrondi {
        this.prb.setCvvCarta(rawCvv);
        return this;
    }

    public PaymentBuilder withEmailPaypal(String rawEmail) throws InvalidCardInputExceptionBrondi {
        this.prb.setEmailPaypal(rawEmail);
        return this;
    }

    public PaymentBuilder withCodicePaypal(String rawCodice) {
        this.prb.setCodicePaypal(rawCodice);
        return this;
    }

    public PaymentResultBean build() {
        return this.prb;
    }
}