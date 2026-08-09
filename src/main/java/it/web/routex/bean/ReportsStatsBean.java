package it.web.routex.bean;

import it.web.routex.exception.InvalidRouteInputExceptionRemoli;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static it.web.routex.utility.text.TextUtils.sanitize;

public class ReportsStatsBean {

    private int totalTrips;
    private double totalDistance;
    private double totalTime;
    private Set<String> utenti;
    private List<PathInfoBean> paths;

    public int getTotalTrips() { return totalTrips; }
    public double getTotalDistance() { return totalDistance; }
    public double getTotalTime() { return totalTime; }
    public Set<String> getUtenti() { return utenti; }
    public List<PathInfoBean> getPaths() { return paths; }

    public void setTotalTrips(int totalTrips) throws InvalidRouteInputExceptionRemoli {
        if (totalTrips < 0) {
            throw new InvalidRouteInputExceptionRemoli(
                    "totalTrips",
                    "Errore: I viaggi totali non possono essere un valore negativo."
            );
        }
        this.totalTrips = totalTrips;
    }

    public void setTotalDistance(double totalDistance) throws InvalidRouteInputExceptionRemoli {
        if (totalDistance < 0) {
            throw new InvalidRouteInputExceptionRemoli(
                    "totalDistance",
                    "Errore: La distanza totale non può essere un valore negativo."
            );
        }
        this.totalDistance = totalDistance;
    }

    public void setTotalTime(double totalTime) throws InvalidRouteInputExceptionRemoli {
        if (totalTime < 0) {
            throw new InvalidRouteInputExceptionRemoli(
                    "totalTime",
                    "Errore: Il tempo totale non può essere un valore negativo."
            );
        }
        this.totalTime = totalTime;
    }

    public void setUtenti(Set<String> rawUtenti) throws InvalidRouteInputExceptionRemoli {
        if (rawUtenti == null) {
            throw new InvalidRouteInputExceptionRemoli(
                    "utenti",
                    "Errore di sistema: L'insieme degli utenti (Set) non può essere nullo."
            );
        }

        Set<String> puliti = new HashSet<>();
        for (String utente : rawUtenti) {
            String sanitized = sanitize(utente);
            if (sanitized == null || sanitized.isEmpty()) {
                throw new InvalidRouteInputExceptionRemoli(
                        "utenti",
                        "Errore: Trovato un utente non valido (stringa vuota o nulla) nel Set."
                );
            }
            puliti.add(sanitized);
        }
        this.utenti = puliti;
    }

    public void setPaths(List<PathInfoBean> paths) throws InvalidRouteInputExceptionRemoli {
        if (paths == null) {
            throw new InvalidRouteInputExceptionRemoli(
                    "paths",
                    "Errore di sistema: La lista dei percorsi (PathInfoBean) non può essere nulla."
            );
        }
        this.paths = paths;
    }
}