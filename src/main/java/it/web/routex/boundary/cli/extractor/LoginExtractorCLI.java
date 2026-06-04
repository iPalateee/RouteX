package it.web.routex.boundary.cli.extractor;

import it.web.routex.boundary.cli.view.LoginViewCLI;
import it.web.routex.exception.InvalidLoginInputExceptionRemoli;
import it.web.routex.record.LoginRecord;


public final class LoginExtractorCLI {

    private LoginExtractorCLI(){

    }

    private static String sanitize(String s) {
        return (s == null) ? null : s.trim();
    }

    public static LoginRecord from() throws InvalidLoginInputExceptionRemoli {

        String emaill = LoginViewCLI.getEmailUtente();
        String passwordd = LoginViewCLI.getPasswordUtente();

        String email = sanitize(emaill);
        String password = sanitize(passwordd);

        if (email.isBlank() && password.isBlank())
            throw new InvalidLoginInputExceptionRemoli(
                    "Email e password mancanti.",
                    "Email = null, password = null.",
                    InvalidLoginInputExceptionRemoli.Severity.MEDIUM
            );

        if(password.isBlank() && !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$") )
            throw new InvalidLoginInputExceptionRemoli(
                    "Campo 'password' vuoto e email non conforme allo standard.",
                    "Email = non conforme, password = null.",
                    InvalidLoginInputExceptionRemoli.Severity.MEDIUM
            );


        if (email.isBlank())
            throw new InvalidLoginInputExceptionRemoli(
                    "Come pensi di autenticarti senza email? Io boh",
                    "Email è blank dopo sanitizzazione.",
                    InvalidLoginInputExceptionRemoli.Severity.MEDIUM
            );

        if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            throw new InvalidLoginInputExceptionRemoli(
                    "Ok che l'utente è scemo, ma non così tanto. Inserisci una email valida.",
                    "Regex email non rispettata: " + email,
                    InvalidLoginInputExceptionRemoli.Severity.MEDIUM
            );
        }
        if (password.isBlank())
            throw new InvalidLoginInputExceptionRemoli(
                    "Secondo te, la password può essere vuota? Sei uno scemo!",
                    "Password blank dopo sanitizzazione.",
                    InvalidLoginInputExceptionRemoli.Severity.MEDIUM
            );
        return new LoginRecord(email, password);
    }
}
