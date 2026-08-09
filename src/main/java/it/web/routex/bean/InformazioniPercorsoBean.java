package it.web.routex.bean;

import it.web.routex.exception.InvalidRouteInputExceptionRemoli;

public class InformazioniPercorsoBean {

    private CityLifeBean cityLife;
    private int numeroStazioniUsate;
    private double minutaggio;
    private double percentualeStazioniUsate;

    public double getMinutaggio() {
        return minutaggio;
    }

    public double getPercentualeStazioniUsate() {
        return percentualeStazioniUsate;
    }

    public int getNumeroStazioniUsate() {
        return numeroStazioniUsate;
    }

    public CityLifeBean getCityLife() {
        return cityLife;
    }

    public void setMinutaggio(double minutaggio) throws InvalidRouteInputExceptionRemoli {
        if (minutaggio < 0) {
            throw new InvalidRouteInputExceptionRemoli(
                    "minutaggio",
                    "Errore: Il minutaggio del percorso non può essere un valore negativo."
            );
        }
        this.minutaggio = minutaggio;
    }

    public void setPercentualeStazioniUsate(double percentualeStazioniUsate) throws InvalidRouteInputExceptionRemoli {
        if (percentualeStazioniUsate < 0 || percentualeStazioniUsate > 100) {
            throw new InvalidRouteInputExceptionRemoli(
                    "percentualeStazioniUsate",
                    "Errore: La percentuale di stazioni usate deve essere compresa tra 0 e 100."
            );
        }
        this.percentualeStazioniUsate = percentualeStazioniUsate;
    }

    public void setNumeroStazioniUsate(int numeroStazioniUsate) throws InvalidRouteInputExceptionRemoli {
        if (numeroStazioniUsate < 0) {
            throw new InvalidRouteInputExceptionRemoli(
                    "numeroStazioniUsate",
                    "Errore: Il numero di stazioni usate non può essere un valore negativo."
            );
        }
        this.numeroStazioniUsate = numeroStazioniUsate;
    }

    public void setCityLife(CityLifeBean cityLife) throws InvalidRouteInputExceptionRemoli {
        if (cityLife == null) {
            throw new InvalidRouteInputExceptionRemoli(
                    "cityLife",
                    "Errore: I dettagli della città (CityLifeBean) non possono essere nulli."
            );
        }
        this.cityLife = cityLife;
    }
}