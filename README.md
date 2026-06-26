# Gestione Biblioteca

> Simula il catalogo di una biblioteca. Avrai classi Libro, Autore, Catalogo. Dovrai aggiungere libri, cercarli per titolo o autore, filtrarli per anno. Metti in pratica OOP + Collections + Stream insieme

Esercizio 2 del piano formativo. Simulazione del catalogo di una biblioteca: aggiunti libri, ricerca e filtri.

Questo progetto ha diverse versioni, ed è stato costruito in modo incrementale, ogni versione che espande la precedente.

---

## v1

**Struttura**

```
src/
├── Autore.java     Dati anagrafici dell'autore (nome, cognome, nazionalità)
├── Libro.java      Rappresentazione di un libro, collegato a un oggetto Autore
├── Catalogo.java   Collezione di libri con metodi di ricerca e filtro
└── Main.java       Dati di test e chiamate dirette ai metodi del catalogo
```

**Note**

`Catalogo` usa `List<Libro>`, non `Map` né `Set`, in quanto un Set avrebbe eliminato i duplicati, che invece sono ammessi in un catalogo di libro, e Map avrebbe implicato un secondo valore accoppiato al Libro.

Stream API per operazioni di ricerca e filtro invece che cicli for; più leggibilità in operazioni di filtraggio e trasformazione su collezioni. Inoltre si prestano meglio a concatenazioni future.

`Libro` contiene un riferimento diretto a `Autore`, non una stringa. Usare un oggetto `Autore` permette che esso si porti con sé ogni dato associatogli + accesso diretto tramite getter. Principio imparato: modellare il dominio con oggetto invece che con dati piatti.

**Funzionamento**

`aggiungiLibro(Libro libro)` — aggiunge un libro alla collezione
`cercaPerTitolo(String titolo)` — restituisce i libri con quel titolo
`cercaPerAutore(String cognome)` — restituisce i libri di quell'autore; ricerca per cognome (dovevo scegliere in modo da usare una stringa, quindi cognome è la scelta arbitraria più sensata)
`filtraPerAnno(int anno)` — restituisce tutti i libri di quell'anno

**Limitazioni/da sistemare**

- La ricerca per titolo e per autore è case sensitive
- Non c'è gestione nel caso di ricerca che non produce risultati (stampa solo `[]`)
- Non c'è interfaccia utente: il Main chiama i metodi su dati hardcodati da me come test iniziale
- I risultati vengono stampati con `System.out.println`, che mostra la lista con le parentesi quadre di default — c'è da decidere se gestire la parte estetica (e quindi lavorare qui) o no.

---

## v2 — Menu interattivo

**Struttura**

```
src/
├── Autore.java
├── Libro.java
├── Catalogo.java
└── Main.java       Menu interattivo con Scanner per interrogare il catalogo
```

**Note**

Il menu usa `do-while` con `switch` expression (sintassi `->`). Il do while garantisce che il menu venga mostrato almeno una volta a prescindere prima di controllare la condizione di uscita. Lo switch non richiede `break`! migliore comodità e leggibilità

`opzione` deve essere dichiarata fuori dal loop. Se fosse dichiarata dentro il loop, non sarebbe stata accessibile nella condizione `while opzione != 0`. Principio: scope! Le variabili devono essere dichiarate nello scope in cui vengono usate.

`scanner.nextLine()` come linea di buffer dopo `scanner.nextInt()`. `nextInt()` legge il numero ma lascia il `\n` nel buffer, quindi la chiamata successiva a `nextLine()` avrebbe letto quella riga vuota invece dell'input reale.

**Interfaccia utente**

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

L'ASCII box è perché mi piaceva e mi son divertita a farla (però tutto con `println` — non formattato).

**Limitazioni/da sistemare**

- stessi della v1
- un input non numerico su qualsiasi campo numerico causa crash

---

## v3 — Gestione risultati

**Struttura**

```
src/
├── Autore.java
├── Libro.java
├── Catalogo.java
└── Main.java       Gestione esplicita dei risultati, messaggio di benvenuto e uscita
```

**Note**

La logica di conteggio e gestione risultati vuoti vive in `Main`, non in `Catalogo`. `Catalogo` sa già cosa ha trovato e restituisce una List; è Main a decidere come presentarlo. Principio: separare la logica di ricerca dalla logica di presentazione mantiene le responsabilità distinte: `Catalogo` non dovrebbe sapere niente di come i risultati vengono mostrati.

**Modifiche principali rispetto a v2**

- Messaggio di benvenuto (mostrato all'avvio, prima del primo ciclo del menu) e messaggio di uscita (selezionando 0 il programma stampa "A presto!" prima di terminare)
- Gestione risultati vuoti: se una ricerca non produce risultati viene stampato "Nessun risultato trovato." invece di una lista vuota `[]`
- Conteggio risultati — se una ricerca produce risultati viene stampato il numero prima della lista ("Libri trovati: N")

**Esempio output**

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

**Limitazioni/da sistemare** — le stesse

---

## v4 — validazione input + case sensitivity

**Struttura**

```
src/
├── Autore.java     
├── Libro.java      
├── Catalogo.java   Ricerca case-insensitive per titolo e autore
└── Main.java       Validazione input per opzione menu e anno di pubblicazione
```

**Note**

Lettura come stringa + `parseInt` invece di `nextInt()`. `nextInt()` crasha con `InputMismatchException` su input non numerico, ma wrapparlo in un `try/catch` non risolve il problema perché quando fallisce non consuma l'input dal buffer, ma rimane lì, e al ciclo successivo lo scanner lo rileggerebbe e crasherebbe di nuovo. `nextLine()` invece consuma sempre l'intera riga indipendentemente dal contenuto, spostando il punto di fallimento su `Integer.parseInt()`, che lavora su una stringa già letta e può essere wrappato normalmente. Questa riscrittura ha eliminato anche la necessità del `nextLine()` di pulizia del buffer introdotto in v2.

Soglia anno `>= 1000`. un'alternativa considerata era `> 0`, ma scartata perché accetterebbe anni come 500 o 3, privi di senso per un catalogo di libri stampati. `>= 1000` è una soglia seppur interamente arbitraria il più pragmatica possibile che copre tutta la stampa moderna senza richiedere casistiche particolari.

Flag booleano `inputValido` per l'anno, ma assente per l'opzione. Per l'anno, dopo il `try/catch` c'è ancora del codice da eseguire (la ricerca) — serve sapere se continuare o saltare. Per l'opzione, se `parseInt` fallisce `opzione` rimane a `-1`, il loop continua da solo e lo `switch` cade nel `default`. Il flag è necessario solo dove la logica successiva dipende dall'esito della conversione.

**Modifiche rispetto a v3**

**`Catalogo.java`**

- `cercaPerTitolo` e `cercaPerAutore` usano `equalsIgnoreCase` al posto di `equals`. "orwell", "Orwell" e "ORWELL" producono ora lo stesso risultato.

**`Main.java`**

- Validazione scelta opzione: input letto come stringa, convertito con `parseInt` in `try/catch`. Un input non numerico stampa "Non valido. Riprova." e ripresenta il menu.
- Validazione anno — stesso approccio. Input non numerico o sotto soglia stampa "Non valido. Riprova." e salta la ricerca con `break`.
