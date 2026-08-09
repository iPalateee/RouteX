package it.web.routex.utility.text;

import it.web.routex.bean.RouteBean;
import it.web.routex.bean.TicketBean;
import it.web.routex.controller.applicativo.AreaRiservata;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;
import it.web.routex.exception.InvalidRouteInputExceptionRemoli;
import it.web.routex.exception.PathNotFoundExceptionRemoli;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AreaRiservataHelper {

    private AreaRiservataHelper() {}
    public static List<RouteBean> estraiPercorsi(AreaRiservata reserved, String cf, Logger logger) throws DAOExceptionBrondi, InvalidRouteInputExceptionRemoli {
        try {
            return reserved.runPath(cf);
        } catch (PathNotFoundExceptionRemoli e) {
            logger.info("Nessun percorso trovato per l'utente {}. La lista resterà vuota", cf);
            return new ArrayList<>();
        } catch (InvalidBuyTicketInputExceptionBrondi e) {
            logger.info("Errore: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<TicketBean> estraiBiglietti(AreaRiservata reserved, String cf, Logger logger) throws DAOExceptionBrondi {
        try {
            return reserved.runTicket(cf);
        } catch (PathNotFoundExceptionRemoli e) {
            logger.info("Nessun biglietto trovato per l'utente {}. La lista resterà vuota.", cf);
            return new ArrayList<>();
        }
    }
}