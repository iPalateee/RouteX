package Testing.Remoli;

import it.web.routex.bean.CityBean;
import it.web.routex.bean.PrezzoTotaleBean;
import it.web.routex.controller.applicativo.CityController;
import it.web.routex.enumerator.Ruolo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import it.web.routex.utility.factory.ConnectionFactory;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.InvalidCityDataExceptionBrondi;

import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ------------------------------------------------------------
 *  Test Class : <CityTest>
 *  Author     : Simone Remoli
 *  Description: Test della classe CityController, in particolare
 *               del metodo ottieni_prezzo_totale e getAllCities.
 * ------------------------------------------------------------
 */

class CityTest {

    private static final ResourceBundle RB = ResourceBundle.getBundle("configurations/testpaths");
    static Stream<String> prezzoTotaleProvider() {
        return Stream.of(
                RB.getString("prezzo1"),
                RB.getString("prezzo3")
        );
    }
    @ParameterizedTest
    @MethodSource("prezzoTotaleProvider")
    void TestPrezzoTotale(String s) throws Exception {

        ConnectionFactory.cambioDiRuolo(Ruolo.TRAVELER);

        String[] parts = s.split(":");
        String city = parts[0];
        String quantita = parts[1];
        double expected = Double.parseDouble(parts[2]);

        CityController cityController = new CityController();
        PrezzoTotaleBean prezzo = cityController.ottieniPrezzoTotale(city, Integer.parseInt(quantita));

        assertEquals(expected, prezzo.getPrezzoTotale());
    }
    @Test
    void OttieniCity() throws InvalidCityDataExceptionBrondi, SQLException, DAOExceptionBrondi {

        ConnectionFactory.cambioDiRuolo(Ruolo.TRAVELER);
        CityController cityController = new CityController();
        List<CityBean> city = cityController.getAllCities();
        assertTrue(city.size() > 0);

    }
}
