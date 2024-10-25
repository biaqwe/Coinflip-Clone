[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/JLYnumnD)
# Coinflip - aplicatie de gestionare a bugetului
### Student(i)

## Descriere
Coinflip este o aplicatie pentru gestionarea bugetului personal care permite utilizatorilor sa isi monitorizeze tranzactiile, oferind functii de filtrare și generare de rapoarte lunare


## Obiective

* monitorizarea veniturilor si cheltuielilor utilizatorului
  - filtrarea tranzactiilor după categorie
  - filtrarea tranzactiilor dupa modul de plata (cash sau card)
* generarea unui raport al tranzactiilor pentru fiecare luna

## Arhitectura
![Untitled Notebook-4 2](https://github.com/user-attachments/assets/a30b0f84-669b-454d-8080-5e0edcd21d5f)

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

## Functionalitati/Exemple utilizare
* adaugarea, editarea si stergerea veniturilor si cheltuielilor
* adaugarea tranzactiilor de tip abonament care se adauga automat in aceeasi zi a fiecarei luni
* optiunea de a exclude tranzactii din raportul lunar
* filtrarea tranzactiilor dupa categorie sau dupa modul de plata

### Resurse
Markdown Guide, [Online] Available: https://www.markdownguide.org/basic-syntax/ [accesed: Mar 14, 1706]
