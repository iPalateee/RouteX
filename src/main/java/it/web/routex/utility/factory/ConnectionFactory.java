package it.web.routex.utility.factory;

import it.web.routex.exception.ConfigurationException;
import it.web.routex.enumerator.Ruolo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static String connectionUrl;
    private static String currentUser;
    private static String currentPass;
    private ConnectionFactory() {
        throw new UnsupportedOperationException("Classe di utility - non istanziabile");
    }


    static {
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null)
                throw new ConfigurationException("Impossibile trovare il file db.properties nel classpath!");

            Class.forName("com.mysql.cj.jdbc.Driver");

            Properties properties = new Properties();
            properties.load(input);

            connectionUrl = properties.getProperty("CONNECTION_URL");
            currentUser = properties.getProperty("LOGIN_USER");
            currentPass = properties.getProperty("LOGIN_PASS");

        } catch (IOException | ClassNotFoundException e) {
            throw new ConfigurationException("Errore durante il caricamento delle impostazioni DB: " + e.getMessage());
        }
    }

    /**
     *  Restituisce una nuova connessione viva ogni volta.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl, currentUser, currentPass);
    }

    /**
     *  Cambia ruolo (utente DB) aggiornando le credenziali per le connessioni future.
     *  Non chiude connessioni usate da altri thread.
     */
    public static void cambioDiRuolo(Ruolo ruolo) throws SQLException
    {
        final Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null)
                throw new ConfigurationException("Impossibile trovare il file db.properties nel classpath!");

            Properties properties = new Properties();
            properties.load(input);

            currentUser = properties.getProperty(ruolo.name() + "_USER");
            currentPass = properties.getProperty(ruolo.name() + "_PASS");

            logger.info("[ConnectionFactory] Cambio ruolo a  {} ! (user: {}).",ruolo, currentUser);
        } catch (IOException e) {
            throw new SQLException("Errore durante il cambio di ruolo: " + e.getMessage());
        }
    }
}

