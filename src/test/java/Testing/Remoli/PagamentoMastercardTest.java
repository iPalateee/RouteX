package Testing.Remoli;

import it.web.routex.bean.PaymentResultBean;
import it.web.routex.controller.applicativo.PagamentoMastercard;
import it.web.routex.enumerator.Ruolo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import it.web.routex.utility.factory.ConnectionFactory;
import it.web.routex.utility.singleton.Credentials;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.CredentialsExceptionBrondi;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * ------------------------------------------------------------
 *  Test Class : <PagamentoMastercardTest>
 *  Author     : Simone Remoli
 *  Description: Test della classe PagamentoMastercard
 * ------------------------------------------------------------
 */
@TestMethodOrder(MethodOrderer.MethodName.class)



class PagamentoMastercardTest
{
    private static final ResourceBundle RB = ResourceBundle.getBundle("configurations/testpaths");
    static Stream<String> Permission(){
        return Stream.of(RB.getString("permission"));
    }
    static Stream<String> permessiGiustiProvider(){
        return Stream.of(RB.getString("permessiOK"));
    }
    static Stream<String> noUserProvider() {
        return Stream.of(RB.getString("noUser"));
    }
    @ParameterizedTest
    @MethodSource("Permission")
    void TestingPermessiSbagliati(String strings){

        String[] parts = strings.split(":");
        double tot = Double.parseDouble(parts[3]);
        int quantitativo = Integer.parseInt(parts[4]);
        PagamentoMastercard pagamento = new PagamentoMastercard(parts[0], parts[1], parts[2], null, tot, quantitativo, parts[5]);

        assertThrows(DAOExceptionBrondi.class, () -> //l'utente login_user non ha i permessi sul db per una stored procedure
        {
            pagamento.run();
        });
    }
    @ParameterizedTest
    @MethodSource("permessiGiustiProvider")
    void TestingPermessiGiusti(String s) throws Exception {

        String[] p = s.split(":");

        ConnectionFactory.cambioDiRuolo(Ruolo.TRAVELER);

        Credentials cred = new Credentials();
        cred.setNome(p[0]);
        cred.setCognome(p[1]);
        cred.setCodiceFiscale(p[2]);
        cred.setDisabile(Boolean.parseBoolean(p[3]));
        cred.setEmail(p[4]);
        cred.setPassword(p[5]);
        cred.setRuolo(Ruolo.TRAVELER);

        PagamentoMastercard pagamento = new PagamentoMastercard(
                p[6], p[7], p[8], cred,
                Double.parseDouble(p[9]),
                Integer.parseInt(p[10]),
                p[11]
        );

        PaymentResultBean result = pagamento.run();

        assertEquals(2, result.getTicketCodes().size());
    }
    @ParameterizedTest
    @MethodSource("noUserProvider")
    void NoUserLoggedPayment(String s) throws Exception {

        ConnectionFactory.cambioDiRuolo(Ruolo.TRAVELER);

        String[] p = s.split(":");

        Credentials cred = p[3].equals("null") ? null : new Credentials();

        PagamentoMastercard pagamento = new PagamentoMastercard(
                p[0],       // numero carta
                p[1],       // scadenza
                p[2],       // cvv
                cred,       // CREDENZIALI NULLE
                Double.parseDouble(p[4]),
                Integer.parseInt(p[5]),
                p[6]
        );
        assertThrows(CredentialsExceptionBrondi.class, pagamento::run);
    }
}
