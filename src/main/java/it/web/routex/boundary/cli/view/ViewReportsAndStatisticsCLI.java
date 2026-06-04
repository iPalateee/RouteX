package it.web.routex.boundary.cli.view;

import it.web.routex.bean.PathInfoBean;
import it.web.routex.bean.ReportsStatsBean;
import it.web.routex.boundary.cli.controller.grafico.ReportsControllerGraficoCLI;
@SuppressWarnings("java:S106")
public final class ViewReportsAndStatisticsCLI {

    private ViewReportsAndStatisticsCLI(){

    }

    public static void mostra()
    {
        ReportsControllerGraficoCLI not = new ReportsControllerGraficoCLI();
        not.doGet();
    }

    public static void mostrareports(ReportsStatsBean stats)
    {
        if (stats == null)
        {
            System.out.println("Nessun report disponibile.");
            return;
        }

        System.out.println("\n==============================================");
        System.out.println("        REPORT E STATISTICHE DI UTILIZZO        ");
        System.out.println("==============================================");

        System.out.println("Totale viaggi effettuati : " + stats.getTotalTrips());
        System.out.println("Distanza totale percorsa : " + String.format("%.2f", stats.getTotalDistance()) + " km");
        System.out.println("Tempo totale di viaggio  : " + String.format("%.2f", stats.getTotalTime()) + " ore");

        System.out.println("----------------------------------------------");



        System.out.println("Utenti coinvolti (" + stats.getUtenti().size() + "):");
        for (String u : stats.getUtenti())
        {
            System.out.println(" - " + u);
        }


        System.out.println("----------------------------------------------");

            System.out.println("Dettaglio percorsi:");
            System.out.println("-----------------------------------------------------------------------------------------------");
            System.out.printf(
                    "%-3s %-15s %-15s %-10s %-15s %-7s %-8s %-10s%n",
                    "#", "START", "END", "CITY", "USER", "CAMBI", "TEMPO", "DISTANZA"
            );
            System.out.println("-----------------------------------------------------------------------------------------------");

            int index = 1;
            for (PathInfoBean p : stats.getPaths()) {

                System.out.printf(
                        "%-3d %-15s %-15s %-10s %-15s %-7d %-8.2f %-10.2f%n",
                        index,
                        p.getStartStation(),
                        p.getEndStation(),
                        p.getCity(),
                        p.getUtente(),
                        p.getNCambi(),
                        p.getTempoDiArrivo(),
                        p.getPercTerrenoUtilizzato()
                );
                index++;
            }

        System.out.println("==============================================\n");
    }

}
