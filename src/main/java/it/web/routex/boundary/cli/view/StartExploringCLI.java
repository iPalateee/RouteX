package it.web.routex.boundary.cli.view;
import it.web.routex.bean.CityBean;
import it.web.routex.boundary.cli.controller.grafico.PathControllerGraficoCLI;
import java.util.List;
import java.util.Scanner;
@SuppressWarnings("java:S106")
public final class StartExploringCLI
{
    private static String city;
    private static String stazionePartenza;
    private static String stazioneArrivo;

    private StartExploringCLI(){

    }

    public static void mostra()
    {
        PathControllerGraficoCLI not = new PathControllerGraficoCLI();
        not.doGet();
    }

    public static void mostraExploring(List<CityBean> cities) {

        System.out.println("\n==============================================");
        System.out.println("              CITTÀ DISPONIBILI               ");
        System.out.println("==============================================");
        int index = 1;
        for (CityBean city : cities) {
            System.out.println(index + ") " + city.getCity());
            index++;
        }
        System.out.println("==============================================");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Inserisci per intero il nome della città: ");
        city = scanner.nextLine();

        System.out.print("Inserisci stazione di partenza: ");
        stazionePartenza = scanner.nextLine();

        System.out.print("Inserisci stazione di arrivo: ");
        stazioneArrivo = scanner.nextLine();

        PathControllerGraficoCLI p = new PathControllerGraficoCLI();
        p.post();

    }

    public static String getCity() {
        return city;
    }

    public static String getStazioneArrivo() {
        return stazioneArrivo;
    }

    public static String getStazionePartenza() {
        return stazionePartenza;
    }
}
