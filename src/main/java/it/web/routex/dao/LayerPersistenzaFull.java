package it.web.routex.dao;
import it.web.routex.enumerator.Ruolo;
import it.web.routex.exception.CredentialsExceptionBrondi;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.LoginNotFoundRemoli;
import it.web.routex.exception.PathNotFoundExceptionRemoli;
import it.web.routex.model.*;
import it.web.routex.utility.builder.RouteBuilder;
import it.web.routex.utility.factory.ConnectionFactory;
import it.web.routex.utility.factory.FactoryPersistence;
import it.web.routex.utility.singleton.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LayerPersistenzaFull extends LayerPersistenza
{
    public Credentials login(String email, String password)
            throws DAOExceptionBrondi, LoginNotFoundRemoli {

        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL RouteX_Update.login_user(?, ?) }")) {

            cs.setString(1, email);
            cs.setString(2, password);

            ResultSet rs = cs.executeQuery();

            if (!rs.next()) {
                throw new LoginNotFoundRemoli("Credenziali non valide per l'autenticazione.", email, password);
            }

            Credentials c = new Credentials();
            c.setCodiceFiscale(rs.getString("p_codiceFiscale"));
            c.setNome(rs.getString("p_nome"));
            c.setCognome(rs.getString("p_cognome"));
            c.setDataDiNascita(rs.getDate("p_dataDiNascita"));
            c.setDisabile(rs.getBoolean("p_disabile"));
            c.setRuolo(Ruolo.fromint(rs.getInt("ruolo")));
            c.setEmail(email);
            c.setPassword(password);

            return c;

        } catch (SQLException e) {
            throw new DAOExceptionBrondi("Errore durante il login: " + e.getMessage(), e);
        }
    }


    public Mastercard getPaymentMastercard(String nC, String sc, String cvv)
            throws DAOExceptionBrondi {

        final String query = "{ CALL RouteX_Update.getMastercardPayment(?,?,?) }";

        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cs = conn.prepareCall(query)) {

            cs.setString(1, nC);
            cs.setString(2, sc);
            cs.setString(3, cvv);

            try (ResultSet rs = cs.executeQuery()) {

                if (rs.next()) {
                    Mastercard found = new Mastercard();
                    found.setNumeroCarta(rs.getString("numero_carta"));
                    found.setDataScadenza(rs.getString("data_scadenza"));
                    found.setCvv(rs.getString("cvv"));
                    return found;
                }

                // nessun risultato → NON è errore DAO
                return null;
            }

        } catch (SQLException e) {
            throw new DAOExceptionBrondi(
                    "Errore interno alla connessione: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public Paypal getPaymentPaypal(String email, String codice)
            throws DAOExceptionBrondi {

        final String query = "{ CALL RouteX_Update.getPaypalPayment(?,?) }";

        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cs = conn.prepareCall(query)) {

            cs.setString(1, email);
            cs.setString(2, codice);

            try (ResultSet rs = cs.executeQuery()) {

                if (rs.next()) {
                    Paypal found = new Paypal();
                    found.setEmail(rs.getString("email_paypal"));
                    found.setCodice(rs.getString("codice_transazione"));
                    return found;
                }

                //  nessun risultato semantica neutra
                return null;
            }

        } catch (SQLException e) {
            throw new DAOExceptionBrondi(
                    "Errore durante il recupero del pagamento Paypal: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public City getCityByName(String nomeCitta) throws DAOExceptionBrondi {
        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL RouteX_Update.getCityByName(?) }")) {

            cs.setString(1, nomeCitta);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return new City(
                            rs.getString("nome_citta"),
                            rs.getDouble("prezzo_ticket"),
                            rs.getLong("numero_stazioni")
                    );
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DAOExceptionBrondi("Errore nella comunicazione con il database: " + e.getMessage(), e);
        }
    }


    @Override
    public List<City> listCities() throws DAOExceptionBrondi {

        final List<City> informazioni = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL RouteX_Update.getAllCity() }");
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("nome_citta");
                double costo = rs.getDouble("prezzo_ticket");
                long numero = rs.getLong("numero_stazioni");

                informazioni.add(new City(nome, costo, numero));
            }

            // può essere vuota, è lecito
            return informazioni;

        } catch (SQLException e) {
            throw new DAOExceptionBrondi(
                    "Errore nella comunicazione con il database: " + e.getMessage(),
                    e
            );
        }
    }


    @Override
    public List<Fermata> getFermateByIds(List<Integer> ids, String city) throws SQLException {
        List<Fermata> fermate = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL RouteX_Update.GetFermataById(?, ?) }")) {

            cs.setString(1, city);

            for (int id : ids) {
                cs.setInt(2, id);

                try (ResultSet rs = cs.executeQuery()) {
                    while (rs.next()) {
                        fermate.add(
                                new Fermata(
                                        rs.getString("nome"),
                                        rs.getString("linea")
                                )
                        );
                    }
                }
            }
        }
        return fermate;
    }

    @Override
    public List<Station> restituisciIdStazioni(String startStation, String endStation, String city) throws SQLException
    {
        String procedure = "{CALL RouteX_Update.RestituisciStazioni(?,?,?)}";
        List<Station> stations = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cs = conn.prepareCall(procedure))
        {
            cs.setString(1, startStation);
            cs.setString(2, endStation);
            cs.setString(3, city);

            boolean hasResults = cs.execute();
            int resultIndex = 0;

            while (hasResults) {
                try (ResultSet rs = cs.getResultSet()) {
                    resultIndex++;
                    if (rs != null) {
                        if (resultIndex == 1 && rs.next()) {
                            stations.add(new Station(rs.getInt("id")));
                        }

                        if (resultIndex == 2 && rs.next()) {
                            stations.add(new Station(rs.getInt("id")));
                        }
                    }
                }
                hasResults = cs.getMoreResults();
            }

        } catch (SQLException e) {
            throw new SQLException("Errore durante l'esecuzione della stored procedure RestituisciStazioni", e);
        }
        return stations;
    }

    @Override
    public List<Notification> getMessages() throws DAOExceptionBrondi {

        List<Notification> result = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL RouteX_Update.getMessages() }")) {

            if (cs.execute()) {
                try (ResultSet rs = cs.getResultSet()) {
                    while (rs.next()) {
                        Notification notification = new Notification(
                                rs.getString("testo"),
                                rs.getTimestamp("data"),
                                rs.getBoolean("risolto")
                        );
                        result.add(notification);
                    }
                }
            }
            //  NESSUN controllo isEmpty()
            return result;

        } catch (SQLException e) {
            throw new DAOExceptionBrondi(
                    "Errore nel recupero delle notifiche",
                    e
            );
        }
    }

    @Override
    public void save(Route route) throws DAOExceptionBrondi {
        try (Connection conn = ConnectionFactory.getConnection()){
            String sp = "{ CALL RouteX_Update.saveRoute(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
            CallableStatement cs = conn.prepareCall(sp);

            cs.setString(1, route.getPartenza());
            cs.setString(2, route.getArrivo());
            cs.setString(3, route.getCitta());
            cs.setString(4, route.getTipoViaggiatore());
            cs.setInt(5, route.getnCambi());
            cs.setString(6, route.getListaCambi());
            cs.setString(7, route.getStazInterscambio());
            cs.setDouble(8, route.getnStazAttraversate());
            cs.setDouble(9, route.getTempoDiArrivo());
            cs.setInt(10, route.getnStazioniCitta());
            cs.setDouble(11, route.getPercTerrenoUtilizzato());
            cs.setString(12, route.getUtente()); // qui salvo utente

            cs.execute();

        } catch (Exception e) {
            throw new DAOExceptionBrondi("Errore durante la registrazione del percorso: " + e.getMessage());
        }
    }


    @Override
    public WorkerSchedule getWorkerSchedule(String codiceFiscale)
            throws DAOExceptionBrondi {

        String sp = "{ CALL RouteX_Update.viewWorkSchedule(?) }";

        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cs = conn.prepareCall(sp)) {

            cs.setString(1, codiceFiscale);

            try (ResultSet rs = cs.executeQuery()) {

                //  NESSUNA ECCEZIONE: se non c'è risultato, ritorno null
                if (!rs.next()) {
                    return null;
                }

                int oraInizio = rs.getInt("oraInizio");
                int oraFine   = rs.getInt("oraFine");
                String luogo  = rs.getString("luogoDiLavoro");

                return new WorkerSchedule(
                        oraInizio,
                        oraFine,
                        luogo
                );
            }

        } catch (SQLException e) {
            throw new DAOExceptionBrondi(
                    "Errore durante la chiamata a viewWorkSchedule",
                    e
            );
        }
    }


    @Override
    public void sendMessage(Notification n) throws DAOExceptionBrondi {

        try (Connection conn = ConnectionFactory.getConnection()) {

            CallableStatement cs = conn.prepareCall("{ CALL RouteX_Update.spCommunication(?, ?, ?) }");

            cs.setString(1, n.getMessage());
            cs.setTimestamp(2, n.getDate());
            cs.setBoolean(3, n.isRisolto());

            cs.execute();

        } catch (Exception e) {
            throw new DAOExceptionBrondi(
                    "Errore durante l'invio della comunicazione",
                    e
            );
        }
    }

    @Override
    public void solvedNotification(Notification n) throws DAOExceptionBrondi {

        try (Connection conn = ConnectionFactory.getConnection()) {

            CallableStatement cs = conn.prepareCall("{ CALL RouteX_Update.spSolvedMessage(?, ?, ?) }");

            cs.setString(1, n.getMessage());
            cs.setTimestamp(2, n.getDate());
            cs.setBoolean(3, n.isRisolto());

            cs.execute();

        } catch (Exception e) {
            throw new DAOExceptionBrondi(
                    "Errore durante l'aggiornamento della notifica",
                    e
            );
        }
    }
    @Override
    public List<Route> getAllPathInfo() throws DAOExceptionBrondi {

        List<Route> resultList = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection()) {

            CallableStatement cs =
                    conn.prepareCall("{ CALL RouteX_Update.getAllPathInfo() }");

            if (cs.execute()) {
                try (ResultSet rs = cs.getResultSet()) {
                    while (rs.next()) {

                        Route info = new RouteBuilder(rs.getString("StartStation"))
                                .endStation(rs.getString("EndStation"))
                                .city(rs.getString("City"))
                                .tipoViaggiatore(rs.getString("TipoViaggiatore"))
                                .nCambi(rs.getInt("NCambi"))
                                .listaCambi(rs.getString("ListaCambi"))
                                .stazioneDiInterscambio(rs.getString("StazioneDiInterscambio"))
                                .nStazioniAttraversate(rs.getInt("NStazioniAttraversate"))
                                .tempoDiArrivo(rs.getDouble("TempoDiArrivo"))
                                .nStazioniCitta(rs.getInt("NStazioniCitta"))
                                .percTerrenoUtilizzato(rs.getDouble("PercTerrenoUtilizzato"))
                                .utente(rs.getString("Utente"))
                                .build();


                        resultList.add(info);
                    }
                }
            }

        } catch (SQLException e) {
            throw new DAOExceptionBrondi(
                    "Errore nel recupero delle statistiche PathInfo",
                    e
            );
        }

        return resultList;
    }

    @Override
    public List<Route> getData(String cf)
            throws PathNotFoundExceptionRemoli, DAOExceptionBrondi {

        try (Connection conn = ConnectionFactory.getConnection())
        {

            CallableStatement cs =
                    conn.prepareCall("{ CALL RouteX_Update.getPath(?) }");

            cs.setString(1, cf);
            cs.execute();

            ResultSet rs = cs.getResultSet();
            List<Route> lista = new ArrayList<>();

            while (rs.next()) {
                Route r = new Route();
                r.setPartenza(rs.getString("StartStation"));
                r.setArrivo(rs.getString("EndStation"));
                r.setCitta(rs.getString("City"));
                r.setTipoViaggiatore(rs.getString("TipoViaggiatore"));
                r.setnCambi(rs.getInt("NCambi"));
                r.setnStazAttraversate(rs.getInt("NStazioniAttraversate"));
                r.setTempoDiArrivo(rs.getDouble("TempoDiArrivo"));
                r.setnStazioniCitta(rs.getInt("NStazioniCitta"));
                r.setPercTerrenoUtilizzato(rs.getDouble("PercTerrenoUtilizzato"));
                r.setListaCambi(rs.getString("ListaCambi"));
                r.setStazInterscambio(rs.getString("StazioneDiInterscambio"));
                r.setUtente(cf);

                lista.add(r);
            }

            if (lista.isEmpty())
            {
                throw new PathNotFoundExceptionRemoli(
                        "Nessun percorso trovato per l'utente.",
                        cf,
                        404,
                        "RouteDAO.getData"
                );
            }

            return lista;
        } catch (SQLException e) {

            throw new DAOExceptionBrondi(
                    "Errore durante la connessione al database",
                    e
            );
        }
    }

    @Override
    public void salvataggio(Credentials cred, List<String> codiciBiglietti, String metodoPagamento, String city) throws CredentialsExceptionBrondi {
        TicketDAOLayer dao = FactoryPersistence.createTicketDAO();
        dao.salvataggio(cred, codiciBiglietti, metodoPagamento, city);
        final Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Modalità FULL: il pagamento  viene salvato in persistenza.");

    }

}
