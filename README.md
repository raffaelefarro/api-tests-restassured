# Test delle API con REST Assured e JUnit 5

Test automatici su una API vera, scritti in Java. Uso [JSONPlaceholder](https://jsonplaceholder.typicode.com/), un servizio pubblico fatto apposta per fare prove: tutto quello che si "salva" o si "cancella" non cambia niente davvero, quindi i test si possono rilanciare quante volte si vuole.

Sono 16 test su post, commenti e utenti.

## Cosa controllo

- **Codici di risposta**: 200 per le letture, 201 per la creazione, 404 per ciò che non esiste
- **Contenuto**: campi presenti, tipi giusti, email che contengono la chiocciola, indirizzo e azienda dell'utente
- **Filtri**: `?userId=1` deve restituire solo i post di quell'utente
- **Tempi**: le risposte principali devono arrivare entro 3 secondi
- **Scrittura**: POST, PUT e DELETE
- **Più casi con lo stesso test**: gli id degli utenti che esistono e quelli che non esistono (0, 11, 9999)

## Come è organizzato

```
src/test/java/api
├── BaseApiTest.java   indirizzo base, intestazioni e controllo dei tempi, in un posto solo
├── PostsTest.java     post e commenti
└── UsersTest.java     utenti
```

Le impostazioni comuni stanno in `BaseApiTest` e le specifiche (`RequestSpecification`, `ResponseSpecification`) sono riusate nei test, così i test restano corti e si leggono come una frase.

## Come lanciarli

Servono Java 17 e Maven.

```bash
mvn test
```

I risultati sono in `target/surefire-reports`. Su GitHub i test partono a ogni push e anche ogni lunedì mattina da soli, così mi accorgo se la API cambia.

## Una nota

Essendo una API pubblica, se è lenta o è giù il test sui tempi o quelli di rete possono fallire senza che ci sia un bug nel mio codice. Per questo non l'ho messa a fare test di carico: per quelli serve un'applicazione mia, in locale.
