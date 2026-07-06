package it.web.routex.boundary.cli.controller.grafico;
import it.web.routex.bean.MastercardBean;
import it.web.routex.bean.PaymentResultBean;
import it.web.routex.bean.PaypalBean;
import it.web.routex.boundary.cli.LoggedCLI;
import it.web.routex.boundary.cli.view.*;
import it.web.routex.controller.applicativo.PagamentoMastercard;
import it.web.routex.controller.applicativo.PagamentoPaypal;
import it.web.routex.controller.applicativo.RegistrazionePagamentoController;
import it.web.routex.exception.*;
import it.web.routex.enumerator.TypesOfPersistenceLayer;
import it.web.routex.utility.singleton.Credentials;
import it.web.routex.utility.singleton.PersistenceMode;

public class ConfermaPagamentoControllerGraficoCLI extends LoggedCLI {

    public void doPost() {

        Credentials cred = Credentials.getInstanceSingleton();
        logUtente(cred);


        PaymentResultBean paymentRecord = estraiPagamento();
        if (paymentRecord == null) return;

        logPersistenzaScelta();

        RegistrazionePagamentoController controllerPagamento = creaControllerPagamento(paymentRecord,cred);

        if (controllerPagamento == null) return;

        PaymentResultBean result = eseguiPagamento(controllerPagamento);

        if (result == null) return;

        mostraSuccesso(result);
    }

    private PaymentResultBean estraiPagamento()
    {
        try{

            PaymentResultBean prb = new PaymentResultBean();

            String rawCity = ConfermaPagamentoCLI.getCity();
            String rawQuantity = ConfermaPagamentoCLI.getQuantity();
            String rawTotale = String.valueOf(ConfermaPagamentoCLI.getPrezzoTotale());
            String rawMetodoPagamento = ConfermaPagamentoCLI.getMetodoPagamento();
            String rawPersistenza = ConfermaPagamentoCLI.getPersistenza();

            prb.setCity(rawCity);
            prb.setQuantity(rawQuantity);
            prb.setTotale(rawTotale);
            prb.setMetodoPagamento(rawMetodoPagamento);

            if (rawPersistenza == null) {
                throw new InvalidPaymentInputExceptionRemoli(
                        "Campo mancante: persistence.",
                        "Parametro 'persistence' è null.",
                        InvalidPaymentInputExceptionRemoli.Severity.LOW
                );
            }

            switch (rawPersistenza) {
                case "JDBC" -> PersistenceMode.getSingletonInstance().setTipo(TypesOfPersistenceLayer.JDBC);
                case "FileSystem" -> PersistenceMode.getSingletonInstance().setTipo(TypesOfPersistenceLayer.FILE_SYSTEM);
                default -> throw new InvalidPaymentInputExceptionRemoli(
                        "Tipo di persistenza non valido.",
                        "Parametro persistence='" + rawPersistenza + "' non riconosciuto.",
                        InvalidPaymentInputExceptionRemoli.Severity.HIGH
                );
            }

            return prb;
        }catch(InvalidPaymentInputExceptionRemoli e) {
            GenericErrorCLI.mostraErrore("Errore nell'input del pagamento"+ e.getUserMessage());
            return null;
        } catch (InvalidCardInputExceptionRemoli | InvalidBuyTicketInputExceptionRemoli e) {
            throw new RuntimeException(e);
        }
    }

    private void logUtente(Credentials cred) {
        logger.info("[PROCESSAMENTO PAGAMENTO] Utente loggato: nome={}, cognome={}, ruolo={}",
                cred.getNome(), cred.getCognome(), cred.getRuolo());
    }

    private void logPersistenzaScelta() {
        TypesOfPersistenceLayer persistenceLayer = PersistenceMode.getSingletonInstance().getTipo();
        logger.info("Tipo di persistenza scelto: {}", persistenceLayer);
    }
    /*private void impostaPersistenza(PaymentResultBean paymentRecord) {
        TypesOfPersistenceLayer persistenceLayer = paymentRecord.persistenceLayer();
        logger.info("Tipo di persistenza scelto {}", persistenceLayer);
        PersistenceMode.getSingletonInstance().setTipo(persistenceLayer);
    }*/
    private RegistrazionePagamentoController creaControllerPagamento(PaymentResultBean paymentRecord, Credentials cred) {

        String metodo = paymentRecord.getPaymentMethod().toLowerCase();

        return switch (metodo) {
            case "mastercard" -> creaPagamentoMastercard(paymentRecord, cred);
            case "paypal"     -> creaPagamentoPaypal(paymentRecord, cred);
            default           -> gestisciMetodoNonValido();
        };
    }
    private RegistrazionePagamentoController creaPagamentoMastercard(PaymentResultBean paymentRecord, Credentials cred) {

        try {

            String rawNumero = MastercardCLI.getNumeroCarta();
            String rawScadenza = MastercardCLI.getScadenza();
            String rawCvv = MastercardCLI.getCvv();

            MastercardBean mb = new MastercardBean();

            mb.setNumero(rawNumero);
            mb.setNumero(rawScadenza);
            mb.setNumero(rawCvv);

            return new PagamentoMastercard(
                    mb.getNumero(),
                    mb.getScadenza(),
                    mb.getCvv(),
                    cred,
                    paymentRecord.getTotal(),
                    paymentRecord.getQuantity(),
                    paymentRecord.getCity()
            );

        } catch (InvalidCardInputExceptionRemoli e) {
            gestisciErroreInput(e.getUserMessage(), "Errore nei dati Mastercard", e);
            return null;
        }
    }
    private RegistrazionePagamentoController creaPagamentoPaypal(PaymentResultBean paymentRecord, Credentials cred) {

        try {

            String email = PaypalCLI.getEmailPaypal();
            String codice = PaypalCLI.getCodiceTransazione();

            PaypalBean pb = new PaypalBean();

            pb.setEmail(email);
            pb.setCodice(codice);

            return new PagamentoPaypal(
                    pb.getEmail(),
                    pb.getCodice(),
                    cred,
                    paymentRecord.getTotal(),
                    paymentRecord.getQuantity(),
                    paymentRecord.getCity()
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
