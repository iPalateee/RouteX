package it.web.routex.boundary.cli.view;
import it.web.routex.bean.CityBean;
import it.web.routex.boundary.cli.controller.grafico.BuyTicketControllerGraficoCLI;

import java.util.List;
import java.util.Scanner;
@SuppressWarnings("java:S106")
public final class BuyTicketCLI
{
    private BuyTicketCLI(){

    }
    public static void mostraBuyTicket()
    {
        BuyTicketControllerGraficoCLI ctrgraphic = new BuyTicketControllerGraficoCLI();
        ctrgraphic.doGet();
    }
    public static void mostraAcquisto(List<CityBean> cities) {
        Scanner scanner = new Scanner(System.in);
        int accesso = -1;
        System.out.println("================================");
        System.out.println("        ROUTEX - BuyTicketCLI       ");
        System.out.println("================================");

        while(accesso > cities.size() || accesso < 1) {
            for(int i=0; i<cities.size(); i++) {
                System.out.println((i+1) + ") per " + cities.get(i));
            }
            System.out.print("Inserisci città: ");

            if (scanner.hasNextInt()) {
                accesso = scanner.nextInt();
            } else {
                System.out.println("\n[!] Errore: devi inserire un numero valido.");
                scanner.next();
            }
        }

        String city = String.valueOf(cities.get(accesso-1));
        scanner.nextLine();

        System.out.print("Inserisci quantità: ");
        String quantity = scanner.nextLine();

        BuyTicketControllerGraficoCLI tick = new BuyTicketControllerGraficoCLI();
        tick.doPost(city, quantity);
    }
}
