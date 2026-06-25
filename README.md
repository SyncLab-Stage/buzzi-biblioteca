# Gestione Biblioteca

Esercizio 2 del piano formativo. Simulazione del catalogo di una biblioteca: aggiunta di libri, ricerca per autore o titolo, filtro per anno di pubblicazione.

## Struttura

```
src/
├── Autore.java     Dati anagrafici dell'autore (nome, cognome, nazionalità)
├── Libro.java      Rappresentazione di un libro, collegato a un oggetto Autore
├── Catalogo.java   Collezione di libri con metodi di ricerca e filtro
└── Main.java       Dati di test e chiamate ai metodi del catalogo
```

## Funzionamento

- `aggiungiLibro(Libro libro)` — aggiunge un libro alla collezione

- `cercaPerTitolo(String titolo)` — restituisce tutti i libri con quel titolo

- `cercaPerAutore(String cognome)` — restituisce tutti i libri di quell'autore

- `filtraPerAnno(int anno)` — restituisce tutti i libri pubblicati in quell'anno