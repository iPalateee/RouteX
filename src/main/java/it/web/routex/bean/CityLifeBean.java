package it.web.routex.bean;

import it.web.routex.exception.InvalidCityDataExceptionBrondi;
import it.web.routex.exception.InvalidCityDataExceptionBrondi.Severity;

import java.util.ArrayList;
import java.util.List;

import static it.web.routex.utility.text.TextUtils.sanitize;

public class CityLifeBean {

    private List<String> percorsiConNomi = new ArrayList<>();
    private int numeroCambi;
    private List<String> linee = new ArrayList<>();
    private List<String> sequenzeDiCambiamento = new ArrayList<>();
    private List<String> sequenzeNodiCruciali = new ArrayList<>();
    private int numeroStazioni;

    public int getNumeroStazioniTotali() { return numeroStazioni; }
    public List<String> getLinee() { return linee; }
    public List<String> getPercorsiConNomi() { return percorsiConNomi; }
    public List<String> getSequenzeDiCambiamento() { return sequenzeDiCambiamento; }
    public List<String> getSequenzeNodiCruciali() { return sequenzeNodiCruciali; }
    public int getNumeroCambi() { return numeroCambi; }

    public void setNumeroStazioniTotali(int numeroStazioni) throws InvalidCityDataExceptionBrondi {
        if (numeroStazioni < 0) {
            throw new InvalidCityDataExceptionBrondi(
                    "Errore: Il numero totale di stazioni non può essere negativo.",
                    "numeroStazioniTotali < 0",
                    Severity.HIGH
            );
        }
        this.numeroStazioni = numeroStazioni;
    }

    public void setNumeroCambi(int numeroCambi) throws InvalidCityDataExceptionBrondi {
        if (numeroCambi < 0) {
            throw new InvalidCityDataExceptionBrondi(
                    "Errore: Il numero di cambi non può essere negativo.",
                    "numeroCambi < 0",
                    Severity.HIGH
            );
        }
        this.numeroCambi = numeroCambi;
    }

    public void setLinee(List<String> linee) throws InvalidCityDataExceptionBrondi {
        if (linee == null || linee.isEmpty()) {
            throw new InvalidCityDataExceptionBrondi(
                    "Errore di sistema: Dati delle linee mancanti o vuoti.",
                    "List<String> linee null o empty",
                    Severity.CRITICAL
            );
        }

        List<String> pulite = new ArrayList<>();
        for (String linea : linee) {
            String sanitized = sanitize(linea);
            if (sanitized == null || sanitized.isEmpty()) {
                throw new InvalidCityDataExceptionBrondi(
                        "Errore: Trovata una linea non valida o vuota.",
                        "Linea blank o null",
                        Severity.HIGH
                );
            }
            pulite.add(sanitized);
        }
        this.linee = pulite;
    }

    public void setPercorsiConNomi(List<String> percorsiConNomi) throws InvalidCityDataExceptionBrondi {

        if (percorsiConNomi == null || percorsiConNomi.isEmpty()) {
            throw new InvalidCityDataExceptionBrondi(
                    "Errore: Dati dei percorsi mancanti o vuoti.",
                    "List<String> percorsiConNomi null o empty",
                    Severity.CRITICAL
            );
        }

        List<String> pulite = new ArrayList<>();

        for (String percorso : percorsiConNomi) {
            String sanitized = sanitize(percorso);
            if (sanitized == null || sanitized.isEmpty()) {
                throw new InvalidCityDataExceptionBrondi(
                        "Errore: Trovato un percorso non valido o vuoto.",
                        "Elemento percorso blank o null",
                        Severity.HIGH
                );
            }
            pulite.add(sanitized);
        }
        this.percorsiConNomi = pulite;
    }

    public void setSequenzeDiCambiamento(List<String> sequenzeDiCambiamento) throws InvalidCityDataExceptionBrondi {

        if (sequenzeDiCambiamento == null) {
            throw new InvalidCityDataExceptionBrondi(
                    "Errore di sistema: Dati sui cambi nulli.",
                    "List<String> sequenzeDiCambiamento è null",
                    Severity.CRITICAL
            );
        }

        if (sequenzeDiCambiamento.isEmpty()) {
            this.sequenzeDiCambiamento = new ArrayList<>();
            return;
        }

        List<String> pulite = new ArrayList<>();

        for (String seq : sequenzeDiCambiamento) {
            String sanitized = sanitize(seq);
            if (sanitized == null || sanitized.isEmpty()) {
                throw new InvalidCityDataExceptionBrondi(
                        "Errore: Trovata una sequenza di cambiamento non valida.",
                        "Elemento sequenzaDiCambiamento blank o null",
                        Severity.HIGH
                );
            }
            pulite.add(sanitized);
        }
        this.sequenzeDiCambiamento = pulite;
    }

    public void setSequenzeNodiCruciali(List<String> sequenzeNodiCruciali) throws InvalidCityDataExceptionBrondi {
        if (sequenzeNodiCruciali == null) {
            throw new InvalidCityDataExceptionBrondi(
                    "Errore di sistema: Nodi cruciali nulli.",
                    "List<String> sequenzeNodiCruciali è null",
                    Severity.CRITICAL
            );
        }

        if (sequenzeNodiCruciali.isEmpty()) {
            this.sequenzeNodiCruciali = new ArrayList<>();
            return;
        }

        List<String> pulite = new ArrayList<>();
        for (String nodo : sequenzeNodiCruciali) {
            String sanitized = sanitize(nodo);
            if (sanitized == null || sanitized.isEmpty()) {
                throw new InvalidCityDataExceptionBrondi(
                        "Errore: Trovato un nodo cruciale non valido.",
                        "Elemento nodo cruciale blank o null",
                        Severity.HIGH
                );
            }
            pulite.add(sanitized);
        }
        this.sequenzeNodiCruciali = pulite;
    }
}