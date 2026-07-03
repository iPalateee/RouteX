package it.web.routex.boundary.cli.controller.grafico;
import it.web.routex.bean.PaymentResultBean;
import it.web.routex.boundary.cli.LoggedCLI;
import it.web.routex.boundary.cli.extractor.MastercardExtractorCLI;
import it.web.routex.boundary.cli.extractor.PagamentoExtractorCLI;
import it.web.routex.boundary.cli.extractor.PaypalExtractorCLI;
import it.web.routex.boundary.cli.view.ErroreLoginCLI;
import it.web.routex.boundary.cli.view.GenericErrorCLI;
import it.web.routex.boundary.cli.view.SuccessoPagamentoCLI;
import it.web.routex.controller.applicativo.PagamentoMastercard;
import it.web.routex.controller.applicativo.PagamentoPaypal;
import it.web.routex.controller.applicativo.RegistrazionePagamentoController;
import it.web.routex.record.MastercardRecord;
import it.web.routex.record.PaymentRecord;
import it.web.routex.enumerator.TypesOfPersistenceLayer;
import it.web.routex.record.PaypalRecord;
import it.web.routex.utility.singleton.Credentials;
import it.web.routex.exception.PaymentValidationExceptionRemoli;
import it.web.routex.exception.CredentialsExceptionRemoli;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.utility.singleton.PersistenceMode;
import it.web.routex.exception.InvalidPaymentInputExceptionRemoli;
import it.web.routex.exception.InvalidCardInputExceptionRemoli;

public class ConfermaPagamentoControllerGraficoCLI extends LoggedCLI {

    public void doPost() {

        Credentials cred = Credentials.getInstanceSingleton();
        logUtente(cred);


        PaymentRecord paymentRecord = estraiPagamento();
        if (paymentRecord == null) return;

        impostaPersistenza(paymentRecord);

        RegistrazionePagamentoController controllerPagamento = creaControllerPagamento(paymentRecord,cred);

        if (controllerPagamento == null) return;

        PaymentResultBean result = eseguiPagamento(controllerPagamento);

        if (result == null) return;

        mostraSuccesso(result);
    }

    private PaymentRecord estraiPagamento()
    {
        try{
            return PagamentoExtractorCLI.from();
        }catch(InvalidPaymentInputExceptionRemoli e) {
            GenericErrorCLI.mostraErrore("Errore nell'input del pagamento"+ e.getUserMessage());
            return null;
        }
    }

    private void logUtente(Credentials cred) {
        logger.info("[PROCESSAMENTO PAGAMENTO] Utente loggato: nome={}, cognome={}, ruolo={}",
                cred.getNome(), cred.getCognome(), cred.getRuolo());
    }
    private void impostaPersistenza(PaymentRecord paymentRecord) {
        TypesOfPersistenceLayer persistenceLayer = paymentRecord.persistenceLayer();
        logger.info("Tipo di persistenza scelto {}", persistenceLayer);
        PersistenceMode.getSingletonInstance().setTipo(persistenceLayer);
    }
    private RegistrazionePagamentoController creaControllerPagamento(PaymentRecord paymentRecord, Credentials cred) {

        String metodo = paymentRecord.method().toLowerCase();

        return switch (metodo) {
            case "mastercard" -> creaPagamentoMastercard(paymentRecord, cred);
            case "paypal"     -> creaPagamentoPaypal(paymentRecord, cred);
            default           -> gestisciMetodoNonValido();
        };
    }
    private RegistrazionePagamentoController creaPagamentoMastercard(PaymentRecord paymentRecord, Credentials cred) {

        try {
            MastercardRecord master = MastercardExtractorCLI.from();
            return new PagamentoMastercard(
                    master.numero_carta(),
                    master.scadenza(),
                    master.cvv(),
                    cred,
                    paymentRecord.total(),
                    paymentRecord.quantity(),
                    paymentRecord.city()
            );
        } catch (InvalidCardInputExceptionRemoli e) {
            gestisciErroreInput(e.getUserMessage(), "Errore nei dati Mastercard", e);
            return null;
        }
    }
    private RegistrazionePagamentoController creaPagamentoPaypal(PaymentRecord paymentRecord, Credentials cred) {

        try {
            PaypalRecord pay = PaypalExtractorCLI.from();
            return new PagamentoPaypal(
                    pay.email_paypal(),
                    pay.codice_transazione(),
                    cred,
                    paymentRecord.total(),
                    paymentRecord.quantity(),
                    paymentRecord.city()
            );
        } catch (InvalidCardInputExceptionRemoli e) {
            gestisciErroreInput(e.getUserMessage(), "Errore nei dati Paypal", e);
            return null;
        }
    }
    private RegistrazionePagamentoController gestisciMetodoNonValido() {

        ErroreLoginCLI.mostraErrore("Errore, non è stato scelto un metodo opportuno di pagamento");
        return null;
    }
    private PaymentResultBean eseguiPagamento(RegistrazionePagamentoController controllerPagamento) {

        try {
            return controllerPagamento.run();

        } catch (PaymentValidationExceptionRemoli | DAOExceptionBrondi | CredentialsExceptionRemoli e) {

            GenericErrorCLI.mostraErrore(e.getMessage());
            logger.error("Errore durante il pagamento", e);
            return null;

        } catch (Exception e) {

            GenericErrorCLI.mostraErrore("Errore generico conferma pagamento"+e.getMessage());
            logger.error("Errore generico conferma pagamento", e);
            return null;
        }
    }
    private void mostraSuccesso(PaymentResultBean pay) {

        SuccessoPagamentoCLI.setCity(pay.getCity());
        SuccessoPagamentoCLI.setQuantity(String.valueOf(pay.getQuantity()));
        SuccessoPagamentoCLI.setTotale(pay.getTotal());
        SuccessoPagamentoCLI.setMetodo(pay.getPaymentMethod());
        SuccessoPagamentoCLI.setMessaggio("Pagamento completato");
        SuccessoPagamentoCLI.setCodiciBiglietti( pay.getTicketCodes());
        SuccessoPagamentoCLI.stampa();

    }
    private void gestisciErroreInput(String messaggio, String log, Exception e) {

        GenericErrorCLI.mostraErrore(messaggio);
        logger.error(log, e);

    }
}
