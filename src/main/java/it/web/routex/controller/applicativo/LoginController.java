package it.web.routex.controller.applicativo;

import it.web.routex.bean.AutenticazioneBean;
import it.web.routex.bean.UtenteBeanGenerico;
import it.web.routex.dao.LayerPersistenza;
import it.web.routex.utility.factory.FactoryLayerPersistenza;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import it.web.routex.utility.singleton.Credentials;
import it.web.routex.exception.DAOExceptionRemoli;
import it.web.routex.exception.LoginNotFoundRemoli;

public class LoginController {

    private final AutenticazioneBean autenticazione;

    public LoginController(AutenticazioneBean autenticazione) {
        this.autenticazione = autenticazione;
    }

    public UtenteBeanGenerico autenticaUtente() throws DAOExceptionRemoli, LoginNotFoundRemoli {

        final Logger logger = LoggerFactory.getLogger(getClass());

        LayerPersistenza layer = FactoryLayerPersistenza.createLayerPersistenza();
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
