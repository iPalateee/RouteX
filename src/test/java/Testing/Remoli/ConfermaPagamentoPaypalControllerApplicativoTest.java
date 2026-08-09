package Testing.Remoli;

import it.web.routex.bean.PaymentResultBean;
import it.web.routex.controller.applicativo.ConfermaPagamentoPaypalControllerApplicativo;
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
 *  Test Class : <ConfermaPagamentoPaypalControllerApplicativoTest>
 *  Author     : Simone Remoli
 *  Description: Test della classe ConfermaPagamentoPaypalControllerApplicativo che gestisce i pagamenti tramite PayPal.
 * ------------------------------------------------------------
 */
@TestMethodOrder(MethodOrderer.MethodName.class)




class ConfermaPagamentoPaypalControllerApplicativoTest {

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
    void A_TestingPermessiSbagliati(String strings) throws InvalidCardInputExceptionBrondi, InvalidBuyTicketInputExceptionBrondi, InvalidPaymentInputExceptionBrondi { //l'utente login_user non ha i permessi sul db per una stored procedure

        String[] parts = strings.split(":");

        Credentials.getInstanceSingleton().clear();

        PaymentResultBean payment = new PaymentBuilder()
                .withEmailPaypal(parts[0])
                .withCodicePaypal(parts[1])
                .withTotale(parts[2])
                .withQuantity(parts[3])
                .withCity(parts[4])
                .withMetodoPagamento("paypal")
                .withPersistenza("JDBC")
                .build();

        ConfermaPagamentoPaypalControllerApplicativo pagamento = new ConfermaPagamentoPaypalControllerApplicativo(payment);
        assertThrows(DAOExceptionBrondi.class, pagamento::run);
    }

    @ParameterizedTest
    @MethodSource("permessiGiustiProvider")
    void B_TestingPermessiGiusti(String s) throws Exception {

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
                .withEmailPaypal(p[6])
                .withCodicePaypal(p[7])
                .withTotale(p[8])
                .withQuantity(p[9])
                .withCity(p[10])
                .withMetodoPagamento("paypal")
                .withPersistenza("JDBC")
                .build();

        ConfermaPagamentoPaypalControllerApplicativo pagamento = new ConfermaPagamentoPaypalControllerApplicativo(payment);
        PaymentResultBean result = pagamento.run();
        assertEquals(4, result.getTicketCodes().size());

    }


    @ParameterizedTest
    @MethodSource("noUserProvider")
    void C_NoUserLoggedPayment(String s) throws Exception {

        ConnectionFactory.cambioDiRuolo(Ruolo.TRAVELER);
        String[] p = s.split(":");

        // SE il parametro è "null" , svuoto il Singleton
        Credentials cred = Credentials.getInstanceSingleton();
        if (p[2].equals("null")) {
            cred.clear();
        }

        PaymentResultBean payment = new PaymentBuilder()
                .withEmailPaypal(p[0])
                .withCodicePaypal(p[1])
                .withTotale(p[4])
                .withQuantity(p[5])
                .withCity(p[6])
                .withMetodoPagamento("paypal")
                .withPersistenza("JDBC")
                .build();

        ConfermaPagamentoPaypalControllerApplicativo pagamento = new ConfermaPagamentoPaypalControllerApplicativo(payment);

        assertThrows(CredentialsExceptionBrondi.class, pagamento::run);
    }


}
