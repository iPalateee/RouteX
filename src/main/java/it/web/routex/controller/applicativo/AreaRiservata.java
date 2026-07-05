package it.web.routex.controller.applicativo;

import it.web.routex.bean.RouteBean;
import it.web.routex.bean.TicketBean;
import it.web.routex.dao.LayerPersistenzaDemo;
import it.web.routex.dao.TicketDAOLayer;
import it.web.routex.exception.InvalidRouteInputExceptionRemoli;
import it.web.routex.model.Route;
import java.util.ArrayList;
import java.util.List;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.InvalidTicketExceptionRemoli;
import it.web.routex.exception.PathNotFoundExceptionRemoli;
import it.web.routex.model.Ticket;
import it.web.routex.utility.decorator.decoratorpath.*;
import it.web.routex.utility.factory.LayerPersistenza;
import it.web.routex.utility.factory.FactoryPersistence;
import it.web.routex.utility.singleton.ApplicationModeManager;

public class AreaRiservata
{
    public List<TicketBean> runTicket(String cf)
            throws DAOExceptionBrondi, PathNotFoundExceptionRemoli {

        List<Ticket> tickets;

        if(ApplicationModeManager.getSingletonInstance().getMode().toString().equals("DEMO")){

            LayerPersistenzaDemo layer = new LayerPersistenzaDemo();
            tickets = layer.getTicketByCFDemo(cf);

        }
        else {

            TicketDAOLayer dao = FactoryPersistence.createTicketDAO();
            tickets = dao.getTicketByCF(cf);
        }

        List<TicketBean> beans = new ArrayList<>();

        for (Ticket t : tickets) {
            try {

                t.validate();

                TicketBean b = new TicketBean();
                b.setCodice(t.getCodice());
                b.setCitta(t.getCitta());
                b.setDataAcquisto(t.getDataAcquisto().toString());

                beans.add(b);

            } catch (InvalidTicketExceptionRemoli e) {

                throw new PathNotFoundExceptionRemoli(
                        "Sono stati trovati ticket non validi associati all'utente.",
                        cf,
                        500,
                        "Errore dominio Ticket: " + e.getMessage()
                );
            }
        }
        return beans;
    }


    public List<RouteBean> runPath(String cf)
            throws PathNotFoundExceptionRemoli, DAOExceptionBrondi, InvalidRouteInputExceptionRemoli {

        it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
        List<Route> listaPercorsi = layer.getData(cf);

        if (listaPercorsi == null || listaPercorsi.isEmpty()) {
            throw new PathNotFoundExceptionRemoli(
                    "Nessun percorso trovato per l'utente.",
                    cf,
                    404,
                    "PathControllerApplicativo.runPath"
            );
        }

        List<RouteBean> listaPercorsiBean = new ArrayList<>();

        Component component =
                new TempoArrivoDecorator(
                        new PercorsoLungoDecorator(
                                new ListaCambiDecorator(
                                        new BaseComponent()
                                )
                        )
                );

        for (Route r : listaPercorsi) {

            RouteBean rb = new RouteBean();
            rb.setPartenza(r.getPartenza());
            rb.setArrivo(r.getArrivo());
            rb.setCitta(r.getCitta());
            rb.setnCambi(r.getnCambi());
            rb.setListaCambi(r.getListaCambi());
            rb.setStazInterscambio(r.getStazInterscambio());
            rb.setnStazAttraversate(r.getnStazAttraversate());
            rb.setTempoDiArrivo(r.getTempoDiArrivo());
            rb.setnStazioniCitta(r.getnStazioniCitta());
            rb.setPercTerrenoUtilizzato(r.getPercTerrenoUtilizzato());

            rb = component.update(rb, r);

            listaPercorsiBean.add(rb);
        }

        return listaPercorsiBean;
    }

}
