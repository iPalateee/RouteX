package it.web.routex.bean;

import java.util.ArrayList;
import java.util.List;

public class CityLifeBean
{
    private List<String> percorsiConNomi = new ArrayList<>();
    private int numeroCambi;
    private List<String> linee = new ArrayList<>();
    private List<String> sequenzeDiCambiamento = new ArrayList<>();
    private List<String> sequenzeNodiCruciali = new ArrayList<>();
    private int numeroStazioni;

    public void setNumeroStazioniTotali(int numeroStazioni) {
        this.numeroStazioni = numeroStazioni;
    }

    public int getNumeroStazioniTotali() {
        return numeroStazioni;
    }

    public List<String> getLinee() {
        return linee;
    }

    public List<String> getPercorsiConNomi() {
        return percorsiConNomi;
    }

    public List<String> getSequenzeDiCambiamento() {
        return sequenzeDiCambiamento;
    }

    public List<String> getSequenzeNodiCruciali() {
        return sequenzeNodiCruciali;
    }

    public int getNumeroCambi() {
        return numeroCambi;
    }

    public void setLinee(List<String> linee) {
        this.linee = linee;
    }

    public void setNumeroCambi(int numeroCambi) {
        this.numeroCambi = numeroCambi;
    }

    public void setPercorsiConNomi(List<String> percorsiConNomi) {
        this.percorsiConNomi = percorsiConNomi;
    }

    public void setSequenzeDiCambiamento(List<String> sequenzeDiCambiamento) {
        this.sequenzeDiCambiamento = sequenzeDiCambiamento;
    }

    public void setSequenzeNodiCruciali(List<String> sequenzeNodiCruciali) {
        this.sequenzeNodiCruciali = sequenzeNodiCruciali;
    }
}
