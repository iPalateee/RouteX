package it.web.routex.boundary.cli.view;

import java.util.Scanner;
@SuppressWarnings("java:S106")
public class AdminViewCLI
{
    private final Scanner scanner = new Scanner(System.in);
    public void mostraHomeAdmin()
    {
        while (true) {
            System.out.println("\n================================");
            System.out.println("        ROUTEX - Admin Home CLI        ");
            System.out.println("================================");
            System.out.println("1) Send communication to Workers");
            System.out.println("2) View Reports and Statistics");
            System.out.println("3) Logout");
            System.out.print("Seleziona un'opzione: ");

            String scelta = scanner.nextLine();
            switch (scelta) {
                case "1" -> SendCommunicationCLI.mostra();

                case "2" -> ViewReportsAndStatisticsCLI.mostra();

                case "3" -> {
                    LogoutCLI.logoutUser();
                    return;
                }
                default -> System.out.println("Scelta non valida.");

            }

        }

    }
}
