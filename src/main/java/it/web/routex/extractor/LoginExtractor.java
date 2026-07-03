/*package it.web.routex.extractor;

import it.web.routex.record.LoginRecord;
import it.web.routex.exception.InvalidLoginInputExceptionRemoli;

import javax.servlet.http.HttpServletRequest;

import static it.web.routex.extractor.RouteInputExtractor.sanitize;

public final class LoginExtractor {

    private LoginExtractor()
    {
        throw new AssertionError("Classe di estrazione dati, non si creano new");
    }

    /*public static LoginRecord from(String rawEmail, String rawPassword) throws InvalidLoginInputExceptionRemoli {

        // PARAMETRI NULL


        //sanitize





        if(password.isBlank() && !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$") )
            throw new InvalidLoginInputExceptionRemoli(
                    "Campo 'password' vuoto e email non conforme allo standard.",
                    "Email = non conforme, password = null.",
                    InvalidLoginInputExceptionRemoli.Severity.MEDIUM
            );

        return new LoginRecord(email, password);
    }
}*/
