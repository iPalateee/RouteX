package it.web.routex.controller.grafico;
import it.web.routex.bean.AutenticazioneBean;
import it.web.routex.bean.UtenteBeanGenerico;
import it.web.routex.controller.applicativo.LoginController;
import it.web.routex.exception.DAOExceptionRemoli;
import it.web.routex.domain.LoggedHttpServlet;
import it.web.routex.extractor.LoginExtractor;
import it.web.routex.record.LoginRecord;
import it.web.routex.utility.factory.ConnectionFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import it.web.routex.exception.LoginNotFoundRemoli;
import it.web.routex.exception.InvalidLoginInputExceptionRemoli;

/**
 * Controller grafico per la gestione del login utente.
 * Si occupa di ricevere i dati dal form, delegare la logica al controller applicativo
 * e reindirizzare l'utente alla pagina corretta in base al ruolo.
 */
@WebServlet("/login")
public class LoginControllerGrafico extends LoggedHttpServlet {

    /**
     * Crea il bean di autenticazione a partire dai parametri del form.
     */

    private static final String ATTR_MESSAGGIO_ERRORE = "messaggioErrore";
    private static final String PAGE_ERRORE_LOGIN = "/erroreLogin.jsp";
    private static final String FORWARDING = "Errore nel forwarding";



    private AutenticazioneBean creaBeanAutenticazione(HttpServletRequest request, HttpServletResponse response) {
        AutenticazioneBean aut = new AutenticazioneBean();
        LoginRecord login = null;

        try {
            login = LoginExtractor.from(request);
        } catch (InvalidLoginInputExceptionRemoli e) {
            request.setAttribute(ATTR_MESSAGGIO_ERRORE, e.getUserMessage());
            try {
                request.getRequestDispatcher(PAGE_ERRORE_LOGIN).forward(request, response);
            }catch(Exception ex){
                logger.error(FORWARDING,ex);
            }
            logger.error("Errore di validazione input login: {}", e.toString());
        }
        aut.setEmail(login.email());
        aut.setPassword(login.password());
        logger.info(
                "Bean di autenticazione creato con email: {}, password presente={}",
                login.email(),
                login.password() != null
        );

        return aut;
    }

    /**
     * Gestisce il reindirizzamento in base al ruolo dell'utente autenticato.
     */
    private void gestisciReindirizzamento(UtenteBeanGenerico utente, HttpServletResponse response)
    {
        try {

            ConnectionFactory.cambioDiRuolo(utente.getRuolo());

            switch (utente.getRuolo().toString().toUpperCase()) {

                case "TRAVELER" -> safeRedirect(response, "indexLogged.jsp");

                case "WORKER" -> safeRedirect(response, "dashboardWorker.jsp");

                case "ADMIN" -> safeRedirect(response, "indexAdmin.jsp");

                default -> safeRedirect(response, "erroreLogin.jsp");
            }
        } catch (SQLException e) {
            try {
                response.sendRedirect("erroreLogin.jsp");
            }catch(Exception ex){
                logger.error(FORWARDING,ex);
            }
        }
    }
    private void safeRedirect(HttpServletResponse response, String pagina) {
        try {
            response.sendRedirect(pagina);
        } catch (IOException e) {
            logger.error("Errore nel redirect verso {}", pagina, e);
        }
    }


    /**
     * Gestisce eventuali errori di login (DAO o credenziali errate).
     */
    private void gestisciErroreLogin(HttpServletRequest request, HttpServletResponse response, DAOExceptionRemoli ex)
    {
        try {
            request.setAttribute(ATTR_MESSAGGIO_ERRORE, "Errore nella connessione al DB [500 internal error]");
            request.getRequestDispatcher(PAGE_ERRORE_LOGIN).forward(request, response);
            logger.error("Errore DAO durante il login: message={}", ex.getMessage());
        }catch(Exception e) {
            logger.error("Errore generico non catturato: message={}", e.getMessage());
        }
    }

    /**
     * Metodo principale di gestione del login.
     * Riceve i dati dal form, invoca il controller applicativo e imposta la sessione.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {

            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(180); // 3 minuti di inattività

            AutenticazioneBean credenziali = creaBeanAutenticazione(request,response);

            LoginController loginController = new LoginController(credenziali);
            UtenteBeanGenerico utente = loginController.autenticaUtente();

            session.setAttribute("nome", utente.getNome());
            session.setAttribute("cognome", utente.getCognome());
            session.setAttribute("ruolo", utente.getRuolo());

            logger.info("Utente perfettamente autenticato: nome={}, cognome={}, ruolo={}", utente.getNome(), utente.getCognome(), utente.getRuolo());

            gestisciReindirizzamento(utente, response);

        } catch (DAOExceptionRemoli ex) {
            gestisciErroreLogin(request, response, ex);

        } catch (LoginNotFoundRemoli ex) {
            request.setAttribute(ATTR_MESSAGGIO_ERRORE, ex.getMessage());
            try {
                request.getRequestDispatcher(PAGE_ERRORE_LOGIN).forward(request, response);
            }catch(Exception e){
                logger.error(FORWARDING,e);
            }
            logger.error("Tentativo di login fallito: email={}, Maskedpassw={}, message={}", ex.getEmail(), ex.getMaskedPassword(), ex.getMessage());
        }


    }
}

/*
Se la password deve stare nell'eccezione, allora la inserisco, ma sempre in modo sicuro, cioè MAI in chiaro,
perché una eccezione può finire nel log e il log è consultabile da tutti.
 */