package it.web.routex.controller.applicativo;
import it.web.routex.bean.PaymentResultBean;
import it.web.routex.enumerator.PaymentMethod;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.CredentialsExceptionBrondi;
import it.web.routex.exception.PaymentValidationExceptionBrondi;
import it.web.routex.model.Mastercard;
import it.web.routex.utility.factory.LayerPersistenza;
import it.web.routex.utility.decorator.decoratorticket.BaseTicketCode;
import it.web.routex.utility.decorator.decoratorticket.CittaDecorator;
import it.web.routex.utility.decorator.decoratorticket.Component;
import it.web.routex.utility.decorator.decoratorticket.TimestampDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ConfermaPagamentoMastercardControllerApplicativo extends RegistrazionePagamentoController
{
    String numeroCarta;
    String scadenza;
    String cvv;
    private final Logger logger = LoggerFactory.getLogger(getClass());


    public PaymentResultBean run() throws DAOExceptionBrondi, PaymentValidationExceptionBrondi, CredentialsExceptionBrondi {

        final List<String> codiciBiglietti;

        it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
        Mastercard mastercard = layer.getPaymentMastercard(numeroCarta, scadenza, cvv);
        if (mastercard == null) {
            throw new PaymentValidationExceptionBrondi(
                    "Carta non valida o non presente nel sistema.",
                    PaymentMethod.MASTERCARD,
                    "ConfermaPagamentoMastercardControllerApplicativo.run"
            );
        }

        mastercard.validate();

        Component gen = new TimestampDecorator(new CittaDecorator(new BaseTicketCode(), city));

        codiciBiglietti = new ArrayList<>();

        for (int i = 0; i < quantitativo; i++) {
            codiciBiglietti.add(gen.genera());
        }

        registraPagamentoPermanente(codiciBiglietti, mastercard);

        return new PaymentResultBean(
                city,
                totale,
                mastercard.getMethod().getDisplayName(),
                codiciBiglietti,
                quantitativo
        );
    }
    public ConfermaPagamentoMastercardControllerApplicativo(PaymentResultBean payment)
    {
        super(payment);
        this.numeroCarta = payment.getNumeroCarta();
        this.scadenza = payment.getScadenzaCarta();
        this.cvv = payment.getCvv();
    }

    private void registraPagamentoPermanente(List<String> codiciBiglietti, Mastercard mastercard) throws CredentialsExceptionBrondi {
        if (credenziali == null) {
            throw new CredentialsExceptionBrondi("Nessun utente loggato associato al pagamento.", "Errore nel ConfermaPagamentoMastercardControllerApplicativo.java");
        }
        it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
        layer.salvataggio(credenziali, codiciBiglietti, mastercard.getMethod().getDisplayName(), city);
        if (logger.isInfoEnabled()) {
            logger.info("Pagamento effettuato con Mastercard {}", mastercard.maskedNumber());
        }

    }
}


