package it.web.routex.bean;

/**
 * Bean che rappresenta una fermata della metropolitana.
 * Contiene l'ID della fermata, il nome e la linea di appartenenza.
 */
public class FermataRecordBean {

    private String nome;
    private String linea;

    public FermataRecordBean() {}

    public FermataRecordBean(String nome, String linea) {
        this.nome = nome;
        this.linea = linea;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    @Override
    public String toString() {
        return "FermataBean{" +
                ", nome='" + nome + '\'' +
                ", linea='" + linea + '\'' +
                '}';
    }
}
