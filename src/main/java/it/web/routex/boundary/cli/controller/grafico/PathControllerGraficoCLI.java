package it.web.routex.boundary.cli.controller.grafico;
import it.web.routex.bean.CityBean;
import it.web.routex.bean.InformazioniPercorsoBean;
import it.web.routex.bean.RouteBean;
import it.web.routex.boundary.cli.LoggedCLI;
import it.web.routex.boundary.cli.domain.RouteDecoratorServiceCLI;
import it.web.routex.boundary.cli.view.GenericErrorCLI;
import it.web.routex.boundary.cli.view.PathNOREGCLI;
import it.web.routex.boundary.cli.view.StartExploringCLI;
import it.web.routex.controller.applicativo.BuyTicketControllerApplicativo;
import it.web.routex.controller.applicativo.PathController;
import it.web.routex.exception.*;
import it.web.routex.domain.UserStatusResolver;
import it.web.routex.utility.builder.PathNORegInitBuilder;
import it.web.routex.utility.singleton.Credentials;
import java.sql.SQLException;
import java.util.List;

public class PathControllerGraficoCLI extends LoggedCLI
{
    public void doGet()
    {
        try {
            BuyTicketControllerApplicativo buyTicketControllerApplicativo = new BuyTicketControllerApplicativo();
            List<CityBean> cities = buyTicketControllerApplicativo.getAllCities();

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

        RouteBean route = estrattorePercorso();
        if (route == null) {
            return;
        }
        String status = UserStatusResolver.resolve(cred);

        logger.info("Dati per il percorso acquisiti correttamente. Città={}, StazPart={}, StazArr={}", route.getCitta(), route.getPartenza(), route.getArrivo());
        InformazioniPercorsoBean dto;

        try {
            PathController path = new PathController();
            dto = path.run(route.getPartenza(), route.getArrivo(), route.getCitta());
        } catch (IllegalArgumentException | UnreacheableNodeExceptionRemoli | FuoriRangeExceptionBrondi |
                 DAOExceptionBrondi | SQLException | InvalidCityDataExceptionBrondi | InvalidRouteInputExceptionRemoli | InvalidBuyTicketInputExceptionBrondi e) {
            logger.error("Errore processamento dati percorso {}", e.toString());
            GenericErrorCLI.mostraErrore("Errore processamento dati percorso");
            return;
        }

        RouteDecoratorServiceCLI.decorate(dto);

        new PathNORegInitBuilder(status).start(route.getPartenza()).end(route.getArrivo()).city(route.getCitta()).build();

        PathController pathCtrl = new PathController();
        boolean salvato = pathCtrl.saveRoute(cred,dto,route, status);
        if(salvato)
            logger.info("[CLI]Percorso salvato correttamente per l'utente {} {} {} relativo alla città {}.", cred.getNome(), cred.getCognome(), cred.getRuolo(), route.getCitta());
        else
            logger.info("[CLI]Percorso non salvato per l'utente {} {} {} relativo alla città {}.", cred.getNome(), cred.getCognome(), cred.getRuolo(), route.getCitta());

        PathNOREGCLI.stampa();
        String result = "[CLI]Route from " + route.getPartenza() + " to " + route.getArrivo() + " in " + route.getCitta();
        logger.info(result);
    }
    private RouteBean estrattorePercorso()
    {
        try {
            RouteBean rb = new RouteBean();

            rb.setCitta(StartExploringCLI.getCity());
            rb.setPartenza(StartExploringCLI.getStazionePartenza());
            rb.setArrivo(StartExploringCLI.getStazioneArrivo());

            return rb;
        } catch (InvalidRouteInputExceptionRemoli e)
        {
            logger.error("Errore nell'input del percorso {}", e.getMessage());
            GenericErrorCLI.mostraErrore("Errore nell'input del percorso: "+e.getMessage());
            return null;
        } catch (InvalidBuyTicketInputExceptionBrondi e) {
            GenericErrorCLI.mostraErrore("Errore: " + e.getMessage());
            return null;
        }
    }
}