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

    protected RegistrazionePagamentoController(double tot, int quantita, String city, Credentials cred)
    {
        this.totale = tot;
        this.quantitativo = quantita;
        this.city = city;
        this.credenziali = cred;
    }
    public abstract PaymentResultBean run() throws DAOExceptionBrondi, PaymentValidationExceptionBrondi, CredentialsExceptionBrondi;
}
