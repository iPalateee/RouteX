package it.web.routex.boundary.cli.view;
import it.web.routex.boundary.cli.controller.grafico.ViewWorkScheduleControllerGraficoCLI;

@SuppressWarnings("java:S106")
public final class ViewWorkScheduleCLI
{
    private static  Integer oraInizio;
    private static  Integer oraFine;
    private static String luogoDiLavoro;
    private static int durataTurno;
    private static final String SEPARATOR = "====================================";


    private ViewWorkScheduleCLI(){

    }

    public static void mostra()
    {
        ViewWorkScheduleControllerGraficoCLI not = new ViewWorkScheduleControllerGraficoCLI();
        not.doGet();
    }
    public static void mostraOrario(Integer inizio, Integer fine, String luogo, int durata)
    {
        oraInizio = inizio;
        oraFine = fine;
        luogoDiLavoro = luogo;
        durataTurno = durata;

        System.out.println(SEPARATOR);
        System.out.println("         ORARIO DI LAVORO");
        System.out.println(SEPARATOR);
        System.out.println("Luogo di lavoro: " + luogoDiLavoro);
        System.out.println("Ora di inizio: " + oraInizio + ":00");
        System.out.println("Ora di fine: " + oraFine + ":00");
        System.out.println("Durata turno: " + durataTurno + " ore");
        System.out.println(SEPARATOR);
    }


}
