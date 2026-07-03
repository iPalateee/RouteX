package it.web.routex.controller.applicativo;

import it.web.routex.bean.MessageBean;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.model.Notification;
import it.web.routex.utility.factory.LayerPersistenza;
import it.web.routex.utility.observer.Notifier;

import java.util.List;

public class UpdateNotificationsControllerApplicativo {

    public void aggiornaStatoNotifica(MessageBean bean) throws DAOExceptionBrondi {

        it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();

        List<Notification> cached = layer.getMessagesRAM();

        for (Notification n : cached) {
            if (n.getMessage().equals(bean.getMessage())
                    && n.getDate().equals(bean.getDate())) {

                n.setRisolto(true);
                layer.solvedNotification(n);
                Notifier.getInstanceSingleton().comunicazioneRisolta();
                return;
            }
        }
        throw new DAOExceptionBrondi("Notifica non trovata in cache");
    }
}
