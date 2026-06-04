package it.web.routex.boundary.cli.controller.grafico;

import it.web.routex.bean.MessageBean;
import it.web.routex.boundary.cli.LoggedCLI;
import it.web.routex.boundary.cli.view.GenericErrorCLI;
import it.web.routex.boundary.cli.view.TuttoRisoltoCLIView;
import it.web.routex.boundary.cli.view.ViewNotificationsCLI;
import it.web.routex.controller.applicativo.ViewNotificationsControllerApplicativo;
import it.web.routex.exception.BrondiException;
import it.web.routex.exception.BrondiNoNotificationsWarningException;

import java.util.List;

public class ViewNotificationsControllerGraficoCLI extends LoggedCLI {

    public void doGet()
    {

        try {
            ViewNotificationsControllerApplicativo notifications = new ViewNotificationsControllerApplicativo();
            List<MessageBean> notifiche = notifications.messages();
            ViewNotificationsCLI.mostraNotifiche(notifiche);

        } catch (BrondiNoNotificationsWarningException w) {

            logger.info(
                    "Nessuna notifica da mostrare. Dettagli={}",
                    w.getDetails()
            );
            TuttoRisoltoCLIView.mostraRisoluzione(w.getMessage());

        }
        catch (BrondiException e) {
            logger.error(
                    "Errore applicativo durante il recupero delle notifiche. Codice={} Dettagli={}",
                    e.getCodiceDiErrore(),
                    e.getDetails(),
                    e
            );
            forwardError(e.getMessage());

        } catch (Exception e) {
            logger.error("Errore imprevisto nella visualizzazione delle notifiche", e);
            forwardError("Errore imprevisto");
        }
    }

    private void forwardError(String message) {
        GenericErrorCLI.mostraErrore(message);
    }
}
