package it.web.routex.bean;

import it.web.routex.exception.InvalidLoginInputExceptionRemoli;
import static it.web.routex.utility.text.TextUtils.sanitize;

public class AutenticazioneBean {

    private String email;
    private String password;

    public AutenticazioneBean() {

    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setEmail(String rawEmail) throws InvalidLoginInputExceptionRemoli {
        if (rawEmail == null)
            throw new InvalidLoginInputExceptionRemoli(
                    "Il campo email non è stato inviato dal form.",
                    "Parametro 'Email' null.",
                    InvalidLoginInputExceptionRemoli.Severity.LOW
            );

        String cleanEmail = sanitize(rawEmail);

        if (cleanEmail.isBlank())
            throw new InvalidLoginInputExceptionRemoli(
                    "Email mancante.",
                    "Email è blank dopo sanitizzazione.",
                    InvalidLoginInputExceptionRemoli.Severity.MEDIUM
            );

        if (!cleanEmail.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            throw new InvalidLoginInputExceptionRemoli(
                    "Inserisci una email valida.",
                    "Regex email non rispettata: " + cleanEmail,
                    InvalidLoginInputExceptionRemoli.Severity.MEDIUM
            );
        }

        this.email = cleanEmail;
    }

    public void setPassword(String rawPassword) throws InvalidLoginInputExceptionRemoli {
        if (rawPassword == null)
            throw new InvalidLoginInputExceptionRemoli(
                    "Il campo password non è stato inviato dal form.",
                    "Parametro 'Password' null dal form.",
                    InvalidLoginInputExceptionRemoli.Severity.LOW
            );

        String cleanPassword = sanitize(rawPassword);

        if (cleanPassword.isBlank()) {
            throw new InvalidLoginInputExceptionRemoli(
                    "La password non deve essere vuota.",
                    "Password blank dopo sanitizzazione.",
                    InvalidLoginInputExceptionRemoli.Severity.MEDIUM
            );
        }

        this.password = cleanPassword;
    }
}