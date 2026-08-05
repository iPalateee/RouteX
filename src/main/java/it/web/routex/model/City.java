package it.web.routex.model;

import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;

import static it.web.routex.utility.text.TextUtils.sanitize;

/**
 * Model di dominio per l'entità Città.
 * Oltre a incapsulare i dati cittadini, contiene la logica di business
 * per la validazione e il calcolo dinamico dei prezzi dei biglietti.
 * @SimoneRemoli
 */

public class City {
    private String city;
    private double costoBiglietto;
    private long numeroStazioni;

    public City() { }

    public City(String city, double costo, long numeroStazioni) {
        this.city = city;
        this.costoBiglietto = costo;
        this.numeroStazioni = numeroStazioni;
    }

    public String getCity() { return city; }

    public void setCity(String rawCity) throws InvalidBuyTicketInputExceptionBrondi {

        if (rawCity == null)
            throw new InvalidBuyTicketInputExceptionBrondi(
                    "Il campo città non è stato inviato dal form.",
                    "Parametro 'Città' null.",
                    InvalidBuyTicketInputExceptionBrondi.Severity.LOW
            );

        String city = sanitize(rawCity);

        if (city.isBlank())
            throw new InvalidBuyTicketInputExceptionBrondi(
                    "Città mancante.",
                    "Città è blank dopo sanitizzazione.",
                    InvalidBuyTicketInputExceptionBrondi.Severity.MEDIUM
            );

        if (!city.matches("^[\\p{L}\\s\\-']{2,50}$")) {
            throw new InvalidBuyTicketInputExceptionBrondi(
                    "Inserisci una città valida.",
                    "Regex città non rispettata: " + city,
                    InvalidBuyTicketInputExceptionBrondi.Severity.MEDIUM
            );
        }

        this.city = city;
    }

    public double getCostoBiglietto() { return costoBiglietto; }

    public boolean isValid() {
        return city != null &&
                !city.isBlank() &&
                costoBiglietto > 0 &&
                numeroStazioni > 0;
    }

    public double calcolaPrezzoTotale(int quantita) {
        return this.costoBiglietto * quantita;
    }

    public long getNumeroStazioni() {
        return numeroStazioni;
    }

}
