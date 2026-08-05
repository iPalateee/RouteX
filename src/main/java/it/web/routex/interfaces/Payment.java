package it.web.routex.interfaces;

import it.web.routex.enumerator.PaymentMethod;
import it.web.routex.exception.PaymentValidationExceptionBrondi;

public interface Payment {

    PaymentMethod getMethod();

    boolean isValid();

    void validate() throws PaymentValidationExceptionBrondi;
}
