package it.web.routex.boundary.cli.view;

import java.util.Scanner;
@SuppressWarnings("java:S106")
public class IndexLoggedCLI{
    private final Scanner scanner = new Scanner(System.in);

    public void mostraMenu() {
        while (true) {
            System.out.println("\n================================");
            System.out.println("        ROUTEX - HOME CLI        ");
            System.out.println("================================");
            System.out.println("1) Start Exploring");
            System.out.println("2) Buy Ticket");
            System.out.println("3) Area Riservata");
            System.out.println("4) Logout");
            System.out.print("Seleziona un'opzione: ");

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> StartExploringCLI.mostra();

                case "2" -> BuyTicketCLI.mostraBuyTicket();

                case "3" -> AreaRiservataCLI.areaRiservata();

                case "4" -> {
                    LogoutCLI.logoutUser();
                    return;
                }
                default -> System.out.println("Scelta non valida.");
            }
        }
    }
}
