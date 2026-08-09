package it.web.routex.boundary.cli.controller.grafico;

import it.web.routex.bean.ApplicationModeBean;
import it.web.routex.boundary.cli.view.HomePrincipaleCLI;
import it.web.routex.boundary.cli.view.SelectModeCLI;
import it.web.routex.controller.applicativo.SelectModeControllerApplicativo;
import it.web.routex.exception.InvalidModeExceptionBrondi;

@SuppressWarnings("java:S106")
public final class SelectModeControllerGraficoCLI {

    private SelectModeControllerGraficoCLI() {
    }

    public static void doPost() throws InvalidModeExceptionBrondi {

        String mode = SelectModeCLI.getScelta();

        if(mode.isEmpty() || (!mode.equals("DEMO") && !mode.equals("FULL"))) {
            System.out.println("Modalità non valida. Per favore, scegli tra DEMO e FULL.");
            SelectModeCLI.choiceDemoFull();
            return;
        }

        ApplicationModeBean bean = new ApplicationModeBean();
        bean.setMode(mode);

        SelectModeControllerApplicativo a =  new SelectModeControllerApplicativo();
        a.selectMode(bean);

        HomePrincipaleCLI home = new HomePrincipaleCLI();
        home.home();
    }
}
