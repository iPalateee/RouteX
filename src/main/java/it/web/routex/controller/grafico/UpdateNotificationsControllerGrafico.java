package it.web.routex.controller.grafico;

import it.web.routex.bean.MessageBean;
import it.web.routex.controller.applicativo.UpdateNotificationsControllerApplicativo;
import it.web.routex.domain.LoggedHttpServlet;
import it.web.routex.exception.DAOExceptionBrondi;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.Timestamp;
@WebServlet("/updateNotifications")
public class UpdateNotificationsControllerGrafico extends LoggedHttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            String[] risolte = request.getParameterValues("risolte"); // eh no io ora da qui me le voglio prendere dalla cache

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

            response.sendRedirect(
                    request.getContextPath() + "/viewNotifications"
            );

        } catch (DAOExceptionBrondi e) {

            request.setAttribute(
                    "errore",
                    "Impossibile aggiornare le notifiche. Riprovare più tardi."
            );

            try {
                request.getRequestDispatcher("/error.jsp")
                        .forward(request, response);
            } catch (Exception ex) {
                logger.error("Errore nel redirect a /error.jsp");
            }

        } catch (Exception e) {

            request.setAttribute(
                    "errore",
                    "Errore generico durante l'operazione."
            );

            try {
                request.getRequestDispatcher("/error.jsp")
                        .forward(request, response);
            } catch (Exception ex) {
                logger.error("Errore generico nel redirect a /error.jsp");
            }
        }
    }
}
