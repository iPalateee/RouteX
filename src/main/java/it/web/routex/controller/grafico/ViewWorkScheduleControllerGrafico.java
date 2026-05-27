package it.web.routex.controller.grafico;
import it.web.routex.bean.WorkerScheduleBean;
import it.web.routex.controller.applicativo.ViewWorkScheduleControllerApplicativo;
import it.web.routex.domain.LoggedHttpServlet;
import it.web.routex.enumerator.Ruolo;
import it.web.routex.exception.BrondiException;
import it.web.routex.utility.singleton.Credentials;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/viewWorkSchedule")
public class ViewWorkScheduleControllerGrafico extends LoggedHttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                redirectToLogin(response);
                return;
            }

            Credentials cred = Credentials.getInstanceSingleton();
            if (cred == null || cred.getCodiceFiscale() == null) {
                redirectToLogin(response);
                return;
            }

            ViewWorkScheduleControllerApplicativo service = new ViewWorkScheduleControllerApplicativo();
            WorkerScheduleBean schedule = service.getSchedule(cred.getCodiceFiscale());

            request.setAttribute("oraInizio", schedule.getOraInizio());
            request.setAttribute("oraFine", schedule.getOraFine());
            request.setAttribute("luogoDiLavoro", schedule.getLuogoDiLavoro());
            request.setAttribute("durataTurno", schedule.getDurataTurno());

            request.getRequestDispatcher("/workSchedule.jsp").forward(request, response);

        } catch (BrondiException e) {
            logger.error(
                    "Errore Brondi. Messaggio={} CodiceErrore={} Dettagli={}",
                    e.getMessage(),
                    e.getCodiceDiErrore(),
                    e.getDetails()
            );

            forwardError(request, response, e.getMessage());
        } catch (Exception e) {
            logger.error("Errore generico", e);
            forwardError(request, response, "Errore imprevisto");
        }
    }

    private void forwardError(HttpServletRequest request,
                              HttpServletResponse response,
                              String message) {
        try {
            request.setAttribute("errore", message);
            Ruolo ruolo = Credentials.getInstanceSingleton().getRuolo();
            String urlRitorno = (ruolo == Ruolo.WORKER) ? "dashboardWorker.jsp" : "index.jsp";
            request.setAttribute("indexUrl", urlRitorno);
            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            logger.error("Errore durante il forward alla pagina di errore", e);
        }
    }

    private void redirectToLogin(HttpServletResponse response) {
        try {
            response.sendRedirect("/login.jsp");
        } catch (Exception e) {
            logger.error("Errore redirect login", e);
        }
    }
}
