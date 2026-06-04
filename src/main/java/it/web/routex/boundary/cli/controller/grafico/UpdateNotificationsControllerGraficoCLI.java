package it.web.routex.boundary.cli.controller.grafico;
import it.web.routex.bean.MessageBean;
import it.web.routex.boundary.cli.view.GenericErrorCLI;
import it.web.routex.boundary.cli.view.ViewNotificationsCLI;
import it.web.routex.controller.applicativo.UpdateNotificationsControllerApplicativo;
import it.web.routex.exception.DAOExceptionRemoli;
import java.sql.Timestamp;
import java.util.List;

public final class UpdateNotificationsControllerGraficoCLI
{
    private UpdateNotificationsControllerGraficoCLI() {

    }

    public static void doPost() {

        try {
            List<String> risolte = ViewNotificationsCLI.getRisolte();

            UpdateNotificationsControllerApplicativo service = new UpdateNotificationsControllerApplicativo();

            if (risolte != null) {
                for (String r : risolte) {

                    String[] parts = r.split("\\|", 2);
                    long timestamp = Long.parseLong(parts[0]);
                    String message = parts[1];

                    MessageBean bean = new MessageBean(
                            message,
                            new Timestamp(timestamp)
                    );

                    service.aggiornaStatoNotifica(bean);
                }
            }

            ViewNotificationsControllerGraficoCLI not = new ViewNotificationsControllerGraficoCLI();
            not.doGet();

        } catch (DAOExceptionRemoli e) {
            GenericErrorCLI.mostraErrore("Impossibile aggiornare le notifiche. Riprovare più tardi.");
        } catch (Exception e) {
            GenericErrorCLI.mostraErrore("Errore imprevisto durante l'operazione.");
        }
    }
}