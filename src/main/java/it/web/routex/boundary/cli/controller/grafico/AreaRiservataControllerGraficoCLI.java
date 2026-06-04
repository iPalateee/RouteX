package it.web.routex.boundary.cli.controller.grafico;
import it.web.routex.bean.RouteBean;
import it.web.routex.bean.TicketBean;
import it.web.routex.boundary.cli.LoggedCLI;
import it.web.routex.boundary.cli.view.AreaRiservataCLI;
import it.web.routex.boundary.cli.view.GenericErrorCLI;
import it.web.routex.boundary.cli.view.LoginViewCLI;
import it.web.routex.controller.applicativo.AreaRiservata;
import it.web.routex.utility.singleton.Credentials;
import java.util.List;
import it.web.routex.exception.PathNotFoundExceptionRemoli;
import it.web.routex.exception.DAOExceptionRemoli;

public class AreaRiservataControllerGraficoCLI extends LoggedCLI
{
    public void doGet(){
        try {

                Credentials cred = Credentials.getInstanceSingleton();
                String cf = cred.getCodiceFiscale();
                AreaRiservata reserved = new AreaRiservata();

                if (cf != null) {

                    List<RouteBean> listaPercorsi = reserved.runPath(cf);
                    List<TicketBean> tickets = reserved.runTicket(cf);
                    AreaRiservataCLI.setListaPercorsi(listaPercorsi);
                    AreaRiservataCLI.setTickets(tickets);
                    forwardAreaRiservata();
                    return;
                }

            redirectToLogin();
        } catch (PathNotFoundExceptionRemoli remoli) {
            logger.error("Errore PathNotFoundExceptionRemoli. Messaggio={} Cf={} CodiceErrore={} Dettagli={}.", remoli.getMessage(), remoli.getCodiceFiscaleUtente(), remoli.getCodiceDiErrore(), remoli.getDetails());
            GenericErrorCLI.mostraErrore(remoli.getMessage());

        } catch (DAOExceptionRemoli remoli) {
            logger.error("Errore DAOExceptionRemoli. Messaggio={}", remoli.getMessage(), remoli.getCause());
            GenericErrorCLI.mostraErrore(remoli.getMessage());
        }

    }
    private void forwardAreaRiservata() {
        AreaRiservataCLI.showArea();
    }
    private void redirectToLogin() {
        LoginViewCLI.mostraLogin();
    }


}
