package it.web.routex.boundary.cli.controller.grafico;
import it.web.routex.bean.MessageBean;
import it.web.routex.boundary.cli.LoggedCLI;
import it.web.routex.boundary.cli.extractor.CommunicationInputExtractorCLI;
import it.web.routex.boundary.cli.view.ComunicazioneInviataCLI;
import it.web.routex.boundary.cli.view.GenericErrorCLI;
import it.web.routex.controller.applicativo.ConfirmCommunicationControllerApplicativo;
import it.web.routex.exception.BrondiInvalidCommunicationInputException;
import it.web.routex.record.CommunicationInput;

public class ConfirmCommunicationControllerGraficoCLI extends LoggedCLI {

    public static void doPost()
    {
        try {
            CommunicationInput input = CommunicationInputExtractorCLI.extract();

            MessageBean mess = new MessageBean();
            mess.setMessage(input.message());
            mess.setDate(input.date());

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
