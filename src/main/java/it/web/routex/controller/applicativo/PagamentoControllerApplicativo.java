package it.web.routex.controller.applicativo;

import it.web.routex.bean.PaymentResultBean;
import it.web.routex.exception.CredentialsExceptionBrondi;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.PaymentValidationExceptionBrondi;
import it.web.routex.utility.factory.FactoryPagamento;

public class PagamentoControllerApplicativo {

    public PaymentResultBean eseguiPagamento(PaymentResultBean payment)
            throws DAOExceptionBrondi, PaymentValidationExceptionBrondi, CredentialsExceptionBrondi {

        RegistrazionePagamentoController pagamento = FactoryPagamento.createController(payment);

        return pagamento.run();
    }
}