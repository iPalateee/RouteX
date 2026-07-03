package it.web.routex.utility.factory;

import it.web.routex.dao.LayerPersistenzaDemo;
import it.web.routex.dao.LayerPersistenzaFull;
import it.web.routex.enumerator.ApplicationMode;
import it.web.routex.utility.singleton.ApplicationModeManager;

public class LayerPersistenza {

    private static it.web.routex.dao.LayerPersistenza instance;

    private LayerPersistenza() {}

    public static it.web.routex.dao.LayerPersistenza createLayerPersistenza() {

        if (instance == null) {

            ApplicationMode mode = ApplicationModeManager.getSingletonInstance().getMode();

            switch (mode) {
                case DEMO:
                    instance = new LayerPersistenzaDemo();
                    break;
                case FULL:
                    instance = new LayerPersistenzaFull();
                    break;
                default:
                    throw new IllegalStateException("Modalità non supportata");
            }
        }
        return instance;
    }
}
