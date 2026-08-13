package it.web.routex.controller.applicativo;

import it.web.routex.bean.PaymentResultBean;
import it.web.routex.exception.CredentialsExceptionBrondi;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.PaymentValidationExceptionBrondi;
import it.web.routex.utility.singleton.Credentials;

public abstract class RegistrazionePagamentoController
{
    double totale;
    String city;
    Credentials credenziali;
    int quantitativo;

    protected RegistrazionePagamentoController(PaymentResultBean payment)
    {
        this.totale = payment.getTotal();
        this.quantitativo = payment.getQuantity();
        this.city = payment.getCity();
        this.credenziali = Credentials.getInstanceSingleton();
    }

    public abstract PaymentResultBean run() throws DAOExceptionBrondi, PaymentValidationExceptionBrondi, CredentialsExceptionBrondi;
}
