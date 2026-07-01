import java.util.Comparator;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Autore autore1 = new Autore("George", "Orwell", "inglese");
        Autore autore2 = new Autore("Luigi", "Pirandello", "italiana");
        Autore autore3 = new Autore("Marcel", "Proust", "francese");

        Libro libro1 = new Libro("1984", autore1, 1949);
        Libro libro2 = new Libro("Uno, nessuno e centomila", autore2, 1926);
        Libro libro3 = new Libro("Dalla parte di Swann", autore3, 1913);
        Libro libro4 = new Libro("Animal Farm", autore1, 1945);

        Catalogo catalogo = new Catalogo();

        catalogo.aggiungiLibro(libro1);
        catalogo.aggiungiLibro(libro2);
        catalogo.aggiungiLibro(libro3);
        catalogo.aggiungiLibro(libro4);

        Scanner scanner = new Scanner(System.in);

        int opzione = -1; // valore di default, inizializzazione prima di assegnazione

        do {
            System.out.println();
            System.out.println("+------------------------------------------------+");
            System.out.println("| Biblioteca — Selezionare l'opzione da eseguire |");
            System.out.println("+------------------------------------------------+");
            System.out.println("|    Cerca per Autore (cognome):          1      |");
            System.out.println("|    Cerca per Titolo:                    2      |");
            System.out.println("|    Filtra per Anno:                     3      |");
            System.out.println("|    Esci:                                0      |");
            System.out.println("|                                                |");
            System.out.println("+------------------------------------------------+");
            System.out.printf("Inserire opzione: ");

            String inputOpzione = scanner.nextLine();
            try {
                opzione = Integer.parseInt(inputOpzione);
            } catch (NumberFormatException e) {
                System.out.println("Non valido. Riprova.");
            }

            switch (opzione) {
                case 0 -> {
                    System.out.println("A presto!");
                }

                case 1 -> {
                    System.out.print("Inserire cognome autore: ");
                    String input = scanner.nextLine();
                    List<Libro> risultati = catalogo.cercaPerAutore(input);
                    if (risultati.isEmpty()) {
                        System.out.println("Nessun risultato trovato.");
                    } else {
                        System.out.println("Ordina per: ");
                        System.out.println("1 — Ordina per titolo");
                        System.out.println("2 — Ordina per anno");
                        int opzioneCognome = 0;
                        String ordinaPerScelta = scanner.nextLine();
                        try {
                            opzioneCognome = Integer.parseInt(ordinaPerScelta);
                        } catch (NumberFormatException e) {
                            System.out.println("Non valido. Riprova.");
                        }
                        if (opzioneCognome == 1) {
                            List<Libro> risultatiOrdinati = catalogo.ordinaLibri(risultati, Comparator.comparing(Libro::getTitolo));
                            System.out.println("Libri trovati: " + risultatiOrdinati.size());
                            System.out.println(risultatiOrdinati);
                        } else if (opzioneCognome == 2) { // avevo paura di fare else perché dobbiamo gestire opzioni non valide
                            List<Libro> risultatiOrdinati = catalogo.ordinaLibri(risultati, Comparator.comparingInt(Libro::getAnno));
                            System.out.println("Libri trovati: " + risultatiOrdinati.size());
                            System.out.println(risultatiOrdinati);
                        }
                    }
                }

                case 2 -> {
                    System.out.print("Inserire titolo: ");
                    String input = scanner.nextLine();
                    List<Libro> risultati = catalogo.cercaPerTitolo(input);
                    if (risultati.isEmpty()) {
                        System.out.println("Nessun risultato trovato.");
                    } else {
                        System.out.println("Ordina per: ");
                        System.out.println("1 — Ordina per anno");
                        System.out.println("2 — Ordina per autore");
                        int opzioneTitolo = 0;
                        String ordinaPerScelta = scanner.nextLine();
                        try {
                            opzioneTitolo = Integer.parseInt(ordinaPerScelta);
                        } catch (NumberFormatException e) {
                            System.out.println("Non valido. Riprova");
                        }
                        if (opzioneTitolo == 1) {
                            List<Libro> risultatiOrdinati = catalogo.ordinaLibri(risultati, Comparator.comparingInt(Libro::getAnno));
                            System.out.println("Libri trovati: " + risultatiOrdinati.size());
                            System.out.println(risultatiOrdinati);
                        } else if (opzioneTitolo == 2) {
                            List<Libro> risultatiOrdinati = catalogo.ordinaLibri(risultati, Comparator.comparing(libro -> libro.getAutore().getCognome()));
                            System.out.println("Libri trovati: " + risultatiOrdinati.size());
                            System.out.println(risultatiOrdinati);
                        }
                    }
                }

                case 3 -> {
                    System.out.print("Inserire anno di pubblicazione: ");
                    String input = scanner.nextLine();
                    boolean inputValido = false;
                    int inputAnno = 0;
                    try {
                        inputAnno = Integer.parseInt(input);
                        inputValido = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Non valido. Riprova.");
                    }
                    if (!inputValido) {
                        break;
                    }

                    if (inputAnno < 1000) {
                        System.out.println("Non valido. Riprova.");
                        break;
                    }

                    List<Libro> risultati = catalogo.filtraPerAnno(inputAnno);
                    if (risultati.isEmpty()) {
                        System.out.println("Nessun risultato trovato.");
                    } else {
                        System.out.println("Ordina per: ");
                        System.out.println("1. Ordina per autore (cognome)");
                        System.out.println("2. Ordina per titolo");
                        int opzioneAnno = 0;
                        String ordinaPerScelta = scanner.nextLine();
                        try {
                            opzioneAnno = Integer.parseInt(ordinaPerScelta);
                        } catch (NumberFormatException e) {
                            System.out.println("Non valido. Riprova.");
                        }
                        if (opzioneAnno == 1) {
                            List<Libro> risultatiOrdinati = catalogo.ordinaLibri(risultati, Comparator.comparing(libro -> libro.getAutore().getCognome()));
                            System.out.println("Libri trovati: " + risultatiOrdinati.size());
                            System.out.print(risultatiOrdinati);
                        } else if (opzioneAnno == 2) {
                            List<Libro> risultatiOrdinati = catalogo.ordinaLibri(risultati, Comparator.comparing(Libro::getTitolo));
                            System.out.println("Libri trovati: " + risultatiOrdinati.size());
                            System.out.print(risultatiOrdinati);
                        }
                    }
                }

                default -> System.out.println("Opzione non valida.");
            }
        } while (opzione != 0);
    }
}