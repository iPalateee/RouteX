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
import it.web.routex.utility.singleton.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PagamentoMastercard extends RegistrazionePagamentoController
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
                    "PagamentoMastercard.run"
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
    public PagamentoMastercard(String numeroCarta, String scadenza, String cvv, Credentials cred,double tot, int quantita, String citta )
    {
        super(tot, quantita, citta, cred);
        this.numeroCarta = numeroCarta;
        this.scadenza = scadenza;
        this.cvv = cvv;
    }

    private void registraPagamentoPermanente(List<String> codiciBiglietti, Mastercard mastercard) throws CredentialsExceptionBrondi {
        if (credenziali == null) {
            throw new CredentialsExceptionBrondi("Nessun utente loggato associato al pagamento.", "Errore nel PagamentoMastercard.java");
        }
        it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
        layer.salvataggio(credenziali, codiciBiglietti, mastercard.getMethod().getDisplayName(), city);
        if (logger.isInfoEnabled()) {
            logger.info("Pagamento effettuato con Mastercard {}", mastercard.maskedNumber());
        }

    }
}


