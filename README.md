# Gestione Biblioteca

Esercizio 2 del piano formativo. Simulazione del catalogo di una biblioteca: aggiunta di libri, ricerca per autore o titolo, filtro per anno di pubblicazione.

---

## v1 — Modellazione e ricerca

### Struttura

```
src/
├── Autore.java     Dati anagrafici dell'autore (nome, cognome, nazionalità)
├── Libro.java      Rappresentazione di un libro, collegato a un oggetto Autore
├── Catalogo.java   Collezione di libri con metodi di ricerca e filtro
└── Main.java       Dati di test e chiamate dirette ai metodi del catalogo
```

### Funzionamento

Ogni `Libro` contiene un riferimento a un oggetto `Autore`, invece che una semplice stringa, in modo da portare con sé tutte le informazioni dell'autore (nome, cognome, nazionalità) senza duplicarle, e di accedervi tramite i getter di `Autore` direttamente dal libro.

- `aggiungiLibro(Libro libro)` — aggiunge un libro alla collezione
- `cercaPerTitolo(String titolo)` — restituisce tutti i libri con quel titolo
- `cercaPerAutore(String cognome)` — restituisce tutti i libri di quell'autore
- `filtraPerAnno(int anno)` — restituisce tutti i libri pubblicati in quell'anno

Le operazioni di ricerca e filtro sono implementate con Stream API.

Esempio output:
```
[Titolo: Uno, nessuno e centomila; Autore Pirandello; Anno: 1926.]
[Titolo: 1984; Autore Orwell; Anno: 1949.]
```

### Note

- La ricerca per titolo e per autore è case-sensitive
- Il catalogo ammette duplicati (più copie dello stesso libro)
- `Libro.toString()` restituisce una rappresentazione leggibile nel formato `Titolo: ...; Autore ...; Anno: ...`
- Non gestisce il caso in cui una ricerca non produca risultati
- I risultati vengono stampati con `System.out.println`, che mostra la lista con le parentesi quadre di default

---

## v2 — Menu interattivo

### Struttura

```
src/
├── Autore.java     Invariato rispetto a v1
├── Libro.java      Invariato rispetto a v1
├── Catalogo.java   Invariato rispetto a v1
└── Main.java       Menu interattivo con Scanner per interrogare il catalogo
```

### Interfaccia utente

Il programma espone un menu interattivo a terminale:

```
+------------------------------------------------+
|      Selezionare l'opzione da eseguire         |
+------------------------------------------------+
|    Cerca per Autore (cognome):          1      |
|    Cerca per Titolo:                    2      |
|    Filtra per Anno:                     3      |
|    Esci:                                0      |
|                                                |
+------------------------------------------------+
```

Selezionata un'opzione, il programma richiede l'input corrispondente (cognome, titolo, o anno) e stampa i risultati. Il menu si ripete finché l'utente non seleziona 0.

### Note

- La ricerca per titolo e per autore è case-sensitive
- Il catalogo ammette duplicati (più copie dello stesso libro)
- I libri sono hardcoded in `Main.java` — nessuna persistenza
- `Libro.toString()` restituisce una rappresentazione nel formato `Titolo: ...; Autore: ...; Anno: ...`