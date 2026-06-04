package it.web.routex.boundary.cli.view;

import it.web.routex.boundary.cli.controller.grafico.ConfirmCommunicationControllerGraficoCLI;

import java.util.Scanner;
@SuppressWarnings("java:S106")
public final class SendCommunicationCLI
{
    private static final Scanner scanner = new Scanner(System.in);
    private static String message;

    private SendCommunicationCLI(){

    }
    public static void mostra()
    {
        System.out.println("\n================================");
        System.out.println("     Invia Comunicazione ai Workers     ");
        System.out.println("================================");
        System.out.print("Inserisci il messaggio da inviare: ");
        message = scanner.nextLine();

        ConfirmCommunicationControllerGraficoCLI.doPost();

    }

    public static String getMessage() {
        return message;
    }
}
