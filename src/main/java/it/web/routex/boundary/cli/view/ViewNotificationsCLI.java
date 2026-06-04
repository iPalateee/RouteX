package it.web.routex.boundary.cli.view;

import it.web.routex.bean.MessageBean;
import it.web.routex.boundary.cli.controller.grafico.UpdateNotificationsControllerGraficoCLI;
import it.web.routex.boundary.cli.controller.grafico.ViewNotificationsControllerGraficoCLI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
@SuppressWarnings("java:S106")
public final class ViewNotificationsCLI
{
    private static String notificaRisolta;
    private static List<String> risolte = new ArrayList<>();

    private ViewNotificationsCLI(){

    }

    public static void mostra()
    {
        ViewNotificationsControllerGraficoCLI not = new ViewNotificationsControllerGraficoCLI();
        not.doGet();
    }
    public static void mostraNotifiche(List<MessageBean> notifiche)
    {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        final Scanner scanner = new Scanner(System.in);


        System.out.println("====================================");
        System.out.println("           NOTIFICHE");
        System.out.println("====================================");

        int index = 1;

        for (MessageBean msg : notifiche)
        {
            String data = formatter.format(msg.getDate());

            System.out.println(index + ") [" + data + "] ");
            System.out.println("   " + msg.getMessage());
            System.out.println("------------------------------------");

            index++;
        }

        System.out.println();
        System.out.println("Quale notifica vuoi risolvere?");
        System.out.println("Inserisci il numero (0 per annullare): ");
        notificaRisolta = scanner.nextLine();

        for(MessageBean msg : notifiche)
        {
            if(notifiche.indexOf(msg)+1 == Integer.parseInt(notificaRisolta))
            {
                System.out.println("Notifica risolta: " + msg.getMessage());
                risolte.add(msg.getDate().getTime() + "|" + msg.getMessage());
                UpdateNotificationsControllerGraficoCLI.doPost();
            }

        }
    }

    public static List<String> getRisolte() {
        return risolte;
    }
}
