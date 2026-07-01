# Gestione Biblioteca

Esercizio 2 del piano formativo. Simulazione del catalogo di una biblioteca: aggiunta di libri, ricerca per autore o titolo, filtro per anno di pubblicazione.

Il progetto è stato sviluppato in modo incrementale. Ogni versione aggiunge o corregge qualcosa rispetto alla precedente — questo documento riflette quelle decisioni nell'ordine in cui sono state prese.

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

### Decisioni di design

**`Catalogo` usa `List<Libro>`, non `Map` o `Set`.**
Una `Map` avrebbe richiesto una chiave univoca per ogni libro — non ovvia (titolo? ISBN? non ce l'abbiamo). Un `Set` avrebbe eliminato i duplicati, ma il catalogo deve ammettere più copie dello stesso libro. `List` è la scelta naturale.

**Le operazioni di ricerca e filtro sono implementate con Stream API.**
Alternativa: cicli `for` espliciti. Gli Stream sono più leggibili per operazioni di filtraggio e trasformazione su collezioni, e si prestano meglio a concatenazioni future.

### Note implementative

**`Libro` contiene un riferimento a `Autore`, non una stringa.**
Memorizzare il nome dell'autore come stringa avrebbe duplicato i dati e reso impossibile accedere alle altre informazioni dell'autore senza aggiungere altri campi. Un oggetto `Autore` porta tutto con sé e si accede via getter — è la conseguenza diretta di modellare il dominio con oggetti invece che con dati piatti.

### Funzionamento

- `aggiungiLibro(Libro libro)` — aggiunge un libro alla collezione
- `cercaPerTitolo(String titolo)` — restituisce tutti i libri con quel titolo
- `cercaPerAutore(String cognome)` — restituisce tutti i libri di quell'autore (per cognome)
- `filtraPerAnno(int anno)` — restituisce tutti i libri pubblicati in quell'anno

### Limitazioni note a fine v1

- La ricerca per titolo e per autore è case-sensitive: "orwell" non trova "Orwell"
- Non gestisce il caso in cui una ricerca non produca risultati: stampa una lista vuota `[]`
- Nessuna interfaccia utente: il `Main` chiama i metodi direttamente con dati hardcoded
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

### Decisioni di design

**Il menu usa `do-while` con `switch` expression (sintassi `->`).**
Il `do-while` garantisce che il menu venga mostrato almeno una volta prima di controllare la condizione di uscita. Lo `switch` con sintassi a freccia non richiede `break` esplicito tra i case — più leggibile e meno soggetto a errori di fall-through.

**Il menu è disegnato con `System.out.println` per ogni riga, non con `printf`.**
`printf` avrebbe richiesto gestione dell'allineamento per ogni riga. Con `println` e stringhe letterali il controllo del layout è diretto e senza sorprese.

### Note implementative

**`opzione` è dichiarata fuori dal loop.**
Se fosse stata dichiarata dentro il `do-while`, non sarebbe stata accessibile nella condizione `while (opzione != 0)`. Le variabili devono essere dichiarate nello scope in cui vengono usate.

**La lettura dell'anno usa `scanner.nextInt()` con `scanner.nextLine()` di pulizia.**
`nextInt()` legge il numero ma lascia il carattere `\n` nel buffer. La chiamata successiva a `nextLine()` avrebbe letto quella riga vuota invece dell'input reale. Questa soluzione verrà rivista in v4.

### Interfaccia utente

```
+------------------------------------------------+
| Biblioteca — Selezionare l'opzione da eseguire |
+------------------------------------------------+
|    Cerca per Autore (cognome):          1      |
|    Cerca per Titolo:                    2      |
|    Filtra per Anno:                     3      |
|    Esci:                                0      |
|                                                |
+------------------------------------------------+
```

Selezionata un'opzione, il programma richiede l'input corrispondente e stampa i risultati. Il menu si ripete finché l'utente non seleziona 0.

### Limitazioni note a fine v2

- La ricerca per titolo e per autore è case-sensitive
- Non gestisce il caso in cui una ricerca non produca risultati
- Un input non numerico su qualsiasi campo numerico causa un crash (`InputMismatchException`)

---

## v3 — Gestione risultati

### Struttura

```
src/
├── Autore.java     Invariato rispetto a v2
├── Libro.java      Invariato rispetto a v2
├── Catalogo.java   Invariato rispetto a v2
└── Main.java       Gestione esplicita dei risultati, messaggio di benvenuto e uscita
```

### Decisioni di design

**La logica di conteggio e gestione risultati vuoti vive in `Main`, non in `Catalogo`.**
`Catalogo` sa già cosa ha trovato — restituisce una `List`. `Main` decide come presentarlo. Separare la logica di ricerca dalla logica di presentazione mantiene le responsabilità distinte: `Catalogo` non dovrebbe sapere niente di come i risultati vengono mostrati.

### Modifiche rispetto a v2

- **Messaggio di benvenuto** — mostrato all'avvio, prima del primo ciclo del menu
- **Messaggio di uscita** — selezionando 0 il programma stampa "A presto!" prima di terminare
- **Gestione risultati vuoti** — se una ricerca non produce risultati viene stampato "Nessun risultato trovato." invece di una lista vuota `[]`
- **Conteggio risultati** — se una ricerca produce risultati viene stampato il numero prima della lista ("Libri trovati: N")

### Esempio output

Con risultati:
```
Inserire cognome autore: Orwell
Libri trovati: 2
[Titolo: 1984; Autore: Orwell; Anno: 1949., Titolo: Animal Farm; Autore: Orwell; Anno: 1945.]
```

Senza risultati:
```
Inserire cognome autore: Hemingway
Nessun risultato trovato.
```

### Limitazioni note a fine v3

- La ricerca per titolo e per autore è case-sensitive
- Un input non numerico su qualsiasi campo numerico causa ancora un crash

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

### Decisioni di design

**Lettura come stringa + `parseInt` invece di `nextInt()`.**
`nextInt()` crasha con `InputMismatchException` su input non numerico e non può essere protetto con `try/catch` in modo pulito perché lascia il buffer in stato inconsistente. Leggere tutto come stringa con `nextLine()` e convertire separatamente sposta il punto di fallimento su `Integer.parseInt()`, wrappabile normalmente. Questa riscrittura ha eliminato anche la necessità del `nextLine()` di pulizia del buffer introdotto in v2.

**Soglia anno `>= 1000`.**
Alternativa considerata: `> 0`. Scartata perché accetterebbe anni come 500 o 3, privi di senso per un catalogo di libri stampati. `>= 1000` è una soglia pragmatica che copre tutta la stampa moderna senza richiedere casistiche particolari.

### Note implementative

**Flag booleano `inputValido` per l'anno, assente per l'opzione.**
Per l'anno, dopo il `try/catch` c'è ancora del codice da eseguire (la ricerca) — serve sapere se continuare o saltare. Per l'opzione, se `parseInt` fallisce `opzione` rimane a `-1`, il loop continua da solo e lo `switch` cade nel `default`. Il flag è necessario solo dove la logica successiva dipende dall'esito della conversione.

### Modifiche rispetto a v3

**`Catalogo.java`**

- `cercaPerTitolo` e `cercaPerAutore` usano `equalsIgnoreCase` al posto di `equals`. "orwell", "Orwell" e "ORWELL" producono ora lo stesso risultato.

**`Main.java`**

- Validazione scelta opzione — input letto come stringa, convertito con `parseInt` in `try/catch`. Input non numerico stampa "Non valido. Riprova." e ripresenta il menu.
- Validazione anno — stesso approccio. Input non numerico o sotto soglia stampa "Non valido. Riprova." e salta la ricerca con `break`.

---

## v5 — Ricerca parziale e ordinamento risultati

### Struttura

```
src/
├── Autore.java     Invariato rispetto a v4
├── Libro.java      Invariato rispetto a v4
├── Catalogo.java   Ricerca parziale per titolo e autore; nuovo metodo ordinaLibri()
└── Main.java       Ordinamento risultati con scelta utente dopo ogni ricerca
```

### Decisioni di design

**Criteri di ordinamento contestuali — mai ridondanti.**
Dopo ogni ricerca, le opzioni di ordinamento escludono il campo già usato come criterio di ricerca: cercare per autore e poi ordinare per autore non avrebbe senso (tutti i risultati hanno lo stesso valore su quel campo). La tabella delle combinazioni:

| Ricerca | Ordina per |
|---|---|
| Per autore | Titolo, Anno |
| Per titolo | Anno, Cognome autore |
| Per anno | Titolo, Cognome autore |

**`ordinaLibri()` è un metodo generico in `Catalogo`, non tre metodi separati.**
Alternativa scartata: un metodo per criterio (`ordinaPerTitolo`, `ordinaPerAnno`, `ordinaPerAutore`). Avrebbero condiviso la stessa struttura con solo il `Comparator` diverso — duplicazione inutile. Un metodo generico che accetta `Comparator<Libro>` come parametro delega la costruzione del criterio al chiamante (`Main`), che è l'unico a sapere cosa ha scelto l'utente.

**Il `Comparator` viene costruito in `Main`, non in `Catalogo`.**
`Catalogo` non sa nulla dell'interfaccia utente — non sa cosa ha scelto l'utente e non dovrebbe saperlo. `Main` costruisce il comparator giusto in base alla scelta e lo passa a `ordinaLibri()`.

### Note implementative

**`Comparator.comparing()` non funziona con tipi primitivi.**
Per ordinare per anno (`int`), si usa `Comparator.comparingInt(Libro::getAnno)` invece di `Comparator.comparing()`. Quest'ultimo si aspetta un `Comparable`, che i tipi primitivi non sono.

**Per ordinare per cognome autore serve una lambda, non un method reference.**
`Comparator.comparing(Libro::getAutore)` non funziona perché `getAutore()` restituisce un oggetto `Autore`, non una stringa confrontabile direttamente. Serve navigare fino al cognome: `Comparator.comparing(libro -> libro.getAutore().getCognome())`.

### Modifiche rispetto a v4

**`Catalogo.java`**

- `cercaPerTitolo` e `cercaPerAutore` usano `toUpperCase().contains()` invece di `equalsIgnoreCase`. La ricerca ora trova qualsiasi sottostringa del campo, indipendentemente dal case. Questa modifica assorbe anche la case-insensitivity introdotta in v4.
- Aggiunto `ordinaLibri(List<Libro> libri, Comparator<Libro> criterio)` — restituisce la lista ordinata secondo il comparator ricevuto.

**`Main.java`**

- Dopo ogni ricerca con risultati, viene mostrato un sottomenu per scegliere il criterio di ordinamento. Input validato con lo stesso pattern `nextLine`/`parseInt`/`try-catch` usato nel menu principale. Se l'input non è valido o non corrisponde a nessuna opzione, i risultati non vengono stampati e il menu principale si ripresenta.