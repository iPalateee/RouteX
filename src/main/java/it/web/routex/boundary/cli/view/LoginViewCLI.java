package it.web.routex.boundary.cli.view;

import it.web.routex.boundary.cli.controller.grafico.LoginControllerGraficoCLI;

import java.util.Scanner;
@SuppressWarnings("java:S106")
public final class LoginViewCLI {

    private static String emailUtente;
    private static String passwordUtente;

    private LoginViewCLI(){

    }


    public static void mostraLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print("Inserisci Email: ");
        emailUtente = scanner.nextLine();

        System.out.print("Inserisci Password: ");
        passwordUtente = scanner.nextLine();

        LoginControllerGraficoCLI login = new LoginControllerGraficoCLI();
        login.post();

    }
    public static String getEmailUtente() {
        return emailUtente;
    }

    public static String getPasswordUtente() {
        return passwordUtente;
    }

}
