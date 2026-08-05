package it.web.routex.controller.grafico;
import it.web.routex.bean.MessageBean;
import it.web.routex.controller.applicativo.ViewNotificationsControllerApplicativo;
import it.web.routex.domain.LoggedHttpServlet;
import it.web.routex.exception.BrondiException;
import it.web.routex.exception.RemoliNoNotificationsWarningException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/viewNotifications")
public class ViewNotificationsControllerGrafico extends LoggedHttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            ViewNotificationsControllerApplicativo notifications = new ViewNotificationsControllerApplicativo();
            List<MessageBean> notifiche = notifications.messages();
            request.setAttribute("notifiche", notifiche);
            request.getRequestDispatcher("/viewNotifications.jsp").forward(request, response);

        } catch (RemoliNoNotificationsWarningException w) {

            logger.info(
                    "Nessuna notifica da mostrare. Dettagli={}",
                    w.getDetails()
            );

            request.setAttribute("warningTitle", "Tutto risolto.");
            request.setAttribute("warningMessage", w.getMessage());
            try {
                request.getRequestDispatcher("/warningNotifications.jsp").forward(request, response);
            }catch (Exception e) {
                logger.error("Errore durante il forward alla pagina di errore", e);
            }

        }
        catch (BrondiException e) {
            logger.error(
                    "Errore applicativo durante il recupero delle notifiche. Codice={} Dettagli={}",
                    e.getCodiceDiErrore(),
                    e.getDetails(),
                    e
            );
            forwardError(request, response, e.getMessage());

        } catch (Exception e) {
            logger.error("Errore imprevisto nella visualizzazione delle notifiche", e);
            forwardError(request, response, "Errore imprevisto");
        }
    }

    private void forwardError(HttpServletRequest request,
                              HttpServletResponse response,
                              String message) {
        try {
            request.setAttribute("errore", message);
            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            logger.error("Errore durante il forward alla pagina di errore", e);
        }
    }
}
