package it.web.routex.controller.grafico;

import it.web.routex.bean.ApplicationModeBean;
import it.web.routex.controller.applicativo.SelectModeControllerApplicativo;
import it.web.routex.domain.LoggedHttpServlet;
import it.web.routex.exception.InvalidModeExceptionBrondi;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/selectMode")
public class SelectModeControllerGrafico extends LoggedHttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            String modeParam = request.getParameter("mode");

            ApplicationModeBean bean = new ApplicationModeBean();
            bean.setMode(modeParam);

            SelectModeControllerApplicativo a = new SelectModeControllerApplicativo();
            a.selectMode(bean);

            response.sendRedirect("index.jsp");
        } catch (IOException e) {
            logger.error("Errore nel redirect a index.jsp");

            request.setAttribute("errore", e.getMessage());

            try {
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (Exception ex) {
                logger.error("Errore redirect ", ex);
            }

        } catch (InvalidModeExceptionBrondi e) {
            logger.error("Modalità non valida", e);

            request.setAttribute("errore", e.getUserMessage());

            try {
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (Exception ex) {
                logger.error("Errore forwarding ", ex);
            }
        }
    }
}
