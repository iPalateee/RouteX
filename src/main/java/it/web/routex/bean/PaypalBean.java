package it.web.routex.bean;

import it.web.routex.exception.InvalidCardInputExceptionBrondi;

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

    public void setCodice(String c) throws InvalidCardInputExceptionBrondi {

        String codicee = sanitizeParam(c);

        if (codicee.isBlank()) {
            throw error("Il codice transazione è obbligatorio.");
        }
        
        if (!codicee.matches("^TXN-[A-Za-z0-9]{6,20}$")) {
            throw error("Il codice transazione non è valido.");
        }
        
        this.codice = codicee;
    }

    public void setEmail(String e) throws InvalidCardInputExceptionBrondi {

        String emaill = sanitizeParam(e);

        if (emaill.isBlank()) {
            throw error("Inserisci la tua email PayPal.");
        }
        if (!emaill.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            throw error("L'email PayPal inserita non è valida.");
        }

        this.email = emaill;
    }

}
