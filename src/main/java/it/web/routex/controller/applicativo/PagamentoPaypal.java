package it.web.routex.controller.applicativo;
import it.web.routex.bean.PaymentResultBean;
import it.web.routex.enumerator.PaymentMethod;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.model.Paypal;
import it.web.routex.utility.factory.LayerPersistenza;
import it.web.routex.utility.decorator.decoratorticket.BaseTicketCode;
import it.web.routex.utility.decorator.decoratorticket.CittaDecorator;
import it.web.routex.utility.decorator.decoratorticket.Component;
import it.web.routex.utility.decorator.decoratorticket.TimestampDecorator;
import it.web.routex.utility.singleton.Credentials;
import it.web.routex.exception.CredentialsExceptionBrondi;
import it.web.routex.exception.PaymentValidationExceptionBrondi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PagamentoPaypal extends RegistrazionePagamentoController
{
    String email;
    String codice;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public PaymentResultBean run() throws DAOExceptionBrondi, PaymentValidationExceptionBrondi, CredentialsExceptionBrondi
    {
        final List<String> codiciBiglietti;

        it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
        Paypal p = layer.getPaymentPaypal(email,codice);

        if (p == null) {
            throw new PaymentValidationExceptionBrondi(
                    "Nessun pagamento Paypal trovato per i dati inseriti.",
                    PaymentMethod.PAYPAL,
                    "PagamentoPaypal.run"
            );
        }

        p.validate();

        Component gen = new TimestampDecorator(new CittaDecorator(new BaseTicketCode(), city));

        codiciBiglietti = new ArrayList<>();

        for (int i = 0; i < quantitativo; i++) {
            codiciBiglietti.add(gen.genera());
        }

        registraPagamentoPermanente(codiciBiglietti,p);

        return new PaymentResultBean(
                city,
                totale,
                p.getMethod().getDisplayName(),
                codiciBiglietti,
                quantitativo
        );
    }

    public PagamentoPaypal(String email, String codiceTransazione, Credentials cred,double tot, int quantita, String citta)
    {
        super(tot, quantita, citta, cred);
        this.email = email;
        this.codice = codiceTransazione;

    }

    private void registraPagamentoPermanente(List<String> codiciBiglietti, Paypal paypal) throws CredentialsExceptionBrondi {
        if (credenziali == null) {
            throw new CredentialsExceptionBrondi("Nessun utente loggato associato al pagamento.", "Errore nel PagamentoPaypal.java");
        }
        it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
        layer.salvataggio(credenziali, codiciBiglietti, paypal.getMethod().getDisplayName(), city);
        if(logger.isInfoEnabled()) {
            logger.info("Pagamento effettuato con Paypal {}", paypal.maskedAccount());
        }
    }
}
