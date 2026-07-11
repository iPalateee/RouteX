package it.web.routex.dao;

import it.web.routex.demo.*;
import it.web.routex.exception.*;
import it.web.routex.model.*;
import it.web.routex.utility.builder.RouteBuilder;
import it.web.routex.utility.singleton.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class LayerPersistenzaDemo extends LayerPersistenza{
    @Override
    public Credentials login(String email, String password)
            throws DAOExceptionBrondi, LoginNotFoundRemoli {

        try {
            for (User u : DemoStorage.getUsers()) {

                if (u.getEmail().equals(email) && u.getPassword().equals(password)) {

                    Credentials cred = new Credentials();

                    cred.setCodiceFiscale(u.getCodiceFiscale());
                    cred.setNome(u.getNome());
                    cred.setCognome(u.getCognome());
                    cred.setDataDiNascita(u.getDataDiNascita());
                    cred.setDisabile(u.isDisabile());
                    cred.setRuolo(u.getRuolo());
                    cred.setEmail(email);
                    cred.setPassword(password);

                    return cred;
                }
            }

            throw new LoginNotFoundRemoli("Credenziali non valide.", email, password);

        } catch (LoginNotFoundRemoli e) {
            throw e;
        } catch (Exception e) {
            throw new DAOExceptionBrondi(
                    "Errore durante il login in modalità DEMO: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public Mastercard getPaymentMastercard(String nC, String sc, String cvv)
            throws DAOExceptionBrondi {

        try {
            for (Mastercard m : DemoStorage.getMastercards()) {

                if (m.getNumeroCarta().equals(nC)
                        && m.getDataScadenza().equals(sc)
                        && m.getCvv().equals(cvv)) {

                    Mastercard found = new Mastercard();
                    found.setNumeroCarta(m.getNumeroCarta());
                    found.setDataScadenza(m.getDataScadenza());
                    found.setCvv(m.getCvv());

                    return found;
                }
            }

            //  nessun risultato : NON errore DAO
            return null;

        } catch (Exception e) {
            throw new DAOExceptionBrondi(
                    "Errore interno alla persistenza DEMO: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public Paypal getPaymentPaypal(String email, String codice)
            throws DAOExceptionBrondi {

        try {
            for (Paypal p : DemoStorage.getPaypals()) {

                if (p.getEmail().equals(email)
                        && p.getCodice().equals(codice)) {

                    Paypal found = new Paypal();
                    found.setEmail(p.getEmail());
                    found.setCodice(p.getCodice());
                    return found;
                }
            }

            return null;

        } catch (Exception e) {
            throw new DAOExceptionBrondi(
                    "Errore nel recupero del pagamento Paypal in modalità DEMO: " + e.getMessage(),
                    e
            );
        }
    }


    @Override
    public List<City> listCities() throws DAOExceptionBrondi {

        try {
            List<City> informazioni = new ArrayList<>();

            for (City c : DemoStorage.getCities()) {
                informazioni.add(
                        new City(
                                c.getName(),
                                c.getCostoBiglietto(),
                                c.getNumeroStazioni()
                        )
                );
            }

            return informazioni;

        } catch (Exception e) {
            throw new DAOExceptionBrondi(
                    "Errore nella comunicazione con il database: " + e.getMessage(),
                    e
            );
        }
    }



    @Override
    public List<Fermata> getFermateByIds(List<Integer> ids, String city) throws SQLException {

        List<Fermata> fermateAll = new ArrayList<>();

        try {
            for (int id : ids) {

                for (Fermate f : DemoStorage.getFermate()) {

                    if (id == f.getId()) {
                        fermateAll.add(
                                new Fermata(
                                        f.getNome(),
                                        f.getLinea()
                                )
                        );
                    }
                }
            }

            return fermateAll;

        } catch (Exception e) {
            throw new SQLException("Errore durante il recupero delle fermate (DEMO)", e);
        }
    }


    @Override
    public List<Station> restituisciIdStazioni(String startStation, String endStation, String city) throws SQLException {

        List<Station> stations = new ArrayList<>();

        try {
            Integer startId = null;
            Integer endId = null;

            // simulazione della stored procedure
            for (Fermate f : DemoStorage.getFermate()) {

                if (startId == null && f.getNome().equalsIgnoreCase(startStation)) {
                    startId = f.getId();
                }

                if (endId == null && f.getNome().equalsIgnoreCase(endStation)) {
                    endId = f.getId();
                }
            }

            // primo result set
            if (startId != null) {
                stations.add(new Station(startId));
            }

            // secondo result set
            if (endId != null) {
                stations.add(new Station(endId));
            }

            return stations;

        } catch (Exception e) {
            throw new SQLException(
                    "Errore durante la simulazione della stored procedure RestituisciStazioni",
                    e
            );
        }
    }


    @Override
    public List<Notification> getMessages() throws DAOExceptionBrondi {

        List<Notification> result = new ArrayList<>();

        try {
            // simulazione del ResultSet
            for (Notification n : DemoStorage.getNotifications()) {

                Notification copy = new Notification(
                        n.getMessage(),
                        n.getDate(),
                        n.isRisolto()
                );

                result.add(copy);
            }

            //  NESSUN controllo su lista vuota
            return result;

        } catch (Exception e) {
            throw new DAOExceptionBrondi(
                    "Errore nel recupero delle notifiche (DEMO)",
                    e
            );
        }
    }



    @Override
    public void save(Route route) throws DAOExceptionBrondi {

        try {
            if (route == null) {
                throw new DAOExceptionBrondi(
                        "Errore durante la registrazione del percorso: route null"
                );
            }

            // simulazione INSERT INTO Route
            Route copy = new RouteBuilder(route.getPartenza()).endStation(route.getArrivo())
                            .city(route.getCitta()).tipoViaggiatore(route.getTipoViaggiatore())
                            .nCambi(route.getnCambi()).listaCambi(route.getListaCambi())
                            .stazioneDiInterscambio(route.getStazInterscambio()).nStazioniAttraversate(route.getnStazAttraversate())
                            .tempoDiArrivo(route.getTempoDiArrivo()).nStazioniCitta(route.getnStazioniCitta())
                            .percTerrenoUtilizzato(route.getPercTerrenoUtilizzato()).utente(route.getUtente())
                            .build();

            DemoStorage.getRoutes().add(copy);

        } catch (DAOExceptionBrondi e) {
            throw e;

        } catch (Exception e) {
            throw new DAOExceptionBrondi(
                    "Errore durante la registrazione del percorso: " + e.getMessage()
            );
        }
    }


    @Override
    public WorkerSchedule getWorkerSchedule(String codiceFiscale)
            throws DAOExceptionBrondi {

        try {
            for (WorkerScheduleRecord r : DemoStorage.getWorkerSchedules()) {

                if (r.getCodiceFiscale().equals(codiceFiscale)) {
                    return new WorkerSchedule(
                            r.getOraInizio(),
                            r.getOraFine(),
                            r.getLuogoDiLavoro()
                    );
                }
            }
            return null;

        } catch (Exception e) {
            throw new DAOExceptionBrondi(
                    "Errore durante il recupero degli orari di lavoro in modalità DEMO",
                    e
            );
        }
    }



    @Override
    public void sendMessage(Notification n) throws DAOExceptionBrondi {

        try {
            if (n == null) {
                throw new DAOExceptionBrondi("Errore durante l'invio della comunicazione");
            }

            // simulazione INSERT INTO communication
            Notification copy = new Notification(
                    n.getMessage(),
                    n.getDate(),
                    n.isRisolto()
            );

            DemoStorage.getNotifications().add(copy);

        } catch (DAOExceptionBrondi e) {
            throw e;

        } catch (Exception e) {
            throw new DAOExceptionBrondi(
                    "Errore durante l'invio della comunicazione",
                    e
            );
        }
    }


    @Override
    public void solvedNotification(Notification n) throws DAOExceptionBrondi {

        try {
            if (n == null) {
                throw new DAOExceptionBrondi(
                        "Errore durante l'aggiornamento della notifica"
                );
            }

            boolean updated = false;

            for (Notification stored : DemoStorage.getNotifications()) {

                // criterio di matching: stesso messaggio + stessa data
                if (stored.getMessage().equals(n.getMessage()) && stored.getDate().equals(n.getDate())) {

                    stored.setRisolto(n.isRisolto());
                    updated = true;
                    break;
                }
            }

            if (!updated) {
                // simulazione: UPDATE che non trova righe
                throw new DAOExceptionBrondi(
                        "Errore durante l'aggiornamento della notifica"
                );
            }

        } catch (DAOExceptionBrondi e) {
            throw e;

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

        try {
            for (Route r : DemoStorage.getRoutes()) {

                // simulazione ResultSet -> nuova riga
                Route copy = new RouteBuilder(r.getPartenza()).endStation(r.getArrivo())
                        .city(r.getCitta()).tipoViaggiatore(r.getTipoViaggiatore())
                        .nCambi(r.getnCambi()).listaCambi(r.getListaCambi())
                        .stazioneDiInterscambio(r.getStazInterscambio()).nStazioniAttraversate(r.getnStazAttraversate())
                        .tempoDiArrivo(r.getTempoDiArrivo()).nStazioniCitta(r.getnStazioniCitta())
                        .percTerrenoUtilizzato(r.getPercTerrenoUtilizzato()).utente(r.getUtente())
                        .build();

                resultList.add(copy);
            }

            return resultList;

        } catch (Exception e) {
            throw new DAOExceptionBrondi(
                    "Errore nel recupero delle statistiche PathInfo",
                    e
            );
        }
    }
    @Override
    public void salvataggio(Credentials cred, List<String> codiciBiglietti, String metodopayment, String city) throws CredentialsExceptionRemoli
    {
        final Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Modalità DEMO: il pagamento non viene salvato in persistenza.");
        try {

            String bigliettiConcatenati = String.join(",", codiciBiglietti);

            // simulazione INSERT SavePayment
            PaymentReg records = new PaymentReg(
                    cred.getCodiceFiscale(),
                    cred.getNome(),
                    cred.getCognome(),
                    cred.getDisabile(),
                    metodopayment,
                    bigliettiConcatenati,
                    city
            );

            DemoStorage.getPayments().add(records);

        } catch (Exception e) {
            throw new CredentialsExceptionRemoli(
                    "Nessun salvataggio del percorso nel livello di persistenza " + e.getMessage(),
                    "Errore in SalvaPagamentoDAO.java"
            );
        }
    }
    public List<Ticket> getTicketByCFDemo(String cf) throws DAOExceptionBrondi, PathNotFoundExceptionRemoli {

        try {
            List<Ticket> result = new ArrayList<>();

            for (PaymentReg p : DemoStorage.getPayments()) {

                if (p.getCodiceFiscale().equals(cf)) {

                    String[] codici = p.getBiglietti().split(",");

                    for (String codice : codici) {
                        Ticket t = new Ticket(
                                codice.trim(),
                                p.getCity(),
                                LocalDateTime.now(ZoneId.systemDefault())
                        );
                        result.add(t);
                    }
                }
            }

            if (result.isEmpty()) {
                throw new PathNotFoundExceptionRemoli(
                        "Nessun biglietto trovato",
                        cf,
                        404,
                        "TicketDAODemo.getTicketByCF"
                );
            }

            return result;

        } catch (PathNotFoundExceptionRemoli e) {
            throw e;

        } catch (Exception e) {
            throw new DAOExceptionBrondi(
                    "Errore DEMO durante il recupero dei biglietti",
                    e
            );
        }
    }
    @Override
    public List<Route> getData(String cf) throws DAOExceptionBrondi {

        try {
            List<Route> result = new ArrayList<>();

            for (Route r : DemoStorage.getRoutes()) {

                if (r.getUtente() != null && r.getUtente().equals(cf)) {
                    Route copy = new Route();
                    copy.setPartenza(r.getPartenza());
                    copy.setArrivo(r.getArrivo());
                    copy.setCitta(r.getCitta());
                    copy.setTipoViaggiatore(r.getTipoViaggiatore());
                    copy.setnCambi(r.getnCambi());
                    copy.setnStazAttraversate(r.getnStazAttraversate());
                    copy.setTempoDiArrivo(r.getTempoDiArrivo());
                    copy.setnStazioniCitta(r.getnStazioniCitta());
                    copy.setPercTerrenoUtilizzato(r.getPercTerrenoUtilizzato());
                    copy.setUtente(cf);
                    copy.setListaCambi(r.getListaCambi());
                    copy.setStazInterscambio(r.getStazInterscambio());

                    result.add(copy);
                }
            }

            // SEMPRE ritorna la lista (anche vuota)
            return result;

        } catch (Exception e) {
            throw new DAOExceptionBrondi(
                    "Errore durante il recupero dei percorsi in modalità DEMO: " + e.getMessage()
            );
        }
    }
}
