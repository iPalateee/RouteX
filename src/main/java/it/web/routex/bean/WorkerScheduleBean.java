package it.web.routex.bean;

public class WorkerScheduleBean {

    private Integer oraInizio;
    private Integer oraFine;
    private String luogoDiLavoro;
    private int durataTurno;

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

    public void setOraInizio(Integer oraInizio) {
        this.oraInizio = oraInizio;
    }

    public Integer getOraFine() {
        return oraFine;
    }

    public void setOraFine(Integer oraFine) {
        this.oraFine = oraFine;
    }

    public String getLuogoDiLavoro() {
        return luogoDiLavoro;
    }
}