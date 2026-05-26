package it.web.routex.controller.grafico;
import it.web.routex.bean.CityBean;
import it.web.routex.bean.InformazioniPercorsoBean;
import it.web.routex.controller.applicativo.CityController;
import it.web.routex.controller.applicativo.PathController;
import it.web.routex.exception.*;
import it.web.routex.domain.LoggedHttpServlet;
import it.web.routex.domain.RouteDecoratorService;
import it.web.routex.domain.UserStatusResolver;
import it.web.routex.extractor.RouteInputExtractor;
import it.web.routex.record.RouteRecord;
import it.web.routex.utility.singleton.Credentials;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/PathControllerGrafico")
public class PathControllerGrafico extends LoggedHttpServlet {
    private static final String FORWARDING = "Errore nel forwarding";
    private static final String ERRORE = "errore";


    private void forward(HttpServletRequest request,
                                    HttpServletResponse response) {
        try {
            request.getRequestDispatcher("search.jsp").forward(request, response);
            logger.info("Forwarding Effettuato a search.jsp");
        } catch (Exception e) {
            logger.error("Errore nel forwarding a search.jsp", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        Credentials cred = Credentials.getInstanceSingleton();
        try {
            // Recupero delle città tramite il controller applicativo
            CityController cityController = new CityController();
            List<CityBean> cities = cityController.getAllCities();

            // Passo la lista alla view
            request.setAttribute("cities2", cities);

            forward(request, response);

        } catch (DAOExceptionRemoli e) {
            forwardToError(request, response, "Errore nel caricamento delle città: " + e.getMessage(), cred);
            logger.error("Errore nella presentazione della view il caricamento delle città: {}", e.toString());

        } catch (InvalidCityDataExceptionRemoli e) {
            forwardToError(request, response, e.getUserMessage(), cred);
            logger.error("Errore nei dati delle città: {}", e.toString());
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    {
            RouteRecord route;
            final HttpSession session = request.getSession(false);
            if (session == null) {
                try {
                    response.sendRedirect("login.jsp");
                    return;
                }catch(Exception e){
                    logger.info(FORWARDING,e);
                }
            }

            final Credentials cred = Credentials.getInstanceSingleton();

            route = estrattorePercorso(request, response, cred);
            String status = UserStatusResolver.resolve(cred);


            logger.info("Dati per il percorso acquisiti correttamente. Città={}, StazPart={}, StazArr={}", route.city(), route.start(), route.end());
            InformazioniPercorsoBean dto = new InformazioniPercorsoBean();

            try {
                PathController path = new PathController();
                dto = path.run(route.start(), route.end(), route.city()); //controller applicativo
            } catch (IllegalArgumentException | UnreacheableNodeExceptionRemoli |
                     FuoriRangeExceptionRemoli | DAOExceptionRemoli | SQLException e)
            {
                forwardToError(request, response, "Errore processamento dati percorso" + e.getMessage(), cred);
                logger.error("Errore processamento dati percorso {}", e.toString());
            }

            RouteDecoratorService.decorate(dto, request);
            request.setAttribute("status", status);
            request.setAttribute("inizio", route.start());
            request.setAttribute("fine", route.end());
            request.setAttribute("city", route.city());


            PathController pathCtrl = new PathController();
            boolean salvato = pathCtrl.saveRoute(cred,dto,route, status);
            if(salvato)
                logger.info("Percorso salvato correttamente per l'utente {} {} {} relativo alla città {}.", cred.getNome(), cred.getCognome(), cred.getRuolo(), route.city());
            else
                logger.info("Percorso non salvato per l'utente {} {} {} relativo alla città {}.", cred.getNome(), cred.getCognome(), cred.getRuolo(), route.city());



        //inoltro la richiesta al jsp
        RequestDispatcher dispatcher;
        if(cred.getCodiceFiscale()!=null) {
            dispatcher = request.getRequestDispatcher("PathREG.jsp");
        }
        else {
            dispatcher = request.getRequestDispatcher("PathNOREG.jsp");
        }
            try {
                dispatcher.forward(request, response);
            }catch(Exception e){
                logger.info(FORWARDING, e);
            }
            //  logica per calcolare il percorso o qualsiasi altra logica
            String result = "Route from " + route.start() + " to " + route.end() + " in " + route.city();
            logger.info(result);

    }
    private RouteRecord estrattorePercorso(HttpServletRequest request, HttpServletResponse response, Credentials cred)
    {
        try {
            return RouteInputExtractor.from(request);
        } catch (InvalidRouteInputExceptionRemoli e)
        {
            forwardToError(request, response, "Errore nell'input del percorso {}. -" + e.getMessage(), cred);
            logger.error("Errore nell'input del percorso {}", e.getMessage());
            return null;
        }
    }
    protected void forwardToError(HttpServletRequest request,
                                  HttpServletResponse response,
                                  String errorMessage, Credentials cred) {
        try {
            request.setAttribute(ERRORE, errorMessage);

            HttpSession session = request.getSession(false);

            String errorPage;
            if (session != null && cred.getCodiceFiscale() != null) {
                errorPage = "errorLogged.jsp";
            } else {
                errorPage = "errorNotLogged.jsp";
            }

            request.getRequestDispatcher(errorPage).forward(request, response);

        } catch (Exception e) {
            logger.error("Errore nel forwarding alla pagina di errore", e);
        }
    }

}
