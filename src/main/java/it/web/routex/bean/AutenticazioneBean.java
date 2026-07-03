package it.web.routex.bean;

import it.web.routex.exception.InvalidLoginInputExceptionRemoli;

import static it.web.routex.utility.text.TextUtils.sanitize;

public class AutenticazioneBean
{
    private String email;
    private String password;


    public AutenticazioneBean() {
        //Costruttore vuoto
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String rawPassword) throws InvalidLoginInputExceptionRemoli {

        if (rawPassword == null)
            throw new InvalidLoginInputExceptionRemoli(
                    "Il campo password non è stato inviato dal form.",
                    "Parametro 'Password' null dal form.",
                    InvalidLoginInputExceptionRemoli.Severity.LOW
            );

        String password = sanitize(rawPassword);

        if (password.isBlank()) {
            throw new InvalidLoginInputExceptionRemoli(
                    "Password mancante.",
                    "Password = null.",
                    InvalidLoginInputExceptionRemoli.Severity.MEDIUM
            );
        }

        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String rawEmail) throws InvalidLoginInputExceptionRemoli {
        if (rawEmail == null)
            throw new InvalidLoginInputExceptionRemoli(
                    "Il campo email non è stato inviato dal form.",
                    "Parametro 'Email' null dal form.",
                    InvalidLoginInputExceptionRemoli.Severity.LOW
            );
        String email = sanitize(rawEmail);

        if (email.isBlank())
            throw new InvalidLoginInputExceptionRemoli(
                    "Email mancante.",
                    "Email = null",
                    InvalidLoginInputExceptionRemoli.Severity.MEDIUM
            );

        if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            throw new InvalidLoginInputExceptionRemoli(
                    "Inserisci un'email valida.",
                    "Regex email non rispettata: " + email,
                    InvalidLoginInputExceptionRemoli.Severity.MEDIUM
            );
        }

        this.email = email;
    }
}
