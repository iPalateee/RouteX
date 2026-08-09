package it.web.routex.controller.grafico;

import it.web.routex.bean.RouteBean;
import it.web.routex.bean.TicketBean;
import it.web.routex.controller.applicativo.AreaRiservata;
import it.web.routex.domain.LoggedHttpServlet;

import it.web.routex.exception.InvalidRouteInputExceptionRemoli;
import it.web.routex.utility.singleton.Credentials;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.utility.text.AreaRiservataHelper;

@WebServlet("/areaRiservata")
public class AreaRiservataControllerGrafico extends LoggedHttpServlet {

    private static final String ATTR_ERRORE = "errore";
    private static final String PAGE_ERRORE = "/error.jsp";
    private static final String MSG_LOG_FORWARD_ERROR = "Errore durante il forward alla pagina di errore";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            final HttpSession session = request.getSession(false);
            if (session != null) {
                Credentials cred = Credentials.getInstanceSingleton();
                String cf = cred.getCodiceFiscale();
                AreaRiservata reserved = new AreaRiservata();

                if (cf != null) {

                    List<RouteBean> listaPercorsi = AreaRiservataHelper.estraiPercorsi(reserved, cf, logger);
                    List<TicketBean> tickets = AreaRiservataHelper.estraiBiglietti(reserved, cf, logger);

                    request.setAttribute("listaPercorsi", listaPercorsi);
                    request.setAttribute("tickets", tickets);
                    forwardAreaRiservata(request, response);
                    return;
                }
            }
            redirectToLogin(response);

        } catch (DAOExceptionBrondi remoli) {
            logger.error("Errore DAOExceptionRemoli. Messaggio={}", remoli.getMessage(), remoli.getCause());
            request.setAttribute(ATTR_ERRORE, remoli.getMessage());
            try {
                request.getRequestDispatcher(PAGE_ERRORE).forward(request, response);
            } catch (Exception e) {
                logger.error(MSG_LOG_FORWARD_ERROR, e);
            }

        } catch (InvalidRouteInputExceptionRemoli e) {
            logger.error("Errore di validazione input percorso. Messaggio={}", e.getMessage(), e);
            request.setAttribute(ATTR_ERRORE, e.getMessage());
            try {
                request.getRequestDispatcher(PAGE_ERRORE).forward(request, response);
            } catch (Exception forwardEx) {
                logger.error(MSG_LOG_FORWARD_ERROR, forwardEx);
            }
        }
    }

    private void forwardAreaRiservata(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/areaRiservata.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Errore durante il forward alla pagina dell'area riservata", e);
        }
    }

    private void redirectToLogin(HttpServletResponse response) {
        try {
            response.sendRedirect("/login.jsp");
        } catch (Exception e) {
            logger.error("Errore durante il redirect alla pagina di login", e);
        }
    }
}