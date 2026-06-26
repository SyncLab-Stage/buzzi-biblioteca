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
            System.out.println();
            System.out.println("+------------------------------------------------+");
            System.out.println("|      Selezionare l'opzione da eseguire         |");
            System.out.println("+------------------------------------------------+");
            System.out.println("|    Cerca per Autore (cognome):          1      |");
            System.out.println("|    Cerca per Titolo:                    2      |");
            System.out.println("|    Filtra per Anno:                     3      |");
            System.out.println("|    Esci:                                0      |");
            System.out.println("|                                                |");
            System.out.println("+------------------------------------------------+");
            System.out.printf("Inserire opzione: ");

            opzione = scanner.nextInt();
            scanner.nextLine();

            switch (opzione) {
                case 0 -> System.out.println("A presto!");
                case 1 -> {
                    System.out.print("Inserire cognome autore: ");
                    String input = scanner.nextLine();
                    System.out.println(catalogo.cercaPerAutore(input));
                }
                case 2 -> {
                    System.out.print("Inserire titolo: ");
                    String input = scanner.nextLine();
                    System.out.println(catalogo.cercaPerTitolo(input));
                }
                case 3 -> {
                    System.out.println("Inserire anno di pubblicazione: ");
                    int input = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println(catalogo.filtraPerAnno(input));
                }
                default -> System.out.println("Opzione non valida.");
            }
        } while (opzione != 0);
    }
}