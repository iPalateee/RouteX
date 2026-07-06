package it.web.routex.utility.builder;

import it.web.routex.bean.PaymentResultBean;
import it.web.routex.enumerator.TypesOfPersistenceLayer;
import it.web.routex.exception.InvalidBuyTicketInputExceptionRemoli;
import it.web.routex.exception.InvalidCardInputExceptionRemoli;
import it.web.routex.exception.InvalidPaymentInputExceptionRemoli;
import it.web.routex.utility.singleton.PersistenceMode;

public class PaymentBuilder {

    private final PaymentResultBean prb;

    private String rawPersistenza;

    public PaymentBuilder() {
        this.prb = new PaymentResultBean();
    }

    public PaymentBuilder withCity(String rawCity) throws InvalidBuyTicketInputExceptionRemoli {
        this.prb.setCity(rawCity);
        return this;
    }

    public PaymentBuilder withQuantity(String rawQuantity) throws InvalidBuyTicketInputExceptionRemoli {
        this.prb.setQuantity(rawQuantity);
        return this;
    }

    public PaymentBuilder withTotale(String rawTotale) throws InvalidBuyTicketInputExceptionRemoli {
        this.prb.setTotale(rawTotale);
        return this;
    }

    public PaymentBuilder withMetodoPagamento(String rawMetodoPagamento) throws InvalidCardInputExceptionRemoli {
        this.prb.setMetodoPagamento(rawMetodoPagamento);
        return this;
    }

    public PaymentBuilder withPersistenza(String rawPersistenza) {
        this.rawPersistenza = rawPersistenza;
        return this;
    }


    public PaymentResultBean build() throws InvalidPaymentInputExceptionRemoli {

        if (this.rawPersistenza == null) {
            throw new InvalidPaymentInputExceptionRemoli(
                    "Campo mancante: persistence.",
                    "Parametro 'persistence' è null.",
                    InvalidPaymentInputExceptionRemoli.Severity.LOW
            );
        }

        switch (this.rawPersistenza) {
            case "JDBC" -> PersistenceMode.getSingletonInstance().setTipo(TypesOfPersistenceLayer.JDBC);
            case "FileSystem" -> PersistenceMode.getSingletonInstance().setTipo(TypesOfPersistenceLayer.FILE_SYSTEM);
            default -> throw new InvalidPaymentInputExceptionRemoli(
                    "Tipo di persistenza non valido.",
                    "Parametro persistence='" + this.rawPersistenza + "' non riconosciuto.",
                    InvalidPaymentInputExceptionRemoli.Severity.HIGH
            );
        }

        return this.prb;
    }
}