package it.web.routex.boundary.cli.view;

public final class MastercardCLI
{
    private static String numeroCarta;
    private static String scadenza;
    private static String cvv;

    private MastercardCLI(){

    }
    public static void init(String a, String b, String c)
    {
        numeroCarta = a;
        scadenza = b;
        cvv = c;
    }

    public static String getCvv() {
        return cvv;
    }

    public static String getNumeroCarta() {
        return numeroCarta;
    }

    public static String getScadenza() {
        return scadenza;
    }
}
