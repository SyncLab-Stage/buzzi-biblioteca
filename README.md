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

---

## v3 — Gestione risultati

### Struttura

```
src/
├── Autore.java     Invariato rispetto a v2
├── Libro.java      Invariato rispetto a v2
├── Catalogo.java   Invariato rispetto a v2
└── Main.java       Aggiunta gestione esplicita dei risultati e messaggio di uscita
```

### Modifiche rispetto a v2

Tutte le modifiche sono in `Main.java`:

- Messaggio di uscita — selezionando 0 il programma stampa "A presto!" prima di terminare
- Gestione risultati vuoti — se una ricerca non produce risultati viene stampato "Nessun risultato trovato." invece di una lista vuota
- Conteggio risultati — se una ricerca produce risultati viene stampato il numero prima della lista ("Libri trovati: N")

Esempio output con risultati:
```
Inserire cognome autore: Orwell
Libri trovati: 2
[Titolo: 1984; Autore: Orwell; Anno: 1949., Titolo: Animal Farm; Autore: Orwell; Anno: 1945.]
```

Esempio output senza risultati:
```
Inserire cognome autore: Hemingway
Nessun risultato trovato.
```

### Note

- La ricerca per titolo e per autore è case-sensitive
- Il catalogo ammette duplicati (più copie dello stesso libro)
- I libri sono hardcoded in `Main.java` — nessuna persistenza

---

---

## v4 — Validazione input e ricerca case-insensitive

### Struttura

```
src/
├── Autore.java     Invariato rispetto a v3
├── Libro.java      Invariato rispetto a v3
├── Catalogo.java   Ricerca case-insensitive per titolo e autore
└── Main.java       Validazione input per opzione menu e anno di pubblicazione
```

### Modifiche rispetto a v3

**`Catalogo.java`**

- Ricerca case-insensitive — `cercaPerTitolo` e `cercaPerAutore` usano `equalsIgnoreCase` al posto di `equals`. "orwell", "Orwell" e "ORWELL" producono ora lo stesso risultato.

**`Main.java`**

- Validazione scelta opzione — l'input del menu non viene più letto con `nextInt()` ma con `nextLine()`, poi convertito con `Integer.parseInt()`. Un input non numerico (es. "abc") stampa "Non valido. Riprova." e ripresenta il menu senza crash.
- Validazione anno di pubblicazione — stesso approccio: lettura come stringa, conversione con `parseInt`, gestione del `NumberFormatException`. Se l'input non è valido la ricerca viene saltata con `break` e il menu si ripresenta.

### Note

- Il catalogo ammette duplicati (più copie dello stesso libro)
- I libri sono hardcoded in `Main.java` — nessuna persistenza
- `Libro.toString()` restituisce una rappresentazione nel formato `Titolo: ...; Autore: ...; Anno: ...`