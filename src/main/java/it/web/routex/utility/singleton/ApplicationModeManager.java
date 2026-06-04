package it.web.routex.utility.singleton;

import it.web.routex.enumerator.ApplicationMode;

/**
 * Singleton che gestisce la modalità di esecuzione dell'applicazione
 * (DEMO o FULL).
 * Implementazione thread-safe tramite inner static holder.
 * Autore: Simone Remoli
 */
public class ApplicationModeManager {

    private ApplicationMode mode;

    private static class Container {
        public static final ApplicationModeManager instance = new ApplicationModeManager();
    }

    protected ApplicationModeManager() {
        //default ragionevole: FULL
        this.mode = ApplicationMode.FULL;
    }

    public static final ApplicationModeManager getSingletonInstance() {
        return Container.instance;
    }

    public ApplicationMode getMode() {
        return mode;
    }

    public void setMode(ApplicationMode mode) {
        this.mode = mode;
    }
}
