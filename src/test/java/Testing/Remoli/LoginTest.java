package Testing.Remoli;

import it.web.routex.bean.AutenticazioneBean;
import it.web.routex.bean.UtenteBeanGenerico;
import it.web.routex.controller.applicativo.LoginController;
import it.web.routex.exception.InvalidLoginInputExceptionBrondi;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.LoginNotFoundRemoli;

import java.util.ResourceBundle;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest
{
    private static final ResourceBundle RB = ResourceBundle.getBundle("configurations/testpaths");
    static Stream<String> invalidCredentialsProvider() {
        return Stream.of(RB.getString("invalidCredentials"));
    }
    static Stream<String> validCredentialsProvider() {
        return Stream.of
                (RB.getString("validCredentials1"),
                RB.getString("validCredentials2"));
    }
    @ParameterizedTest
    @MethodSource("invalidCredentialsProvider")
    void testLoginInvalidCredentials(String strings) throws InvalidLoginInputExceptionBrondi {

        String[] parts = strings.split(":");
        AutenticazioneBean credenziali = new AutenticazioneBean();
        credenziali.setEmail(parts[0]);
        credenziali.setPassword(parts[1]);
        LoginController loginController = new LoginController(credenziali);
        assertThrows(LoginNotFoundRemoli.class, () -> {
            loginController.autenticaUtente();
        });
    }
    @ParameterizedTest
    @MethodSource("validCredentialsProvider")
    void testLoginValidCredentials(String strings) throws DAOExceptionBrondi, LoginNotFoundRemoli, InvalidLoginInputExceptionBrondi {

        String[] parts = strings.split(":");
        AutenticazioneBean credenziali = new AutenticazioneBean();
        credenziali.setEmail(parts[0]);
        credenziali.setPassword(parts[1]);
        LoginController loginController = new LoginController(credenziali);
        UtenteBeanGenerico ut = loginController.autenticaUtente();
        assertNotNull(ut);
    }
}
