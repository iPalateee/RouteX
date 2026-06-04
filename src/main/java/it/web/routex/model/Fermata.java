package it.web.routex.model;

/**
 * Model di dominio che rappresenta una fermata.
 * Contiene dati + logica di dominio.
 */
public class Fermata {

    private final String nome;
    private final String linea;

    public Fermata(String nome, String linea) {
        this.nome = nome;
        this.linea = linea;
    }

    public String getNome() {
        return nome;
    }

    public String getLinea() {
        return linea;
    }

    public boolean isLineaComposta() {
        return linea != null && linea.contains("-");
    }

    @Override
    public String toString() {
        return nome + " (" + linea + ")";
    }
}
