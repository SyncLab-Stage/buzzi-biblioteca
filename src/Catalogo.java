import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Catalogo {
    private List<Libro> libri = new ArrayList<>();

    public void aggiungiLibro(Libro libro) {
        libri.add(libro);
    }

    public List<Libro> cercaPerTitolo(String titolo) {
        List<Libro> perTitolo = libri.stream()
                .filter(n -> n.getTitolo().toUpperCase().contains(titolo.toUpperCase()))
                .toList();
        return perTitolo;
    }

    public List<Libro> cercaPerAutore(String cognome) {
        List<Libro> perAutore = libri.stream()
                .filter(n -> n.getAutore().getCognome().toUpperCase().contains(cognome.toUpperCase()))
                .toList();
        return perAutore;
    }

    public List<Libro> filtraPerAnno(int anno) {
        List<Libro> perAnno = libri.stream()
                .filter(n -> n.getAnno() == anno)
                .toList();
        return perAnno;
    }

    public List<Libro> ordinaLibri (List<Libro> libri, Comparator<Libro> criterio) {
        List<Libro> libriOrdinati = libri.stream()
                .sorted(criterio)
                .toList();
        return libriOrdinati;
    }
}
