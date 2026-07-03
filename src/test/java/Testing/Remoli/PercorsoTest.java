package Testing.Remoli;

import it.web.routex.bean.InformazioniPercorsoBean;
import it.web.routex.bean.RouteBean;
import it.web.routex.controller.applicativo.AreaRiservata;
import it.web.routex.controller.applicativo.PathController;
import it.web.routex.enumerator.Ruolo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.utility.factory.ConnectionFactory;

import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * ------------------------------------------------------------
 *  Test Class : <PercorsoTest>
 *  Author     : Simone Remoli
 *  Description: Test di integrazione per la classe PathController.
 * ------------------------------------------------------------
 */
@TestMethodOrder(MethodOrderer.MethodName.class)

class PercorsoTest
{
    private static final ResourceBundle RB = ResourceBundle.getBundle("configurations/testpaths");

    static Stream<String> validPathsProvider() {
        return Stream.of(
                RB.getString("valid1"),
                RB.getString("valid2")
        );
        /*
        Stream.of("Ottaviano:Quintiliani:Rome", "Spagna:Anagnina:Rome")
         */
    }
    static Stream<String> pathCFProvider() {
        return Stream.of(RB.getString("pathsCF").split(":"));
    }
    static Stream<String> invalidPathsProvider() {
        return Stream.of(
                RB.getString("invalid1"),
                RB.getString("invalid2")
        );
    }
    @ParameterizedTest
    @MethodSource("validPathsProvider")
    void TestPath(String strings) throws Exception {

        String[] parts = strings.split(":");
        PathController path = new PathController();
        InformazioniPercorsoBean dto = path.run(parts[0], parts[1], parts[2]);
        int stazioniTotali = dto.getCityLife().getNumeroStazioniTotali();
        assertTrue(stazioniTotali > 0);

    }
    @ParameterizedTest
    @MethodSource("invalidPathsProvider")
    void TestExceptionPath(String strings){

        String[] parts = strings.split(":");
        PathController path = new PathController();
        assertThrows(DAOExceptionBrondi.class, () ->
        {
            path.run(parts[0], parts[1], parts[2]);
        });
    }
    @ParameterizedTest
    @MethodSource("pathCFProvider")
    void CheckListaPercorsi(String codiceFiscale) throws Exception {

        ConnectionFactory.cambioDiRuolo(Ruolo.TRAVELER);
        AreaRiservata reserved = new AreaRiservata();
        List<RouteBean> listaPercorsi = reserved.runPath(codiceFiscale);
        assertTrue(listaPercorsi.size() > 0);
    }
}
