package it.web.routex.controller.applicativo;

import it.web.routex.bean.ApplicationModeBean;
import it.web.routex.enumerator.ApplicationMode;
import it.web.routex.utility.singleton.ApplicationModeManager;

public class SelectModeControllerApplicativo {

    public void selectMode(ApplicationModeBean bean) {

        ApplicationMode mode = ApplicationMode.valueOf(bean.getMode());
        ApplicationModeManager.getSingletonInstance().setMode(mode);

    }
}
