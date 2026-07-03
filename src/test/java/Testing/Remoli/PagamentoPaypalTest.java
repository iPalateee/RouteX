package Testing.Remoli;

import it.web.routex.bean.PaymentResultBean;
import it.web.routex.controller.applicativo.PagamentoPaypal;
import it.web.routex.enumerator.Ruolo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import it.web.routex.utility.factory.ConnectionFactory;
import it.web.routex.utility.singleton.Credentials;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.CredentialsExceptionRemoli;

import java.util.ResourceBundle;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * ------------------------------------------------------------
 *  Test Class : <PagamentoPaypalTest>
 *  Author     : Simone Remoli
 *  Description: Test della classe PagamentoPaypal che gestisce i pagamenti tramite PayPal.
 * ------------------------------------------------------------
 */
@TestMethodOrder(MethodOrderer.MethodName.class)




class PagamentoPaypalTest {

    private static final ResourceBundle RB = ResourceBundle.getBundle("configurations/testpaths");

    static Stream<String> Permission(){
        return Stream.of(RB.getString("permission2"));
    }

    static Stream<String> permessiGiustiProvider() {
        return Stream.of(RB.getString("permessiGiusti"));
    }

    static Stream<String> noUserProvider() {
        return Stream.of(RB.getString("noUserPaypal"));
    }



    @ParameterizedTest
    @MethodSource("Permission")
    void A_TestingPermessiSbagliati(String strings){ //l'utente login_user non ha i permessi sul db per una stored procedure

        String[] parts = strings.split(":");
        String emailPaypal = parts[0];
        String codiceTransazione = parts[1];
        double totale = Double.parseDouble(parts[2]);
        int quantita = Integer.parseInt(parts[3]);
        String city = parts[4];

        PagamentoPaypal pagamento = new PagamentoPaypal(
                emailPaypal,
                codiceTransazione,
                null,
                totale,
                quantita,
                city
        );

        assertThrows(DAOExceptionBrondi.class, pagamento::run);
    }

    @ParameterizedTest
    @MethodSource("permessiGiustiProvider")
    void B_TestingPermessiGiusti(String s) throws Exception {

        String[] p = s.split(":");

        String nome = p[0];
        String cognome = p[1];
        String codiceFiscale = p[2];
        boolean disabile = Boolean.parseBoolean(p[3]);
        String email = p[4];
        String password = p[5];

        String emailPaypal = p[6];
        String codiceTransazione = p[7];
        double totale = Double.parseDouble(p[8]);
        int quantita = Integer.parseInt(p[9]);
        String city = p[10];

        ConnectionFactory.cambioDiRuolo(Ruolo.TRAVELER);

        Credentials cred = new Credentials();
        cred.setNome(nome);
        cred.setCognome(cognome);
        cred.setCodiceFiscale(codiceFiscale);
        cred.setDisabile(disabile);
        cred.setEmail(email);
        cred.setPassword(password);
        cred.setRuolo(Ruolo.TRAVELER);

        PagamentoPaypal pagamento = new PagamentoPaypal(
                emailPaypal,
                codiceTransazione,
                cred,
                totale,
                quantita,
                city
        );
        PaymentResultBean result = pagamento.run();

        assertEquals(4, result.getTicketCodes().size());

    }


    @ParameterizedTest
    @MethodSource("noUserProvider")
    void C_NoUserLoggedPayment(String s) throws Exception {

        ConnectionFactory.cambioDiRuolo(Ruolo.TRAVELER);

        String[] p = s.split(":");

        // SE il parametro è "null" , user = null
        Credentials cred = p[2].equals("null") ? null : new Credentials();

        PagamentoPaypal pagamento = new PagamentoPaypal(
                p[0],                        // email PayPal
                p[1],                        // codice transazione
                cred,                        // credenziali (null per errore)
                Double.parseDouble(p[4]),    // totale
                Integer.parseInt(p[5]),     // quantità
                p[6]                         // city
        );

        assertThrows(CredentialsExceptionRemoli.class, pagamento::run);
    }


}
