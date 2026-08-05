package it.web.routex.exception;

public class InvalidTicketExceptionBrondi extends Exception {

    private final String codiceTicket;

    public InvalidTicketExceptionBrondi(String userMessage,
                                        String details,
                                        String codiceTicket) {
        super(userMessage + " | " + details);
        this.codiceTicket = codiceTicket;
    }

    public String getCodiceTicket() {
        return codiceTicket;
    }
}
