package it.web.routex.utility.factory;

import it.web.routex.bean.PaymentResultBean;
import it.web.routex.controller.applicativo.ConfermaPagamentoMastercardControllerApplicativo;
import it.web.routex.controller.applicativo.ConfermaPagamentoPaypalControllerApplicativo;
import it.web.routex.controller.applicativo.RegistrazionePagamentoController;

public class FactoryPagamento {

    public static RegistrazionePagamentoController createController(PaymentResultBean payment) {

        String metodo = payment.getPaymentMethod().toLowerCase();

        switch (metodo) {
            case "mastercard":
                return new ConfermaPagamentoMastercardControllerApplicativo(payment);
            case "paypal":
                return new ConfermaPagamentoPaypalControllerApplicativo(payment);
            default:
                throw new IllegalArgumentException("Metodo di pagamento non supportato: " + metodo);
        }
    }
}