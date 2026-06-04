package it.web.routex.model;
/**
 * Model di dominio per l'entità Città.
 * Oltre a incapsulare i dati cittadini, contiene la logica di business
 * per la validazione e il calcolo dinamico dei prezzi dei biglietti.
 * @SimoneRemoli
 */

public class City {
    private String name;
    private double costoBiglietto;
    private long numeroStazioni;

    public City() {}

    public City(String name, double costo, long numeroStazioni) {
        this.name = name;
        this.costoBiglietto = costo;
        this.numeroStazioni = numeroStazioni;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getCostoBiglietto() { return costoBiglietto; }

    public boolean isValid() {
        return name != null &&
                !name.isBlank() &&
                costoBiglietto > 0 &&
                numeroStazioni > 0;
    }

    public double calcolaPrezzoTotale(int quantita) {
        return costoBiglietto * quantita;
    }

    public long getNumeroStazioni() {
        return numeroStazioni;
    }

}
