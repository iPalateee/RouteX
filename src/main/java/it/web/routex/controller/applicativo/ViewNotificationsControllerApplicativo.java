package it.web.routex.controller.applicativo;
import it.web.routex.bean.MessageBean;
import it.web.routex.exception.BrondiException;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.exception.BrondiNoNotificationsWarningException;
import it.web.routex.model.Notification;
import it.web.routex.utility.factory.LayerPersistenza;
import java.util.ArrayList;
import java.util.List;

public class ViewNotificationsControllerApplicativo {

    public List<MessageBean> messages()
            throws BrondiException, BrondiNoNotificationsWarningException {

        List<MessageBean> result = new ArrayList<>();

        it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
        try {
            List<Notification> notifications = layer.getMessagesRAM();

            for (Notification n : notifications) {
                if (!n.isRisolto()) {
                    MessageBean bean = new MessageBean(n.getMessage(), n.getDate());
                    result.add(bean);
                }
            }

            if (result.isEmpty()) {
                throw new BrondiNoNotificationsWarningException(
                        "Nessuna notifica da visualizzare",
                        "Tutte le notifiche risultano risolte"
                );
            }
            return result;

        } catch (DAOExceptionBrondi e) {
            throw new BrondiException(
                    "Errore nel recupero delle notifiche",
                    "BRONDI_020",
                    "ViewNotificationsControllerApplicativo.messages"
            );
        }
    }
}
