package it.web.routex.boundary.cli.view;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("java:S106")
public final class SuccessoPagamentoCLI{

    private static String city;
    private static String quantity;
    private static double totale;
    private static String metodo;
    private static String messaggio;
    private static List<String> codiciBiglietti = new ArrayList<>();

    private SuccessoPagamentoCLI(){

    }

    public static void setCity(String city) {
        SuccessoPagamentoCLI.city = city;
    }

    public static void setCodiciBiglietti(List<String> codiciBiglietti) {
        SuccessoPagamentoCLI.codiciBiglietti = codiciBiglietti;
    }

    public static void setMessaggio(String messaggio) {
        SuccessoPagamentoCLI.messaggio = messaggio;
    }

    public static void setMetodo(String metodo) {
        SuccessoPagamentoCLI.metodo = metodo;
    }

    public static void setQuantity(String quantity) {
        SuccessoPagamentoCLI.quantity = quantity;
    }

    public static void setTotale(double totale) {
        SuccessoPagamentoCLI.totale = totale;
    }

    public static String getQuantity() {
        return quantity;
    }

    public static String getCity() {
        return city;
    }

    public static List<String> getCodiciBiglietti() {
        return codiciBiglietti;
    }

    public static String getMessaggio() {
        return messaggio;
    }

    public static String getMetodo() {
        return metodo;
    }

    public static double getTotale() {
        return totale;
    }

    public static void stampa() {

        System.out.println("\n========================================");
        System.out.println("          PAGAMENTO AVVENUTO            ");
        System.out.println("========================================");

        System.out.println("Città: " + city);
        System.out.println("Quantità biglietti: " + quantity);
        System.out.println("Totale pagato: " + totale);
        System.out.println("Metodo di pagamento: " + metodo);
        System.out.println("Messaggio: " + messaggio);

        System.out.println("\n--- Codici Biglietti Generati ---");
        if (codiciBiglietti != null && !codiciBiglietti.isEmpty()) {
            for (String codice : codiciBiglietti) {
                System.out.println(" - " + codice);
            }
        } else {
            System.out.println(" Nessun biglietto generato.");
        }

        System.out.println("========================================\n");
    }


}
