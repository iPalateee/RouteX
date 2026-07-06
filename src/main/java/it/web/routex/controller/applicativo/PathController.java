package it.web.routex.controller.applicativo;
import it.web.routex.bean.InformazioniPercorsoBean;
import it.web.routex.bean.RouteBean;
import it.web.routex.bean.RoutingRequestBean;
import it.web.routex.exception.*;
import it.web.routex.model.Route;
import it.web.routex.model.Station;
import it.web.routex.utility.factory.LayerPersistenza;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import it.web.routex.utility.facade.FacadePath;
import it.web.routex.utility.singleton.Credentials;
import java.sql.SQLException;
import java.util.List;


public class PathController
{
    public InformazioniPercorsoBean run(String startStation, String endStation, String city) throws IllegalArgumentException, FuoriRangeExceptionBrondi, UnreacheableNodeExceptionRemoli, SQLException, DAOExceptionBrondi
    {
        it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
        List<Station> stations = layer.restituisciIdStazioni(startStation, endStation, city);

        if (stations.size() != 2) {
            throw new DAOExceptionBrondi(
                    "Numero di stazioni restituito non valido: " + stations.size()
            );
        }

        final Logger logger = LoggerFactory.getLogger(getClass());
        Station partenza = stations.get(0);
        Station arrivo   = stations.get(1);

        try {
            partenza.validate();
            arrivo.validate();
        } catch (IllegalStateException e) {
            throw new DAOExceptionBrondi(
                    "Stazioni non valide nella città selezionata: " + city,
                    e
            );
        }

        logger.info("Stazioni trovate. Id stazione di partenza = {} e stazione di arrivo = {}", partenza.getId(), arrivo.getId());

        RoutingRequestBean route = new RoutingRequestBean();
        route.setCity(city);
        route.setStartId(partenza.getId());
        route.setEndId(arrivo.getId());
        return new FacadePath().compute(route);
    }
    public boolean saveRoute(Credentials cred, InformazioniPercorsoBean dto, RouteBean route, String status) {

        final Logger logger = LoggerFactory.getLogger(getClass());
        String cf = cred.getCodiceFiscale();
        try {

            if (cf != null) {
                Route info = new Route(dto,route,status);
                it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
                layer.save(info);
                //uso route per salvare il percorso. Poi RouteBean è diverso, non ha utente
                return true;
            } else {
                throw new CFIsNullBrondi("Devi effettuare il login per salvare il percorso.",
                        "CF nullo: richiesta di salvataggio senza autenticazione.",
                        "ERR-CF-NULL",
                        CFIsNullBrondi.Severity.CRITICAL);
            }
        }catch (CFIsNullBrondi e)
        {
            logger.error("Utente non autenticato, impossibile salvare il percorso. {}", e.toString());
        }
        catch (DAOExceptionBrondi e)
        {
            logger.error("Errore nel salvataggio del percorso. {}", e.toString());
        } catch (InvalidRouteException e) {
            logger.error("Errore nella conversione dei dati nel percorso. {}", e.toString());
        }
        return false;
    }
}
