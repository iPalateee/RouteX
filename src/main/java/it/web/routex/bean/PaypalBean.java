package it.web.routex.bean;

import it.web.routex.exception.InvalidCardInputExceptionRemoli;

import static it.web.routex.utility.text.TextUtils.*;

public class PaypalBean {

    private String codice;
    private String email;

    public String getCodice(){
        return this.codice;
    }

    public String getEmail(){
        return this.email;
    }

    public void setCodice(String c) throws InvalidCardInputExceptionRemoli {

        String codicee = sanitizeParam(c);

        if (codicee.isBlank()) {
            error("Il codice transazione è obbligatorio.");
        }
        
        if (!codicee.matches("^TXN-[A-Za-z0-9]{6,20}$")) {
            error("Il codice transazione non è valido.");
        }
        
        this.codice = codicee;
    }

    public void setEmail(String e) throws InvalidCardInputExceptionRemoli {

        String emaill = sanitizeParam(e);

        if (emaill.isBlank()) {
            error("Inserisci la tua email PayPal.");
        }
        if (!emaill.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            error("L'email PayPal inserita non è valida.");
        }

        this.email = emaill;
    }

}
