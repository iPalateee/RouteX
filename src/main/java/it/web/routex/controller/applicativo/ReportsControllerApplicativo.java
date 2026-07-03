package it.web.routex.controller.applicativo;

import it.web.routex.bean.PathInfoBean;
import it.web.routex.bean.ReportsStatsBean;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.model.Route;
import it.web.routex.utility.factory.LayerPersistenza;

import java.util.*;

public class ReportsControllerApplicativo {

    public ReportsStatsBean recuperaStatistiche() throws DAOExceptionBrondi {

        it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
        List<Route> models=layer.getAllPathInfo();

        ReportsStatsBean stats = new ReportsStatsBean();

        List<PathInfoBean> beans = new ArrayList<>();
        Set<String> utenti = new HashSet<>();

        double totalDistance = 0;
        double totalTime = 0;

        for (Route r : models) {

            PathInfoBean b = new PathInfoBean();
            b.setStartStation(r.getPartenza());
            b.setEndStation(r.getArrivo());
            b.setCity(r.getCitta());
            b.setTipoViaggiatore(r.getTipoViaggiatore());
            b.setNCambi(r.getnCambi());
            b.setListaCambi(r.getListaCambi());
            b.setStazioneDiInterscambio(r.getStazInterscambio());
            b.setNStazioniAttraversate(r.getnStazAttraversate());
            b.setTempoDiArrivo(r.getTempoDiArrivo());
            b.setNStazioniCitta(r.getnStazioniCitta());
            b.setPercTerrenoUtilizzato(r.getPercTerrenoUtilizzato());
            b.setUtente(r.getUtente());

            beans.add(b);
            utenti.add(r.getUtente());

            totalDistance += r.getPercTerrenoUtilizzato();
            totalTime += r.getTempoDiArrivo();
        }

        stats.setPaths(beans);
        stats.setUtenti(utenti);
        stats.setTotalTrips(beans.size());
        stats.setTotalDistance(totalDistance);
        stats.setTotalTime(totalTime);

        return stats;
    }
}
