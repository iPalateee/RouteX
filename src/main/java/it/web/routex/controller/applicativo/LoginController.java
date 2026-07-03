package it.web.routex.controller.applicativo;

import it.web.routex.bean.AutenticazioneBean;
import it.web.routex.bean.UtenteBeanGenerico;
import it.web.routex.utility.factory.LayerPersistenza;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import it.web.routex.utility.singleton.Credentials;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.LoginNotFoundRemoli;

public class LoginController {

    private final AutenticazioneBean autenticazione;

    public LoginController(AutenticazioneBean autenticazione) {
        this.autenticazione = autenticazione;
    }

    public UtenteBeanGenerico autenticaUtente() throws DAOExceptionBrondi, LoginNotFoundRemoli {

        final Logger logger = LoggerFactory.getLogger(getClass());

        it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
        Credentials credFromDb = layer.login(autenticazione.getEmail(), autenticazione.getPassword());

        Credentials sessionCred = Credentials.getInstanceSingleton();

        sessionCred.setCodiceFiscale(credFromDb.getCodiceFiscale());
        sessionCred.setNome(credFromDb.getNome());
        sessionCred.setCognome(credFromDb.getCognome());
        sessionCred.setDataDiNascita(credFromDb.getDataDiNascita());
        sessionCred.setDisabile(credFromDb.getDisabile());
        sessionCred.setRuolo(credFromDb.getRuolo());
        sessionCred.setEmail(credFromDb.getEmail());
        sessionCred.setPassword(credFromDb.getPassword());


        logger.info("Funzione autenticaUtente() dentro LoginController.java con autenticazione {} e {}", sessionCred.getNome(), sessionCred.getCognome());

        UtenteBeanGenerico utente = new UtenteBeanGenerico();
        utente.setNome(sessionCred.getNome());
        utente.setCognome(sessionCred.getCognome());
        utente.setCodiceFiscale(sessionCred.getCodiceFiscale());
        utente.setDisable(sessionCred.getDisabile());
        utente.setRuolo(sessionCred.getRuolo());

        return utente;
    }
}
