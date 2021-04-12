# Zadanie rekrutacyjne (wersja 2.0)
## Treść
   Stwórz serwis, który będzie udostępniał punkt końcowy (endpoint) REST API poprzez protokół HTTP z parametrami wskazanymi w punkcie 3. Celem samego serwisu jest policzenie prowizji za wykonane przelewy przez danego klienta banku. Zakładamy, że dane znajdujące się w dostarczonym pliku odpowiadają wykonanym przelewom przez danego klienta w danym miesiącu. Dla uproszczenia, użytkownik końcowy powinien autentykować się przy pomocy BASIC w każdym żądaniu HTTP (stateless). Lista użytkowników może być predefiniowana w wybrany sposób, np. w pliku typu properties z użytkownikiem jako klucz i hasłem jako wartość.
   
   Na wejściu serwis przyjmuje numer identyfikacyjny klienta. W odpowiedzi użytkownik powinien dostać pogrupowaną po użytkowniku (imię, nazwisko oraz identyfikator) liczbę wykonanych transakcji, sumę kwot transakcji, wyliczoną prowizję oraz datę ostatniej transakcji danego użytkownika.
   
Każde zapytanie o policzenie prowizji powinno logować ten fakt do bazy danych MongoDb zawierając identyfikator klienta, kwotę prowizji oraz datę policzenia prowizji. Techniczne założenia opisane są w kolejnych puntkach.

## Zestawy danych wejściowych
   plik transactions.csv: plik zawierający transakcje wraz z informacjami odnośnie ich kwoty, imienia, nazwiska i identyfikatora zlecajacego przelew oraz datę transakcji.

plik fee_wages.csv: plik zawierający stawkę procentową prowizji zależny od sumy kwot wszystkich transakcji.

## Parametry wyszukiwania
   a) Numer identyfikacyjny zlecającego tranzakcję-klienta(id): wyszukiwanie użytkownika po numerze identyfikacyjnym (np. 5) z możliwością podania kilku numerów identyfikacyjnych oddzielonych przecinkiem (np. 1, 5). Wprowadzenie kilku identyfikatorów klienta powinno być traktowane jako wyrażenie logiczne „lub”. Brak podania wartości parametru identyfikatora klienta lub wypełnienie go wartością „ALL” powinno przyjąć wszystkie dostępne identyfikatory.

## Przykładowe żądanie-odpowiedź
   Uwaga! Przedstawione poniżej żądania i odpowiedzi nie pochodzą z danych dołączonych do zadania. Prezentują jedynie logikę samego wyszukiwania.

### Przykład:
Parametry żądania: customer_id = 3
Odpowiedź: 1 user →
1. (First name: Adam, Last name: Adamowski, Customer ID = 3, Number of transactions: 11, Total value of transactions: 1500,00 Transactions fee value: 5,33, Last transaction date: 11.12.2020 14:54:31.


Przykład 2:
Parametry żądania: customer_id = ALL
Odpowiedź: 5 users →
1. (First name: Adam, Last name: Adamowski, Customer ID = 3, Number of transactions: 11, Total value of transactions: 1500,00 Transactions fee value: 5,33, Last transaction date: 11.12.2020 14:54:31).
2. (First name: Marek, Last name: Marecki, Customer ID = 1, Number of transactions: 3, Total value of transactions: 95200,30 Transactions fee value: 0,00, Last transaction date: 14.12.2020 11:00:01).
3. (First name: Anna, Last name: Annowska, Customer ID = 2, Number of transactions: 33, Total value of transactions: 12531,21 Transactions fee value: 0,99, Last transaction date: 31.12.2020 19:21:04).
4. (First name: Cezary, Last name: Czarecki, Customer ID = 4, Number of transactions: 0, Total value of transactions: 0,00 Transactions fee value: 10,00, Last transaction date: null).
5. (First name: Damian, Last name: Damianowski, Customer ID = 5 Number of transactions: 2, Total value of transactions: 1512,00 Transactions fee value: 5,01, Last transaction date: 26.12.2020 14:31:54).

## Wymagania techniczne:
Serwis powinien być aplikacją webową napisaną w Kotlinie wraz z użyciem Spring Framework (najchętniej aplikacja Spring Boot w wersji 2.x). Kod może być również napisany częściowo w Java (wersja 8 i wyższe), aczkolwiek zachowując proporcję napisanych co najmniej połowy klas w języku Kotlin.
   Dane, które dostarczone są w postaci plików, powinny zostać załadowane do pamięci aplikacji przy starcie aplikacji. Baza danych może być zarówno umieszczona w kontenerze Docker lub jako standardowa baza „standalone”. Przy projektowaniu rozwiązania wyliczania prowizji weź pod uwagę prędkość działania (załóż, że docelowo aplikacja ma dużą ilość klientów (100 tys. I transakcji – około kilkudziesięciu na jednego klienta). Struktura zapisanych danych do MongoDb jest dowolna. Preferowane narzędzia do budowania to: Gradle 5+ lub Maven.
   Instrukcja włączania i budowania aplikacji powinna być umieszczona w projekcie.
   Możesz również dołączyć obraz dockerowy aplikacji.
   Punkt końcowy (endpoint), którego zadanie dotyczy powinien posiadać testy jednostkowe napisane w języku Java lub Kotlin lub Groovy. Narzędzie do włączania testów jednostkowych jest dowolone, jednakże proszę o zawarcie instrukcji, jak testy te należy włączyć.
   Użycie dobrych praktyk typu „clean code” oraz wzorców projektowych tam, gdzie to ma szczególny sens będzie dodatkowym atutem.

Zadanie nie powinno zająć Ci dłużej niż 4 godziny.