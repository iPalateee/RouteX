package it.web.routex.demo;
import it.web.routex.enumerator.Ruolo;
import it.web.routex.model.*;
import it.web.routex.utility.builder.UserBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Memoria centrale in RAM per la DEMO version.
 * Tutti i dati vengono persi alla chiusura dell'applicazione.
 */
public final class DemoStorage {

    private static List<Route> routes = new ArrayList<>();
    private static List<Notification> notifications = new ArrayList<>();
    private static Map<String, City> cities = new HashMap<>();
    private static List<User> users = new ArrayList<>();
    private static Map<String, Mastercard> mastercards = new HashMap<>();
    private static Map<String, Paypal> paypals = new HashMap<>();
    private static List<Fermate> fermate = new ArrayList<>();
    private static List<WorkerScheduleRecord> workerSchedules = new ArrayList<>();
    private static List<PaymentReg> payments = new ArrayList<>();

    static {


        cities.put("rome", new City("Rome", 1.50, 76));

        users.add(
                new UserBuilder("RSSMRA80A01H501U")
                        .nome("Mario")
                        .cognome("Rossi")
                        .dataDiNascita(java.sql.Date.valueOf("1980-01-01"))
                        .disabile(false)
                        .ruolo(Ruolo.TRAVELER)
                        .email("demo@routex.it")
                        .password("demo")
                        .build()
        );


        users.add(
                new UserBuilder("LUCMRCLN5H4JF7G8")
                        .nome("Lucia")
                        .cognome("Mercolano")
                        .dataDiNascita(java.sql.Date.valueOf("1980-01-01"))
                        .disabile(false)
                        .ruolo(Ruolo.ADMIN)
                        .email("lucia@gmail.com")
                        .password("a")
                        .build()
        );


        users.add(
                new UserBuilder("BNCLGI90B22F205X")
                        .nome("Luigi")
                        .cognome("Bianchi")
                        .dataDiNascita(java.sql.Date.valueOf("1990-02-22"))
                        .disabile(false)
                        .ruolo(Ruolo.WORKER)
                        .email("worker@routex.it")
                        .password("worker")
                        .build()
        );


        notifications.add(new Notification(
                "Manutenzione programmata linea A",
                new java.sql.Timestamp(System.currentTimeMillis()),
                false
        ));

        notifications.add(new Notification(
                "Ritardo temporaneo Linea A",
                new java.sql.Timestamp(System.currentTimeMillis()),
                false
        ));

        notifications.add(new Notification(
                "Ritardo temporaneo linea C",
                new java.sql.Timestamp(System.currentTimeMillis()),
                false
        ));

        workerSchedules.add(
                new WorkerScheduleRecord(
                        "BNCLGI90B22F205X",
                        8,
                        16,
                        "Deposito Roma Tiburtina"
                )
        );

        workerSchedules.add(
                new WorkerScheduleRecord(
                        "VRDLGI75C12F839K",
                        6,
                        14,
                        "Deposito Laurentina"
                )
        );




        Mastercard demoCard = new Mastercard();
        demoCard.setNumeroCarta("1111222233334444");
        demoCard.setDataScadenza("2027-03-01");
        demoCard.setCvv("123");

        mastercards.put(demoCard.getNumeroCarta(), demoCard);

        Paypal demoPaypal = new Paypal();
        demoPaypal.setEmail("demo@paypal.it");
        demoPaypal.setCodice("TXN-A1B2C3");

        paypals.put(demoPaypal.getEmail(), demoPaypal);

        fermate.add(new Fermate(0, "Rebibbia", false, "MB"));
        fermate.add(new Fermate(1, "Ponte Mammolo", true, "MB"));
        fermate.add(new Fermate(2, "Santa Maria del Soccorso", true, "MB"));
        fermate.add(new Fermate(3, "Pietralata", true, "MB"));
        fermate.add(new Fermate(4, "Monti Tiburtini", true, "MB"));
        fermate.add(new Fermate(5, "Quintiliani", true, "MB"));
        fermate.add(new Fermate(6, "Tiburtina FS", true, "MB"));
        fermate.add(new Fermate(7, "Bologna", true, "MB-MB1"));
        fermate.add(new Fermate(8, "Annibaliano", true, "MB1"));
        fermate.add(new Fermate(9, "Libia", true, "MB1"));
        fermate.add(new Fermate(10, "Conca d'Oro", true, "MB1"));
        fermate.add(new Fermate(11, "Jonio", true, "MB1"));
        fermate.add(new Fermate(12, "Policlinico", true, "MB"));
        fermate.add(new Fermate(13, "Castro Pretorio", true, "MB"));
        fermate.add(new Fermate(14, "Termini", true, "MA-MB"));
        fermate.add(new Fermate(15, "Cavour", false, "MB"));
        fermate.add(new Fermate(16, "Colosseo", true, "MB"));
        fermate.add(new Fermate(17, "Circo Massimo", true, "MB"));
        fermate.add(new Fermate(18, "Piramide", false, "MB"));
        fermate.add(new Fermate(19, "Garbatella", false, "MB"));
        fermate.add(new Fermate(20, "Basilica S. Paolo", true, "MB"));
        fermate.add(new Fermate(21, "Marconi", true, "MB"));
        fermate.add(new Fermate(22, "EUR Magliana", false, "MB"));
        fermate.add(new Fermate(23, "EUR Palasport", true, "MB"));
        fermate.add(new Fermate(24, "EUR Fermi", false, "MB"));
        fermate.add(new Fermate(25, "Laurentina", true, "MB"));
        fermate.add(new Fermate(26, "Anagnina", true, "MA"));
        fermate.add(new Fermate(27, "Cinecittà", true, "MA"));
        fermate.add(new Fermate(28, "Subaugusta", true, "MA"));
        fermate.add(new Fermate(29, "Giulio Agricola", true, "MA"));
        fermate.add(new Fermate(30, "Lucio Sestio", true, "MA"));
        fermate.add(new Fermate(31, "Numidio Quadrato", true, "MA"));
        fermate.add(new Fermate(32, "Porta Furba", false, "MA"));
        fermate.add(new Fermate(33, "Arco di Travertino", false, "MA"));
        fermate.add(new Fermate(34, "Colli Albani", true, "MA"));
        fermate.add(new Fermate(35, "Furio Camillo", true, "MA"));
        fermate.add(new Fermate(36, "Ponte Lungo", true, "MA"));
        fermate.add(new Fermate(37, "Re di Roma", true, "MA"));
        fermate.add(new Fermate(38, "San Giovanni", true, "MA-MC"));
        fermate.add(new Fermate(39, "Lodi", true, "MC"));
        fermate.add(new Fermate(40, "Pigneto", true, "MC"));
        fermate.add(new Fermate(41, "Malatesta", true, "MC"));
        fermate.add(new Fermate(42, "Teano", true, "MC"));
        fermate.add(new Fermate(43, "Gardenie", true, "MC"));
        fermate.add(new Fermate(44, "Mirti", true, "MC"));
        fermate.add(new Fermate(45, "Parco di Centocelle", true, "MC"));
        fermate.add(new Fermate(46, "Togliatti", true, "MC"));
        fermate.add(new Fermate(47, "Grano", true, "MC"));
        fermate.add(new Fermate(48, "Alessandrino", true, "MC"));
        fermate.add(new Fermate(49, "Torre Spaccata", true, "MC"));
        fermate.add(new Fermate(50, "Torre Maura", true, "MC"));
        fermate.add(new Fermate(51, "Tobagi", true, "MC"));
        fermate.add(new Fermate(52, "Giardinetti", true, "MC"));
        fermate.add(new Fermate(53, "Torrenova", true, "MC"));
        fermate.add(new Fermate(54, "Torre Angela", true, "MC"));
        fermate.add(new Fermate(55, "Torre Gaia", true, "MC"));
        fermate.add(new Fermate(56, "Grotte Celoni", true, "MC"));
        fermate.add(new Fermate(57, "Due Leoni - Fontana Candida", false, "MC"));
        fermate.add(new Fermate(58, "Borghesiana", false, "MC"));
        fermate.add(new Fermate(59, "Bolognetta", true, "MC"));
        fermate.add(new Fermate(60, "Finocchio", true, "MC"));
        fermate.add(new Fermate(61, "Graniti", true, "MC"));
        fermate.add(new Fermate(62, "Pantano", true, "MC"));
        fermate.add(new Fermate(63, "Manzoni", true, "MA"));
        fermate.add(new Fermate(64, "Vittorio Emanuele", false, "MA"));
        fermate.add(new Fermate(65, "Repubblica", true, "MA"));
        fermate.add(new Fermate(66, "Barberini", true, "MA"));
        fermate.add(new Fermate(67, "Spagna", true, "MA"));
        fermate.add(new Fermate(68, "Flaminio", true, "MA"));
        fermate.add(new Fermate(69, "Lepanto", true, "MA"));
        fermate.add(new Fermate(70, "Ottaviano", true, "MA"));
        fermate.add(new Fermate(71, "Cipro", true, "MA"));
        fermate.add(new Fermate(72, "Valle Aurelia", true, "MA"));
        fermate.add(new Fermate(73, "Baldo degli Ubaldi", true, "MA"));
        fermate.add(new Fermate(74, "Cornelia", true, "MA"));
        fermate.add(new Fermate(75, "Battistini", true, "MA"));


    }

    private DemoStorage() {}

    public static List<City> getCities() {
        //return cities;
        return new ArrayList<>(cities.values());
    }

    public static City getCityByName(String nomeCitta) {
        return cities.get(nomeCitta.toLowerCase());
    }

    public static List<Fermate> getFermate() {
        return fermate;
    }

    public static List<Mastercard> getMastercards() {
        return new ArrayList<>(mastercards.values());
    }

    public static Mastercard getMastercardByNumber(String numero){
        return mastercards.get(numero);
    }

    public static List<Notification> getNotifications() {
        return notifications;
    }

    public static List<PaymentReg> getPayments() {
        return payments;
    }

    public static List<Paypal> getPaypals() {
        return new ArrayList<>(paypals.values());
    }

    public static Paypal getPaypalByEmail(String email){
        return paypals.get(email);
    }

    public static List<Route> getRoutes() {
        return routes;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<WorkerScheduleRecord> getWorkerSchedules() {
        return workerSchedules;
    }
}
