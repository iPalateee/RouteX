package it.web.routex.bean;

import it.web.routex.exception.InvalidLoginInputExceptionBrondi;

import static it.web.routex.utility.text.TextUtils.sanitize;

public class AutenticazioneBean {

    private String email;
    private String password;

    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setEmail(String rawEmail) throws InvalidLoginInputExceptionBrondi {
        if (rawEmail == null)
            throw new InvalidLoginInputExceptionBrondi(
                    "Il campo email non è stato inviato dal form.",
                    "Parametro 'Email' null.",
                    InvalidLoginInputExceptionBrondi.Severity.LOW
            );

        String cleanEmail = sanitize(rawEmail);

        if (cleanEmail.isBlank())
            throw new InvalidLoginInputExceptionBrondi(
                    "Email mancante.",
                    "Email è blank dopo sanitizzazione.",
                    InvalidLoginInputExceptionBrondi.Severity.MEDIUM
            );

        if (!cleanEmail.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            throw new InvalidLoginInputExceptionBrondi(
                    "Inserisci una email valida.",
                    "Regex email non rispettata: " + cleanEmail,
                    InvalidLoginInputExceptionBrondi.Severity.MEDIUM
            );
        }

        this.email = cleanEmail;
    }

    public void setPassword(String rawPassword) throws InvalidLoginInputExceptionBrondi {
        if (rawPassword == null)
            throw new InvalidLoginInputExceptionBrondi(
                    "Il campo password non è stato inviato dal form.",
                    "Parametro 'Password' null dal form.",
                    InvalidLoginInputExceptionBrondi.Severity.LOW
            );

        String cleanPassword = sanitize(rawPassword);

        if (cleanPassword.isBlank()) {
            throw new InvalidLoginInputExceptionBrondi(
                    "La password non deve essere vuota.",
                    "Password blank dopo sanitizzazione.",
                    InvalidLoginInputExceptionBrondi.Severity.MEDIUM
            );
        }

        this.password = cleanPassword;
    }
}