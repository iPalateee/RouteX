package it.web.routex.boundary.cli.controller.grafico;

import it.web.routex.bean.PaymentResultBean;
import it.web.routex.boundary.cli.LoggedCLI;
import it.web.routex.boundary.cli.view.*;
import it.web.routex.controller.applicativo.PagamentoControllerApplicativo;
import it.web.routex.exception.*;
import it.web.routex.enumerator.TypesOfPersistenceLayer;
import it.web.routex.utility.builder.PaymentBuilder;
import it.web.routex.utility.singleton.Credentials;
import it.web.routex.utility.singleton.PersistenceMode;

public class ConfermaPagamentoControllerGraficoCLI extends LoggedCLI {

    public void doPost() {

        Credentials cred = Credentials.getInstanceSingleton();
        logUtente(cred);

        PaymentResultBean payment = estraiPagamento();
        if (payment == null) return;

        logPersistenzaScelta();

        PagamentoControllerApplicativo pag = new PagamentoControllerApplicativo();
        PaymentResultBean result = eseguiPagamento(pag, payment);

        if (result == null) return;

        mostraSuccesso(result);
    }

    private PaymentResultBean estraiPagamento() {
        try {
            String metodoPagamento = ConfermaPagamentoCLI.getMetodoPagamento();

            PaymentBuilder builder = new PaymentBuilder()
                    .withCity(ConfermaPagamentoCLI.getCity())
                    .withQuantity(ConfermaPagamentoCLI.getQuantity())
                    .withTotale(String.valueOf(ConfermaPagamentoCLI.getPrezzoTotale()))
                    .withMetodoPagamento(metodoPagamento)
                    .withPersistenza(ConfermaPagamentoCLI.getPersistenza());

            if ("mastercard".equalsIgnoreCase(metodoPagamento)) {
                builder.withNumeroCarta(MastercardCLI.getNumeroCarta())
                        .withScadenzaCarta(MastercardCLI.getScadenza())
                        .withCvvCarta(MastercardCLI.getCvv());
            } else if ("paypal".equalsIgnoreCase(metodoPagamento)) {
                builder.withEmailPaypal(PaypalCLI.getEmailPaypal())
                        .withCodicePaypal(PaypalCLI.getCodiceTransazione());
            } else {
                ErroreLoginCLI.mostraErrore("Errore, non è stato scelto un metodo opportuno di pagamento");
                return null;
            }

            return builder.build();

        } catch (InvalidPaymentInputExceptionBrondi | InvalidCardInputExceptionBrondi | InvalidBuyTicketInputExceptionBrondi e) {
            gestisciErroreInput(e.getUserMessage(), "Errore nell'input del pagamento", e);
            return null;
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

    private PaymentResultBean eseguiPagamento(PagamentoControllerApplicativo facciata, PaymentResultBean paymentRecord) {
        try {
            return facciata.eseguiPagamento(paymentRecord);

        } catch (PaymentValidationExceptionBrondi | DAOExceptionBrondi | CredentialsExceptionBrondi e) {
            GenericErrorCLI.mostraErrore(e.getMessage());
            logger.error("Errore durante il pagamento", e);
            return null;
        } catch (Exception e) {
            GenericErrorCLI.mostraErrore("Errore generico conferma pagamento: " + e.getMessage());
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
        SuccessoPagamentoCLI.setCodiciBiglietti(pay.getTicketCodes());
        SuccessoPagamentoCLI.stampa();
    }

    private void gestisciErroreInput(String messaggio, String log, Exception e) {
        GenericErrorCLI.mostraErrore(messaggio);
        logger.error(log, e);
    }
}