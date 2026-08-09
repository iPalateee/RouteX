package it.web.routex.bean;

public class WorkerScheduleBean {

    private final Integer oraInizio;
    private final Integer oraFine;
    private final String luogoDiLavoro;
    private final int durataTurno;

    public WorkerScheduleBean(Integer oraInizio, Integer oraFine, String luogoDiLavoro, int durataTurno) {
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.luogoDiLavoro = luogoDiLavoro;
        this.durataTurno = durataTurno;
    }

    public int getDurataTurno() {
        return durataTurno;
    }
    public Integer getOraInizio() {
        return oraInizio;
    }


    public Integer getOraFine() {
        return oraFine;
    }

    public String getLuogoDiLavoro() {
        return luogoDiLavoro;
    }
}