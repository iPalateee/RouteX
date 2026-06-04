package it.web.routex.model;

public class WorkerSchedule {

    private final int oraInizio;
    private final int oraFine;
    private final String luogoDiLavoro;

    public WorkerSchedule(int oraInizio, int oraFine, String luogoDiLavoro) {
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.luogoDiLavoro = luogoDiLavoro;
        if (oraInizio >= oraFine) {
            throw new IllegalArgumentException("oraInizio deve essere minore di oraFine nel formato a 24H");
        }
    }

    public boolean isValid() {
        return oraInizio >= 0 && oraFine <= 24 && oraInizio < oraFine;
    }

    public int durataTurno() {
        return oraFine - oraInizio;
    }

    //getter solo se servono alla view
    public int getOraInizio() { return oraInizio; }
    public int getOraFine() { return oraFine; }
    public String getLuogoDiLavoro() { return luogoDiLavoro; }
}
