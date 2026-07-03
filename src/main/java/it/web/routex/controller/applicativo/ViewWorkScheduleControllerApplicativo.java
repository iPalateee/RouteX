package it.web.routex.controller.applicativo;

import it.web.routex.bean.WorkerScheduleBean;
import it.web.routex.exception.BrondiException;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.model.WorkerSchedule;
import it.web.routex.utility.factory.LayerPersistenza;

public class ViewWorkScheduleControllerApplicativo {

    public WorkerScheduleBean getSchedule(String cf) throws BrondiException {

        try {

            it.web.routex.dao.LayerPersistenza layer = LayerPersistenza.createLayerPersistenza();
            WorkerSchedule model = layer.getWorkerSchedule(cf);

            if (model == null) {
                throw new BrondiException(
                        "Il Worker non possiede orari di lavoro assegnati da nessun admin.",
                        "BRONDI_002",
                        "Nessun record restituito dalla DAO"
                );
            }

            if (!model.isValid()) {
                throw new BrondiException(
                        "Orario di lavoro non valido",
                        "BRONDI_001",
                        "oraInizio >= oraFine"
                );
            }

            int durata = model.durataTurno();

            return new WorkerScheduleBean(
                    model.getOraInizio(),
                    model.getOraFine(),
                    model.getLuogoDiLavoro(),
                    durata
            );

        } catch (DAOExceptionBrondi e) {
            throw new BrondiException(
                    "Il Worker non possiede orari di lavoro assegnati.",
                    "BRONDI_002",
                    e.getMessage()
            );
        }
    }
}
