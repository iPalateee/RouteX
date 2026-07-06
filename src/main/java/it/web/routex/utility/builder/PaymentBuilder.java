package it.web.routex.utility.builder;

import it.web.routex.bean.PaymentResultBean;
import it.web.routex.enumerator.TypesOfPersistenceLayer;
import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;
import it.web.routex.exception.InvalidCardInputExceptionRemoli;
import it.web.routex.exception.InvalidPaymentInputExceptionBrondi;
import it.web.routex.utility.singleton.PersistenceMode;

public class PaymentBuilder {

    private final PaymentResultBean prb;

    private String rawPersistenza;

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

    public PaymentBuilder withMetodoPagamento(String rawMetodoPagamento) throws InvalidCardInputExceptionRemoli {
        this.prb.setMetodoPagamento(rawMetodoPagamento);
        return this;
    }

    public PaymentBuilder withPersistenza(String rawPersistenza) {
        this.rawPersistenza = rawPersistenza;
        return this;
    }


    public PaymentResultBean build() throws InvalidPaymentInputExceptionBrondi {

        if (this.rawPersistenza == null) {
            throw new InvalidPaymentInputExceptionBrondi(
                    "Campo mancante: persistence.",
                    "Parametro 'persistence' è null.",
                    InvalidPaymentInputExceptionBrondi.Severity.LOW
            );
        }

        switch (this.rawPersistenza) {
            case "JDBC" -> PersistenceMode.getSingletonInstance().setTipo(TypesOfPersistenceLayer.JDBC);
            case "FileSystem" -> PersistenceMode.getSingletonInstance().setTipo(TypesOfPersistenceLayer.FILE_SYSTEM);
            default -> throw new InvalidPaymentInputExceptionBrondi(
                    "Tipo di persistenza non valido.",
                    "Parametro persistence='" + this.rawPersistenza + "' non riconosciuto.",
                    InvalidPaymentInputExceptionBrondi.Severity.HIGH
            );
        }

        return this.prb;
    }
}