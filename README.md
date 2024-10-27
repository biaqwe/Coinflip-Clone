[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/JLYnumnD)
# Coinflip - aplicatie de gestionare a bugetului
### Bianca Balaș

## Descriere
Coinflip este o aplicatie pentru gestionarea bugetului personal care permite utilizatorilor sa isi monitorizeze tranzactiile, oferind functii de filtrare și generare de rapoarte lunare


## Obiective

* monitorizarea veniturilor si cheltuielilor utilizatorului
  - filtrarea tranzactiilor după categorie
  - filtrarea tranzactiilor dupa modul de plata (cash sau card)
* generarea unui raport al tranzactiilor
  - total venituri si cheltuieli

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
    - mostenite: amount, category, paymentMethod, date, subscription
    - source (String): sursa venitului
  -Operatii:
    - getDetails(): include metodele de tip get pentru fiecare atribut
   
* clasa "Expense" extinde clasa "Transaction"
  - Atribute:
    - mostenite: amount, category, paymentMethod, date, subscription
    - isEssential (boolean): true daca tranzactia este esentiala (ex. chirie), false altfel (valoarea implicita: false)
  -Operatii:
    - getDetails(): include metodele de tip get pentru fiecare atribut
   
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
![Untitled_Artwork](https://github.com/user-attachments/assets/437234d2-70e1-45e5-be24-3b0906a0f2ff)



## Functionalitati/Exemple utilizare
* adaugarea, editarea si stergerea veniturilor si cheltuielilor
* adaugarea tranzactiilor de tip abonament care se adauga automat in aceeasi zi a fiecarei luni
* optiunea de a exclude tranzactii din rapoarte
* filtrarea tranzactiilor dupa categorie sau dupa modul de plata

### Resurse
Markdown Guide, [Online] Available: https://www.markdownguide.org/basic-syntax/ [accesed: Mar 14, 1706]
