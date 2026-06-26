import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Autore autore1 = new Autore("George", "Orwell", "inglese");
        Autore autore2 = new Autore("Luigi", "Pirandello", "italiana");
        Autore autore3 = new Autore("Marcel", "Proust", "francese");

        Libro libro1 = new Libro("1984", autore1, 1949);
        Libro libro2 = new Libro("Uno, nessuno e centomila", autore2, 1926);
        Libro libro3 = new Libro("Dalla parte di Swann", autore3, 1913);

        Catalogo catalogo = new Catalogo();
        catalogo.aggiungiLibro(libro1);
        catalogo.aggiungiLibro(libro2);
        catalogo.aggiungiLibro(libro3);

        Scanner scanner = new Scanner(System.in);
        int opzione = 0;

        do {
            System.out.println("Selezionare l'opzione da eseguire");
            System.out.printf("%-30s %d%n", "Cerca per Autore (cognome):", 1);
            System.out.printf("%-30s %d%n", "Cerca per Titolo:", 2);
            System.out.printf("%-30s %d%n", "Filtra per Anno:", 3);
            System.out.printf("%-30s %d%n", "Esci:", 0);
            System.out.print("Inserire opzione: ");
            opzione = scanner.nextInt();
        } while (opzione != 0);
    }
}