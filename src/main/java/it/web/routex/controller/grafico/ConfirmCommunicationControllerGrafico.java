package it.web.routex.controller.grafico;

import it.web.routex.bean.MessageBean;
import it.web.routex.controller.applicativo.ConfirmCommunicationControllerApplicativo;
import it.web.routex.domain.LoggedHttpServlet;
import it.web.routex.exception.BrondiInvalidCommunicationInputException;
import it.web.routex.extractor.CommunicationInputExtractor;
import it.web.routex.record.CommunicationInput;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/confirmCommunication")
public class ConfirmCommunicationControllerGrafico extends LoggedHttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        try {

            CommunicationInput input = CommunicationInputExtractor.extract(request);

            MessageBean mess = new MessageBean();
            mess.setMessage(input.message());
            mess.setDate(input.date());

            ConfirmCommunicationControllerApplicativo service = new ConfirmCommunicationControllerApplicativo();

            service.communication(mess);

            request.setAttribute("successTitle", "Comunicazione inviata");
            request.setAttribute(
                    "successMessage",
                    "La comunicazione è stata correttamente inviata a tutti i lavoratori del sistema."
            );

            request.getRequestDispatcher("/successCommunication.jsp").forward(request, response);

        } catch (BrondiInvalidCommunicationInputException e) {

            request.setAttribute("errore", e.getMessage());
            try {
                request.getRequestDispatcher("/adminError.jsp").forward(request, response);
            }catch(Exception d){
                logger.error("Errore specifico nel forwarding verso adminError.jsp : {} ", e.getMessage());
            }

        } catch (Exception e) {

            request.setAttribute(
                    "errore",
                    "Errore durante l'invio della comunicazione."
            );
            try {
            request.getRequestDispatcher("/adminError.jsp").forward(request, response);
            }catch(Exception d){
                logger.error("Errore generico nel forwarding verso adminError.jsp : {} ", e.getMessage());

            }
        }
    }
}
