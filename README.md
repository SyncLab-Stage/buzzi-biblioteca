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

Ogni `Libro` contiene un riferimento a un oggetto `Autore`, invece che una semplice stringa, in modo da portare con sé tutte le informazioni dell'autore (nome, cognome, nazionalità) senza duplicarle, e di accedervi tramite i getter di `Autore` direttamente dal libro.

- `aggiungiLibro(Libro libro)` — aggiunge un libro alla collezione

- `cercaPerTitolo(String titolo)` — restituisce tutti i libri con quel titolo

- `cercaPerAutore(String cognome)` — restituisce tutti i libri di quell'autore

- `filtraPerAnno(int anno)` — restituisce tutti i libri pubblicati in quell'anno

Le operazioni di ricerca e filtro sono implementate con Stream API.

---

## Note

- La ricerca per titolo e per autore è case-sensitive
- Il catalogo ammette duplicati (più copie dello stesso libro)
- `Libro.toString()` restituisce una rappresentazione leggibile nel formato `Titolo: ...; Autore ...; Anno: ...`