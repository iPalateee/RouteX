package it.web.routex.boundary.cli.controller.grafico;
import it.web.routex.bean.CityBean;
import it.web.routex.bean.InformazioniPercorsoBean;
import it.web.routex.boundary.cli.LoggedCLI;
import it.web.routex.boundary.cli.domain.RouteDecoratorServiceCLI;
import it.web.routex.boundary.cli.extractor.RouteInputExtractorCLI;
import it.web.routex.boundary.cli.view.GenericErrorCLI;
import it.web.routex.boundary.cli.view.PathNOREGCLI;
import it.web.routex.boundary.cli.view.StartExploringCLI;
import it.web.routex.controller.applicativo.CityController;
import it.web.routex.controller.applicativo.PathController;
import it.web.routex.exception.*;
import it.web.routex.domain.UserStatusResolver;
import it.web.routex.record.RouteRecord;
import it.web.routex.utility.builder.PathNORegInitBuilder;
import it.web.routex.utility.singleton.Credentials;
import java.sql.SQLException;
import java.util.List;

public class PathControllerGraficoCLI extends LoggedCLI
{
    public void doGet()
    {
        try {
            CityController cityController = new CityController();
            List<CityBean> cities = cityController.getAllCities();

            StartExploringCLI.mostraExploring(cities);


        } catch (DAOExceptionBrondi e) {
            GenericErrorCLI.mostraErrore("Errore nel caricamento delle città: " + e.getMessage());
            logger.error("Errore nella presentazione della view il caricamento delle città: {}", e.toString());
        } catch (InvalidCityDataExceptionBrondi e) {
            GenericErrorCLI.mostraErrore(e.getUserMessage());
            logger.error("Errore nei dati delle città: {}", e.toString());

        }
    }

    public void post()
    {
        final Credentials cred = Credentials.getInstanceSingleton();

        RouteRecord route = estrattorePercorso();
        if (route == null) {
            return;
        }
        String status = UserStatusResolver.resolve(cred);

        logger.info("Dati per il percorso acquisiti correttamente. Città={}, StazPart={}, StazArr={}", route.city(), route.start(), route.end());
        InformazioniPercorsoBean dto;

        try {
            PathController path = new PathController();
            dto = path.run(route.start(), route.end(), route.city()); //controller applicativo
        } catch (IllegalArgumentException | UnreacheableNodeExceptionRemoli |
                 FuoriRangeExceptionRemoli | DAOExceptionBrondi | SQLException e) {
            logger.error("Errore processamento dati percorso {}", e.toString());
            GenericErrorCLI.mostraErrore("Errore processamento dati percorso");
            return;
        }

        RouteDecoratorServiceCLI.decorate(dto);

        new PathNORegInitBuilder(status).start(route.start()).end(route.end()).city(route.city()).build();

        PathController pathCtrl = new PathController();
        boolean salvato = pathCtrl.saveRoute(cred,dto,route, status);
        if(salvato)
            logger.info("[CLI]Percorso salvato correttamente per l'utente {} {} {} relativo alla città {}.", cred.getNome(), cred.getCognome(), cred.getRuolo(), route.city());
        else
            logger.info("[CLI]Percorso non salvato per l'utente {} {} {} relativo alla città {}.", cred.getNome(), cred.getCognome(), cred.getRuolo(), route.city());

        PathNOREGCLI.stampa();
        String result = "[CLI]Route from " + route.start() + " to " + route.end() + " in " + route.city();
        logger.info(result);
    }
    private RouteRecord estrattorePercorso()
    {
        try {
            return RouteInputExtractorCLI.from();
        } catch (InvalidRouteInputExceptionRemoli e)
        {
            logger.error("Errore nell'input del percorso {}", e.getMessage());
            GenericErrorCLI.mostraErrore("Errore nell'input del percorso: "+e.getMessage());
            return null;
        }
    }
}