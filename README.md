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
* utilizatorii pot filtra tranzactiile dupa categorie (ex. transport, utilitati) si dupa tip (venit sau cheltuiala)

### Generarea rapoartelor:
* aplicatia poate genera rapoarte care rezuma totalul veniturilor si cheltuielilor pentru o anumita luna
* utilizatorul are optiunea de a exclude tranzactii din rapoarte pentru a personaliza analiza

## Arhitectura

### Clase
<img width="529" alt="Screenshot 2025-01-18 at 23 05 45" src="https://github.com/user-attachments/assets/9bd73e85-e5f1-4fd6-b625-a840044d46c4" />

* clasa "Transaction"
  - Atribute:
    - transactionID (int): identifificatorul unic al tranzactiei
    - name (String): numele tranzactiei
    - amount (double): suma care a fost incasata sau decontata
    - category (String): categoria tranzactiei
    - paymentMethod (String): metoda de plata (ex. cash, card)
    - date (String): data tranzactiei
    - subscription (boolean): true daca tranzactia este de tip abonament, false altfel (valoarea implicita: false)
    - excpludedFromReport (boolean): true daca tranzactia va fi exclusa din raport, false altfel (valoarea implicita: false)
    - currency (String): moneda tranzactiei
  - Operatii:
    - metode de tip get pentru fiecare atribut
    - metode de tip set pentru fiecare atribut
   
* clasa "Income" extinde clasa "Transaction"
  - Atribute:
    - source (String): sursa venitului
   
* clasa "Expense" extinde clasa "Transaction"
  - Atribute:
    - isEssential (boolean): true daca tranzactia este esentiala (ex. chirie), false altfel (valoarea implicita: false)
   
### Tabele
<img width="621" alt="Screenshot 2025-01-18 at 22 52 13" src="https://github.com/user-attachments/assets/e5f6d4b2-bf05-4d41-9dee-e9cf2427ecc7" />

* tabelul "users": contine utilizatorii, fiecare cu un identificator, username si parola
* tabelul "transactions": contine tranzactiile, cu detalii despre nume, suma, moneda, metoda de plata, data, daca este abonament, daca este exclusa din raport, tip, sursa (in cazul veniturilor), si daca este esentiala (in cazul cheltuielilor); are legatura catre tabelul "users" prin campul "userID"

### Pagini
<img width="399" alt="Screenshot 2025-01-18 at 22 53 50" src="https://github.com/user-attachments/assets/ce4ee5eb-f11d-473e-8fe1-e1a8d0dc44bf" />
<img width="399" alt="Screenshot 2025-01-18 at 22 53 06" src="https://github.com/user-attachments/assets/e6832798-f284-4ac3-88b7-f973f3e04e78" />
<img width="399" alt="Screenshot 2025-01-18 at 22 55 30" src="https://github.com/user-attachments/assets/125a5275-1d74-4ce7-8e50-727c4b574b25" />
<img width="399" alt="Screenshot 2025-01-18 at 22 56 08" src="https://github.com/user-attachments/assets/4ce4b58b-7216-46a0-a3a9-58cc903a54a6" />

## Functionalitati/Exemple utilizare
### Inregistrare:
* Utilizatorul completeaza un formular cu informatiile de inregistrare (username, parola)
* Verifica daca username-ul e unic

### Autentificare:
* Utilizatorul introduce username-ul si parola alese in timpul inregistrarii
* Daca datele sunt corecte, utilizatorul este autentificat

### Adaugarea unei tranzactii:
* Utilizatorul poate adauga o noua tranzactie (venit sau cheltuiala) completand un formular cu urmatoarele detalii: nume, suma, moneda, categoria, metoda de plata, data, si tipul de tranzactie (abonament sau nu)
* Utilizatorul poate selecta daca tranzactia sa fie exclusa din rapoarte

### Editarea unei tranzactii:
* Utilizatorul poate edita o tranzactie existenta

### Stergerea unei tranzactii:
* Utilizatorul poate sterge o tranzactie din lista

### Filtrarea tranzactiilor:
* Utilizatorul poate filtra tranzactiile dupa categorie sau dupa tip

### Generarea unui raport lunar:
* Utilizatorul poate genera un raport al tranzactiilor pentru o luna si un an specificat
