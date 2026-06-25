public class Libro {
    private String titolo;
    private Autore autore;
    private int anno;

    Libro(String titolo, Autore autore, int anno) {
        this.titolo = titolo;
        this.autore = autore;
        this.anno = anno;
    }

    public String getTitolo() { return titolo; }
    public Autore getAutore() { return autore; }
    public int getAnno() { return anno; }

    public String toString() {
        String libroCompleto = String.format("Titolo: %s; Autore %s; Anno: %d.", titolo, autore.getCognome(), anno);
        return libroCompleto;
    }
}
