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
import it.web.routex.exception.PathNotFoundExceptionRemoli;
import it.web.routex.exception.DAOExceptionBrondi;

@WebServlet("/areaRiservata")
public class AreaRiservataControllerGrafico extends LoggedHttpServlet {

    private static final String ATTR_ERRORE = "errore";
    private static final String PAGE_ERRORE = "/error.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            final HttpSession session = request.getSession(false);
            if (session != null) {
                Credentials cred = Credentials.getInstanceSingleton();
                String cf = cred.getCodiceFiscale();
                AreaRiservata reserved = new AreaRiservata();

                if (cf != null) {
                    List<RouteBean> listaPercorsi = reserved.runPath(cf);
                    List<TicketBean> tickets = reserved.runTicket(cf);
                    request.setAttribute("listaPercorsi", listaPercorsi);
                    request.setAttribute("tickets", tickets);
                    forwardAreaRiservata(request, response);
                    return;
                }
            }
            redirectToLogin(response);

        } catch (PathNotFoundExceptionRemoli remoli) {
            logger.error("Errore PathNotFoundExceptionRemoli. Messaggio={} Cf={} CodiceErrore={} Dettagli={}.", remoli.getMessage(), remoli.getCodiceFiscaleUtente(), remoli.getCodiceDiErrore(), remoli.getDetails());

            request.setAttribute(ATTR_ERRORE, remoli.getMessage());
            String indexUrl = request.getContextPath() + "/indexLogged.jsp";
            request.setAttribute("indexUrl", indexUrl);

            try {
                request.getRequestDispatcher(PAGE_ERRORE).forward(request, response);
            } catch (Exception e) {
                logger.error("Errore durante il forward alla pagina di errore", e);
            }

        } catch (DAOExceptionBrondi remoli) {
            logger.error("Errore DAOExceptionRemoli. Messaggio={}", remoli.getMessage(), remoli.getCause());

            request.setAttribute(ATTR_ERRORE, remoli.getMessage()); // COSTANTE

            try {
                request.getRequestDispatcher(PAGE_ERRORE).forward(request, response); // COSTANTE
            } catch (Exception e) {
                logger.error("Errore durante il forward alla pagina di errore", e);
            }

        } catch (InvalidRouteInputExceptionRemoli e) {
            logger.error("Errore di validazione input percorso. Messaggio={}", e.getMessage(), e);

            request.setAttribute(ATTR_ERRORE, e.getMessage()); // COSTANTE

            try {
                request.getRequestDispatcher(PAGE_ERRORE).forward(request, response); // COSTANTE
            } catch (Exception forwardEx) {
                logger.error("Errore durante il forward alla pagina di errore", forwardEx);
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