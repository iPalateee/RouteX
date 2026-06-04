package it.web.routex.boundary.cli.controller.grafico;

import it.web.routex.bean.AutenticazioneBean;
import it.web.routex.bean.UtenteBeanGenerico;
import it.web.routex.boundary.cli.CLIRoute;
import it.web.routex.boundary.cli.LoggedCLI;
import it.web.routex.boundary.cli.extractor.LoginExtractorCLI;
import it.web.routex.boundary.cli.view.*;
import it.web.routex.controller.applicativo.LoginController;
import it.web.routex.exception.DAOExceptionRemoli;
import it.web.routex.exception.InvalidLoginInputExceptionRemoli;
import it.web.routex.exception.LoginNotFoundRemoli;
import it.web.routex.record.LoginRecord;
import it.web.routex.utility.factory.ConnectionFactory;
import java.sql.SQLException;

public class LoginControllerGraficoCLI extends LoggedCLI {


    public void post()
    {
        try {
        AutenticazioneBean credenziali = creaBeanAutenticazione();

        if (credenziali == null) return;

        LoginController loginController = new LoginController(credenziali);
        UtenteBeanGenerico utente = loginController.autenticaUtente();
        logger.info("[CLI]Utente perfettamente autenticato: nome={}, cognome={}, ruolo={}", utente.getNome(), utente.getCognome(), utente.getRuolo());

        gestisciReindirizzamento(utente);


        } catch (DAOExceptionRemoli ex) {
            gestisciErroreLogin(ex);

        } catch (LoginNotFoundRemoli ex) {
            ErroreLoginCLI.mostraErrore(ex.getMessage());
            LoginViewCLI.mostraLogin();
            logger.error("[CLI]Tentativo di login fallito: email={}, Maskedpassw={}, message={}", ex.getEmail(), ex.getMaskedPassword(), ex.getMessage());
        }


    }
    private AutenticazioneBean creaBeanAutenticazione() {
        try {
            LoginRecord login = LoginExtractorCLI.from();

            AutenticazioneBean aut = new AutenticazioneBean();
            aut.setEmail(login.email());
            aut.setPassword(login.password());

            logger.info(
                    "Bean di autenticazione creato con email: {}, password presente={}",
                    login.email(),
                    login.password() != null
            );

            return aut;

        } catch (InvalidLoginInputExceptionRemoli e) {
            ErroreLoginCLI.mostraErrore(e.getUserMessage());
            logger.error("[CLI]Errore di validazione input login: {}", e.toString());
            return null;   // ✅ SOLO QUESTO
        }
    }
    private void gestisciReindirizzamento(UtenteBeanGenerico utente)
    {
        try {
            ConnectionFactory.cambioDiRuolo(utente.getRuolo());

            switch (utente.getRuolo().toString().toUpperCase()) {
                case "TRAVELER" -> safeRedirect(CLIRoute.TRAVELER_HOME);

                case "WORKER" -> safeRedirect(CLIRoute.WORKER_DASHBOARD);

                case "ADMIN" -> safeRedirect(CLIRoute.ADMIN_HOME);

                default -> safeRedirect(CLIRoute.ERRORE_LOGIN);
            }
        } catch (SQLException e) {
            ErroreLoginCLI.mostraErrore("Errore nel cambio di ruolo.");

        }
    }
    private void safeRedirect(CLIRoute route) {
        try {
            switch (route) {

                case TRAVELER_HOME:
                    new IndexLoggedCLI().mostraMenu();
                    return;

                case WORKER_DASHBOARD:
                    new WorkerViewCLI().mostraDashboard();
                    return;

                case ADMIN_HOME:
                    new AdminViewCLI().mostraHomeAdmin();
                    return;

                case ERRORE_LOGIN:
                    ErroreLoginCLI.mostraErrore("Errore di autenticazione.");
            }

        } catch (Exception e) {
            logger.error("[CLI]Errore nel redirect CLI verso {}", route, e);
        }
    }
    private void gestisciErroreLogin(DAOExceptionRemoli ex)
    {
        try {
            ErroreLoginCLI.mostraErrore("Errore nella connesione al database.");
            logger.error("[CLI]Errore DAO durante il login: message={}", ex.getMessage());
        }catch(Exception e) {
            logger.error("[CLI]Errore generico non catturato: message={}", e.getMessage());
        }
    }
}
