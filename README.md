[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/JLYnumnD)
# Coinflip - aplicatie de gestionare a bugetului
### Bianca Balaș

## Descriere
Coinflip este o aplicatie pentru gestionarea bugetului personal care permite utilizatorilor sa isi monitorizeze tranzactiile, oferind functii de filtrare și generare de rapoarte lunare


## Obiective

### Monitorizarea veniturilor si cheltuielilor:
* permite utilizatorilor sa adauge si sa vizualizeze toate veniturile si cheltuielile
* suporta tranzactii recurente, cum ar fi abonamente, care se adauga automat in fiecare luna

### Filtrarea tranzactiilor:
* utilizatorii pot filtra tranzactiile dupa categorie (ex. transport, utilitati) si dupa metoda de plata (cash sau card)

### Generarea rapoartelor:
* aplicatia poate genera rapoarte care rezuma totalul veniturilor si cheltuielilor pentru o anumita luna
* utilizatorul are optiunea de a exclude tranzactii din rapoarte pentru a personaliza analiza

## Arhitectura

### Clase
![JPEG image-4D85-B89A-A7-0](https://github.com/user-attachments/assets/577e4a20-801e-487c-9279-2a6ab9854ed5)


* clasa "Transaction"
  - Atribute:
    - transactionID (int): identifificatorul unic al tranzactiei
    - amount (double): suma care a fost incasata sau decontata
    - category (String): categoria tranzactiei
    - paymentMethod (String): metoda de plata (ex. cash, card)
    - date (String): data tranzactiei
    - subscription (boolean): true daca tranzactia este de tip abonament, false altfel (valoarea implicita: false)
    - excpludedFromReport (boolean): true daca tranzactia va fi exclusa din raport, false altfel (valoarea implicita: false)
  - Operatii:
    - getDetails(): include metodele de tip get pentru fiecare atribut
    - makeSubscription(): inverseaza valoarea curenta a atributului "subscription"
    - excludeFromReport(): inverseaza valoarea curenta a atributului "excpludedFromReport"
   
* clasa "Income" extinde clasa "Transaction"
  - Atribute:
    - source (String): sursa venitului
   
* clasa "Expense" extinde clasa "Transaction"
  - Atribute:
    - isEssential (boolean): true daca tranzactia este esentiala (ex. chirie), false altfel (valoarea implicita: false)
   
* clasa "User"
  - Atribute:
    - userID (int): identifificatorul unic al utilizatorului
    - username (String)
    - password (String)
    - email (String)
    - transactions (Transaction): lista de tranzactii a utilizatorului
  - Operatii:
    - login(username, password)
    - signup(username, password, email)
    - addTransaction(transaction): adauga o noua tranzactie in lista
    - removeTransaction(transaction): elimina o tranzactie din lista
    - editTransaction(transaction): editeaza o tranzactie din lista
    - getTransactions(): returneaza lista de tranzactii
    - getTransactionsByCategory(category): returneaza tranzactiile din lista care fac parte dintr-o anumita categorie
    - getTransactionsByPaymentMethod(paymentMethod): returneaza tranzactiile din lista care au metoda de plata specificata
    - generateReport(month, year): genereaza un raport al tranzactiilor utilizatorului pentru luna si anul specificate
   
### Tabele
![JPEG image-4483-95C9-C4-0](https://github.com/user-attachments/assets/2e502fec-5588-43ce-8682-bd7dfc47c1f5)
* tabelul "users": contine utilizatorii, fiecare cu un identificator, username, parola si email
* tabelul "categories": contine categoriile de tranzactii, fiecare cu un identificator si nume
* tabelul "transactions": contine tranzactiile, cu detalii despre suma, metoda de plata, data, tipul de tranzactie (in caz de abonament), daca este exclusa din raport, sursa (in cazul veniturilor), si daca este esentiala (in cazul cheltuielilor); are legaturi catre tabelele "users" si "categories" prin campurile "userID" si "categoryID"

### Pagini
![Untitled_Artwork 2](https://github.com/user-attachments/assets/57e681bb-31e1-4472-9491-0dd0c4a91ad1)

## Functionalitati/Exemple utilizare
### Inregistrare:
* Utilizatorul completeaza un formular cu informatiile de inregistrare (username, parola, email)
* Verifica daca username-ul si email-ul sunt unice

### Autentificare:
* Utilizatorul introduce username-ul si parola alese in timpul inregistrarii
* Daca datele sunt corecte, utilizatorul este autentificat

### Adaugarea unei tranzactii:
* Utilizatorul poate adauga o noua tranzactie (venit sau cheltuiala) completand un formular cu urmatoarele detalii: suma, categoria, metoda de plata, data, si tipul de tranzactie (abonament sau nu)
* Utilizatorul poate selecta daca tranzactia sa fie exclusa din rapoarte

### Editarea unei tranzactii:
* Utilizatorul poate edita o tranzactie existenta

### Stergerea unei tranzactii:
* Utilizatorul poate sterge o tranzactie din lista

### Filtrarea tranzactiilor:
* Utilizatorul poate filtra tranzactiile dupa categorie sau dupa metoda de plata

### Generarea unui raport lunar:
* Utilizatorul poate genera un raport al tranzactiilor pentru o luna si un an specificat
