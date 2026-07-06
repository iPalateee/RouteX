package it.web.routex.boundary.cli.controller.grafico;
import it.web.routex.bean.MessageBean;
import it.web.routex.boundary.cli.LoggedCLI;
import it.web.routex.boundary.cli.view.ComunicazioneInviataCLI;
import it.web.routex.boundary.cli.view.GenericErrorCLI;
import it.web.routex.boundary.cli.view.SendCommunicationCLI;
import it.web.routex.controller.applicativo.ConfirmCommunicationControllerApplicativo;
import it.web.routex.exception.BrondiInvalidCommunicationInputException;

import java.sql.Timestamp;

public class ConfirmCommunicationControllerGraficoCLI extends LoggedCLI {

    public static void doPost()
    {
        try {
            String testo = SendCommunicationCLI.getMessage();

            MessageBean mess = new MessageBean();
            mess.setMessage(testo);
            mess.setDate(new Timestamp(System.currentTimeMillis()));

            ConfirmCommunicationControllerApplicativo service = new ConfirmCommunicationControllerApplicativo();

            service.communication(mess);

            ComunicazioneInviataCLI.invioComunicazioneConfirm(
                    "La comunicazione è stata correttamente inviata a tutti i lavoratori del sistema."
            );


        } catch (BrondiInvalidCommunicationInputException e) {

            GenericErrorCLI.mostraErrore(e.getMessage());


        } catch (Exception e) {

            GenericErrorCLI.mostraErrore("Errore durante l'invio della comunicazione.");

        }
    }
}
