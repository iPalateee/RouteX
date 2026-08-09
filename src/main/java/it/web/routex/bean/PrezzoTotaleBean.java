package it.web.routex.bean;

import it.web.routex.exception.InvalidPriceCalculationExceptionBrondi;

public class PrezzoTotaleBean {
    private double prezzoTotale;

    public double getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(double prezzoTotale) throws InvalidPriceCalculationExceptionBrondi {

        if (prezzoTotale < 0) {
            throw new InvalidPriceCalculationExceptionBrondi(
                    "Impossibile procedere: il prezzo totale risulta negativo.",
                    "Prezzo negativo: " + prezzoTotale,
                    InvalidPriceCalculationExceptionBrondi.Severity.HIGH
            );
        }

        this.prezzoTotale = prezzoTotale;
    }
}
