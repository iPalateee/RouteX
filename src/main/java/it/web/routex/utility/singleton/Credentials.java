package it.web.routex.utility.singleton;
import it.web.routex.enumerator.Ruolo;
import java.sql.Date;

public class Credentials {

    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String password;
    private String email;
    private Date dataDiNascita;
    private boolean disabile;
    private Ruolo ruolo;

    private static class LazyCointainer{
        public static final ThreadLocal<Credentials> sigletonInstance = ThreadLocal.withInitial(Credentials::new);
    }

    public static Credentials getInstanceSingleton() {
        return LazyCointainer.sigletonInstance.get();
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getDataDiNascita() { return dataDiNascita; }
    public void setDataDiNascita(Date dataDiNascita) { this.dataDiNascita = dataDiNascita; }

    public boolean getDisabile() { return disabile; }
    public void setDisabile(boolean disabile) { this.disabile = disabile; }

    public Ruolo getRuolo() { return ruolo; }
    public void setRuolo(Ruolo ruolo) { this.ruolo = ruolo; }

    public void clear() {
        this.nome = null;
        this.cognome = null;
        this.codiceFiscale = null;
        this.password = null;
        this.email = null;
        this.dataDiNascita = null;
        this.disabile = false;
        this.ruolo = null;
    }
}