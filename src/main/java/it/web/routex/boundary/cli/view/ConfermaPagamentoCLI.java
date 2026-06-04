package it.web.routex.boundary.cli.view;

import it.web.routex.boundary.cli.controller.grafico.ConfermaPagamentoControllerGraficoCLI;
import it.web.routex.utility.builder.PayPalBuilder;

import java.util.Scanner;
@SuppressWarnings("java:S106")
public final class ConfermaPagamentoCLI
{
    private static String city;
    private static String quantity;
    private static double prezzoTotale;
    private static String metodoPagamento;
    private static String persistenza;

    private ConfermaPagamentoCLI() {}

    public static void init(String c, String q, double p) {
        city = c;
        quantity = q;
        prezzoTotale = p;
    }

    public static String getCity() {
        return city;
    }

    public static double getPrezzoTotale() {
        return prezzoTotale;
    }

    public static String getQuantity() {
        return quantity;
    }

    public static String getMetodoPagamento() {
        return metodoPagamento;
    }

    public static String getPersistenza() {
        return persistenza;
    }

    public static void stampa() {

        Scanner scanner = new Scanner(System.in);

        printHeader();
        printRiepilogo();

        if (!askConferma(scanner)) {
            return;
        }

        metodoPagamento = scegliMetodoPagamento(scanner);
        gestisciDatiPagamento(scanner, metodoPagamento);

        persistenza = scegliPersistenza(scanner);

        ConfermaPagamentoControllerGraficoCLI pay =
                new ConfermaPagamentoControllerGraficoCLI();
        pay.doPost();
    }
    private static void printHeader() {
        System.out.println("\n========================================");
        System.out.println("            CONFERMA PAGAMENTO           ");
        System.out.println("========================================");
    }

    private static void printRiepilogo() {
        System.out.println("Città: " + getCity());
        System.out.println("Quantità dei biglietti: " + getQuantity());
        System.out.println("Prezzo: " + getPrezzoTotale());
    }
    private static boolean askConferma(Scanner scanner) {
        System.out.println("Vuoi confermare?(si/no) :");
        return "si".equals(scanner.nextLine());
    }
    private static String scegliMetodoPagamento(Scanner scanner) {

        while (true) {
            System.out.println("* METODI DI PAGAMENTO DISPONIBILI *");
            System.out.println("1. Mastercard");
            System.out.println("2. Paypal");
            System.out.print("Scegli il metodo di pagamento: ");

            int value = scanner.nextInt();

            if (value == 1) return "Mastercard";
            if (value == 2) return "Paypal";
        }
    }
    private static void gestisciDatiPagamento(Scanner scanner, String metodo) {

        scanner.nextLine();

        if ("Mastercard".equals(metodo)) {
            System.out.print("Numero carta: ");
            String numeroCarta = scanner.nextLine();

            System.out.print("Scadenza (YYYY-MM-DD): ");
            String scadenza = scanner.nextLine();

            System.out.print("CVV: ");
            String cvv = scanner.nextLine();

            MastercardCLI.init(numeroCarta, scadenza, cvv);
            return;
        }

        if ("Paypal".equals(metodo)) {
            System.out.print("Email paypal: ");
            String emailPaypal = scanner.nextLine();

            System.out.print("Codice Transazione: ");
            String codiceTransazione = scanner.nextLine();

            new PayPalBuilder(emailPaypal)
                    .codiceTransazione(codiceTransazione)
                    .build();
        }
    }
    private static String scegliPersistenza(Scanner scanner) {

        while (true) {
            System.out.println("* Modalità di persistenza *");
            System.out.println("1. JDBC");
            System.out.println("2. FILE_SYSTEM");
            System.out.print("Scegli la modalità di persistenza: ");

            int value = scanner.nextInt();

            if (value == 1) return "JDBC";
            if (value == 2) return "FileSystem";
        }
    }






}
