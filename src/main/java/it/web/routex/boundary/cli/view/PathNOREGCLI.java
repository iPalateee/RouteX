package it.web.routex.boundary.cli.view;
import it.web.routex.utility.builder.data.PathInit;
import it.web.routex.utility.builder.data.PathNoRegData;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("java:S106")
public final class PathNOREGCLI
{
    private static List<String> percorsiConNomi = new ArrayList<>();
    private static int numeroCambi;
    private static List<String> linee = new ArrayList<>();
    private static List<String> sequenzeDiCambiamento = new ArrayList<>();
    private static int numeroStazioniUsate;
    static double minutaggio;
    private static List<String> sequenzeNodiCruciali = new ArrayList<>();
    private static int numeroStazioniTotali;
    static double percentualeStazioniUsate;
    static String status;
    static String start;
    static String end;
    static String city;

    private PathNOREGCLI(){

    }

    public static double getMinutaggio() {
        return minutaggio;
    }

    public static double getPercentualeStazioniUsate() {
        return percentualeStazioniUsate;
    }

    public static int getNumeroCambi() {
        return numeroCambi;
    }

    public static int getNumeroStazioniTotali() {
        return numeroStazioniTotali;
    }

    public static int getNumeroStazioniUsate() {
        return numeroStazioniUsate;
    }

    public static List<String> getLinee() {
        return linee;
    }

    public static List<String> getPercorsiConNomi() {
        return percorsiConNomi;
    }

    public static List<String> getSequenzeDiCambiamento() {
        return sequenzeDiCambiamento;
    }

    public static List<String> getSequenzeNodiCruciali() {
        return sequenzeNodiCruciali;
    }

    public static String getCity() {
        return city;
    }

    public static String getEnd() {
        return end;
    }

    public static String getStart() {
        return start;
    }

    public static String getStatus() {
        return status;
    }

    public static void from2(PathInit a) {
        initialize(a);
    }

    public static void from(PathNoRegData data) {
        init(data);
    }

    public static void init(PathNoRegData path) {
        percorsiConNomi = path.getPercorsiConNomi();
        numeroCambi = path.getNumeroCambi();
        linee = path.getLinee();
        numeroStazioniUsate = path.getNumeroStazioniUsate();
        minutaggio = path.getMinutaggio();
        numeroStazioniTotali = path.getNumeroStazioniTotali();
        percentualeStazioniUsate = path.getPercentualeStazioniUsate();
        sequenzeDiCambiamento = path.getSequenzeDiCambiamento();
        sequenzeNodiCruciali = path.getSequenzeNodiCruciali();
    }

    public static void initialize(PathInit a)
    {
        status = a.getStatus();
        start = a.getStart();
        end = a.getEnd();
        city = a.getCity();

    }
    public static void stampa() {

        printHeader();
        printDatiPrincipali();
        printInformazioniGenerali();

        printLista("Linee coinvolte", getLinee(), "Nessuna linea disponibile.");
        printLista("Percorsi con nomi", getPercorsiConNomi(), "Nessun percorso disponibile.");
        printLista("Sequenze di cambiamento", getSequenzeDiCambiamento(), "Nessuna sequenza disponibile.");
        printLista("Nodi cruciali", getSequenzeNodiCruciali(), "Nessun nodo cruciale.");

        printFooter();
    }
    private static void printHeader() {
        System.out.println("\n========================================");
        System.out.println("            DETTAGLI PERCORSO           ");
        System.out.println("========================================");
    }

    private static void printFooter() {
        System.out.println("========================================\n");
    }
    private static void printDatiPrincipali() {
        System.out.println("Città: " + getCity());
        System.out.println("Stazione di partenza: " + getStart());
        System.out.println("Stazione di arrivo: " + getEnd());
        System.out.println("Stato: " + getStatus());
    }
    private static void printInformazioniGenerali() {
        System.out.println("\n--- Informazioni generali ---");
        System.out.println("Numero cambi: " + getNumeroCambi());
        System.out.println("Numero stazioni usate: " + getNumeroStazioniUsate());
        System.out.println("Numero stazioni totali: " + getNumeroStazioniTotali());
        System.out.println("Minutaggio: " + getMinutaggio() + " min");
        System.out.println("Percentuale stazioni usate: " + getPercentualeStazioniUsate() + " %");
    }
    private static void printLista(String titolo, List<String> lista, String emptyMessage) {

        System.out.println("\n--- " + titolo + " ---");

        if (lista == null || lista.isEmpty()) {
            System.out.println(" " + emptyMessage);
            return;
        }

        for (String elemento : lista) {
            System.out.println(" - " + elemento);
        }
    }





}
