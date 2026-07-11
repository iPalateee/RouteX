package it.web.routex.boundary.cli.view;

import java.util.Scanner;
@SuppressWarnings("java:S106")
public class WorkerViewCLI {

    private final Scanner scanner = new Scanner(System.in);

    public void mostraDashboard() {
        while (true) {
            System.out.println("\n================================");
            System.out.println("        ROUTEX - Worker Home CLI        ");
            System.out.println("================================");
            System.out.println("1) View Notifications");
            System.out.println("2) View Work Schedule");
            System.out.println("3) Logout");
            System.out.print("Seleziona un'opzione: ");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> ViewNotificationsCLI.mostra();

                case "2" -> ViewWorkScheduleCLI.mostra();

                case "3" -> {
                    LogoutCLI.logoutUser();
                    return;
                }
                default -> System.out.println("Scelta non valida.");

            }
        }
    }
}
