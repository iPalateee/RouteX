package it.web.routex.boundary.cli.view;

import it.web.routex.boundary.cli.controller.grafico.SelectModeControllerGraficoCLI;
import it.web.routex.exception.InvalidModeExceptionBrondi;

import java.util.Scanner;

@SuppressWarnings("java:S106")
public final class SelectModeCLI {

    private static final Scanner scanner = new Scanner(System.in);
    private static String scelta = "";
    private static String mode = "";

    private SelectModeCLI(){

    }

    public static void choiceDemoFull() throws InvalidModeExceptionBrondi {

        System.out.println("\n================================");
        System.out.println("     ROUTEX - TYPE MODE       ");
        System.out.println("================================");
        System.out.println("- DEMO  -");
        System.out.println("- FULL  -");
        System.out.print("Scrivi una modalità tra DEMO e FULL: ");
        mode = scanner.nextLine();
        scelta = mode.toUpperCase();
        SelectModeControllerGraficoCLI.doPost();
    }

    public static String getScelta() {
        return scelta;
    }
}
