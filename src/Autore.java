public class Autore {
    private String nome;
    private String cognome;
    private String nazionalita;

    Autore(String nome, String cognome, String nazionalita){
        this.nome = nome;
        this.cognome = cognome;
        this.nazionalita = nazionalita;
    }

    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getNazionalita() { return nazionalita; }
}
