package it.web.routex.controller.applicativo;

import it.web.routex.bean.MessageBean;
import it.web.routex.dao.LayerPersistenza;
import it.web.routex.exception.DAOExceptionRemoli;
import it.web.routex.model.Notification;
import it.web.routex.utility.factory.FactoryLayerPersistenza;
import it.web.routex.utility.observer.Notifier;

public class ConfirmCommunicationControllerApplicativo {

    public void communication(MessageBean bean) throws DAOExceptionRemoli {

        Notification notification = new Notification(
                bean.getMessage(),
                bean.getDate(),
                false
        );

        LayerPersistenza layer = FactoryLayerPersistenza.createLayerPersistenza();
        layer.sendMessage(notification);
        Notifier.getInstanceSingleton().comunicazioneInviata();
    }
}
