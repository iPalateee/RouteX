package Testing.Brondi;

import it.web.routex.bean.MessageBean;
import it.web.routex.controller.applicativo.UpdateNotificationsControllerApplicativo;
import it.web.routex.enumerator.Ruolo;
import it.web.routex.utility.factory.ConnectionFactory;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * ------------------------------------------------------------
 * Test Class : <UpdateNotificationsTest>
 * Author     : Lorenzo Brondi
 * Description: Test dell'aggiornamento stato notifiche.
 * ------------------------------------------------------------
 */
class UpdateNotificationsTest {

    private static final ResourceBundle RB =
            ResourceBundle.getBundle("configurations/testpaths");

    @Test
    void TestAggiornaNotifica() throws SQLException {

        // 1. Setup Ruolo
        ConnectionFactory.cambioDiRuolo(Ruolo.WORKER);


        // 2. Creazione Bean (come fa il controller grafico)
        MessageBean bean = new MessageBean(
                RB.getString("msgDaRisolvere"),
                Timestamp.valueOf(RB.getString("orarioMsg"))
                //new Timestamp(System.currentTimeMillis())
        );

        // 3. Controller applicativo
        UpdateNotificationsControllerApplicativo controller = new UpdateNotificationsControllerApplicativo();

        // 4. Verifica
        assertDoesNotThrow(() ->
                controller.aggiornaStatoNotifica(bean)
        );
    }
}
