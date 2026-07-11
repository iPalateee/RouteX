package it.web.routex.boundary.cli.controller.grafico;
import it.web.routex.bean.RouteBean;
import it.web.routex.bean.TicketBean;
import it.web.routex.boundary.cli.LoggedCLI;
import it.web.routex.boundary.cli.view.AreaRiservataCLI;
import it.web.routex.boundary.cli.view.GenericErrorCLI;
import it.web.routex.boundary.cli.view.LoginViewCLI;
import it.web.routex.controller.applicativo.AreaRiservata;
import it.web.routex.exception.InvalidRouteInputExceptionRemoli;
import it.web.routex.utility.singleton.Credentials;

import java.util.ArrayList;
import java.util.List;
import it.web.routex.exception.PathNotFoundExceptionRemoli;
import it.web.routex.exception.DAOExceptionBrondi;

public class AreaRiservataControllerGraficoCLI extends LoggedCLI
{
    public void doGet(){
        try {
            Credentials cred = Credentials.getInstanceSingleton();
            String cf = cred.getCodiceFiscale();
            AreaRiservata reserved = new AreaRiservata();

            if (cf != null) {

                List<RouteBean> listaPercorsi = new ArrayList<>();
                List<TicketBean> tickets = new ArrayList<>();

                try {
                    listaPercorsi = reserved.runPath(cf);
                } catch (PathNotFoundExceptionRemoli e) {
                    logger.info("Nessun percorso trovato in CLI per l'utente {}. La lista percorsi resterà vuota.", cf);
                }

                try {
                    tickets = reserved.runTicket(cf);
                } catch (PathNotFoundExceptionRemoli e) {
                    logger.info("Nessun biglietto trovato in CLI per l'utente {}. La lista biglietti resterà vuota.", cf);
                }

                AreaRiservataCLI.setListaPercorsi(listaPercorsi);
                AreaRiservataCLI.setTickets(tickets);
                forwardAreaRiservata();
                return;
            }

            redirectToLogin();

        } catch (DAOExceptionBrondi remoli) {
            logger.error("Errore DAOExceptionRemoli. Messaggio={}", remoli.getMessage(), remoli.getCause());
            GenericErrorCLI.mostraErrore(remoli.getMessage());
        } catch (InvalidRouteInputExceptionRemoli e) {
            throw new RuntimeException(e);
        }
    }
    private void forwardAreaRiservata() {
        AreaRiservataCLI.showArea();
    }
    private void redirectToLogin() {
        LoginViewCLI.mostraLogin();
    }


}
