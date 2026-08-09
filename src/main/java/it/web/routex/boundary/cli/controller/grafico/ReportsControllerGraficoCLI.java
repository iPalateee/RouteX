package it.web.routex.boundary.cli.controller.grafico;

import it.web.routex.bean.ReportsStatsBean;
import it.web.routex.boundary.cli.LoggedCLI;
import it.web.routex.boundary.cli.view.GenericErrorCLI;
import it.web.routex.boundary.cli.view.ViewReportsAndStatisticsCLI;
import it.web.routex.controller.applicativo.ReportsControllerApplicativo;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;
import it.web.routex.exception.InvalidRouteInputExceptionRemoli;

public class ReportsControllerGraficoCLI extends LoggedCLI {

    public void doGet() {

        try {
            ReportsControllerApplicativo service = new ReportsControllerApplicativo();
            ReportsStatsBean stats = service.recuperaStatistiche();

            ViewReportsAndStatisticsCLI.mostrareports(stats);

        } catch (DAOExceptionBrondi | InvalidBuyTicketInputExceptionBrondi | InvalidRouteInputExceptionRemoli e) {
            logger.error("Errore statistiche admin", e);
            GenericErrorCLI.mostraErrore("Errore nel recupero statistiche.");

        }
    }
}
