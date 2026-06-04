package it.web.routex.boundary.cli.view;

import it.web.routex.utility.builder.data.PaypalData;

public final class PaypalCLI
{
    private static String emailPaypal;
    private static String codiceTransazione;

    private PaypalCLI(){

    }

    public static void from(PaypalData p){
        init(p);
    }
    public static void init(PaypalData p)
    {
        emailPaypal = p.getEmailPaypal();
        codiceTransazione = p.getCodiceTransazione();
    }
    public static String getCodiceTransazione() {
        return codiceTransazione;
    }

    public static String getEmailPaypal() {
        return emailPaypal;
    }
}
