package it.web.routex.controller.applicativo;

import it.web.routex.bean.MessageBean;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.model.Notification;
import it.web.routex.utility.factory.LayerPersistenza;
import it.web.routex.utility.observer.Notifier;

public class ConfirmCommunicationControllerApplicativo {

    public void communication(MessageBean bean) throws DAOExceptionBrondi {

        Notification notification = new Notification(
                bean.getMessage(),
                bean.getDate(),
                false
        );

        it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
        layer.sendMessage(notification);
        Notifier.getInstanceSingleton().comunicazioneInviata();
    }
}
