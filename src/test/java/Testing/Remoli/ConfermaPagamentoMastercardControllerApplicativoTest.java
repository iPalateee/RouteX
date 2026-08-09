package Testing.Remoli;

import it.web.routex.bean.PaymentResultBean;
import it.web.routex.controller.applicativo.ConfermaPagamentoMastercardControllerApplicativo;
import it.web.routex.enumerator.Ruolo;
import it.web.routex.exception.*;
import it.web.routex.utility.builder.PaymentBuilder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import it.web.routex.utility.factory.ConnectionFactory;
import it.web.routex.utility.singleton.Credentials;

import java.util.ResourceBundle;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * ------------------------------------------------------------
 *  Test Class : <ConfermaPagamentoMastercardControllerApplicativoTest>
 *  Author     : Simone Remoli
 *  Description: Test della classe ConfermaPagamentoMastercardControllerApplicativo
 * ------------------------------------------------------------
 */
@TestMethodOrder(MethodOrderer.MethodName.class)



class ConfermaPagamentoMastercardControllerApplicativoTest
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
    void TestingPermessiSbagliati(String strings) throws InvalidCardInputExceptionBrondi, InvalidBuyTicketInputExceptionBrondi, InvalidPaymentInputExceptionBrondi {

        String[] parts = strings.split(":");

        Credentials.getInstanceSingleton().clear();

        PaymentResultBean payment = new PaymentBuilder()
                .withNumeroCarta(parts[0])
                .withScadenzaCarta(parts[1])
                .withCvvCarta(parts[2])
                .withTotale(parts[3])
                .withQuantity(parts[4])
                .withCity(parts[5])
                .withMetodoPagamento("mastercard")
                .withPersistenza("JDBC")
                .build();

        ConfermaPagamentoMastercardControllerApplicativo pagamento = new ConfermaPagamentoMastercardControllerApplicativo(payment);

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

        Credentials cred = Credentials.getInstanceSingleton();
        cred.setNome(p[0]);
        cred.setCognome(p[1]);
        cred.setCodiceFiscale(p[2]);
        cred.setDisabile(Boolean.parseBoolean(p[3]));
        cred.setEmail(p[4]);
        cred.setPassword(p[5]);
        cred.setRuolo(Ruolo.TRAVELER);

        PaymentResultBean payment = new PaymentBuilder()
                .withNumeroCarta(p[6])
                .withScadenzaCarta(p[7])
                .withCvvCarta(p[8])
                .withTotale(p[9])
                .withQuantity(p[10])
                .withCity(p[11])
                .withMetodoPagamento("mastercard")
                .withPersistenza("JDBC")
                .build();

        ConfermaPagamentoMastercardControllerApplicativo pagamento = new ConfermaPagamentoMastercardControllerApplicativo(payment);
        PaymentResultBean result = pagamento.run();
        assertEquals(2, result.getTicketCodes().size());
    }
    @ParameterizedTest
    @MethodSource("noUserProvider")
    void NoUserLoggedPayment(String s) throws Exception {

        ConnectionFactory.cambioDiRuolo(Ruolo.TRAVELER);
        String[] p = s.split(":");

        Credentials cred = Credentials.getInstanceSingleton();
        if (p[3].equals("null")) {
            cred.clear();
        }

        PaymentResultBean payment = new PaymentBuilder()
                .withNumeroCarta(p[0])
                .withScadenzaCarta(p[1])
                .withCvvCarta(p[2])
                .withTotale(p[4])
                .withQuantity(p[5])
                .withCity(p[6])
                .withMetodoPagamento("mastercard")
                .withPersistenza("JDBC")
                .build();

        ConfermaPagamentoMastercardControllerApplicativo pagamento = new ConfermaPagamentoMastercardControllerApplicativo(payment);
        assertThrows(CredentialsExceptionBrondi.class, pagamento::run);
    }
}
