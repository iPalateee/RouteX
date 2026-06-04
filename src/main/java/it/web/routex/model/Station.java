package it.web.routex.model;
/**
 * Model di dominio che rappresenta una Stazione nel sistema RouteX.
 * Incapsula l'identificativo univoco della stazione e fornisce
 * i metodi di logica interna ({@link #isValid()}, {@link #validate()})
 * per garantire la coerenza dei dati prima della persistenza.
 * @SimoneRemoli
 */
public class Station {

    private final int id;

    public Station(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isValid() {
        return id > 0;
    }

    public void validate() {
        if (!isValid()) {
            throw new IllegalStateException(
                    "Stazione non valida: id=" + id
            );
        }
    }
}
