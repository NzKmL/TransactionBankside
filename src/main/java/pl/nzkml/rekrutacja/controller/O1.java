package pl.nzkml.rekrutacja.controller;

public class O1 {
    /*

    Pytanie 1: Chcemy pobrać parametr id z adresu URL: /api/orders/123. Której adnotacji użyjesz w argumencie metody kontrolera?

    public ResponseEntity<Object> methodName( ???1/2/3??? Long id){...} Spring boot default

        1. @PathParam("id")
        2. @PathVariable("id")
        3. @RouteParam("id")

        A (ŹLE/PUŁAPKA): @PathParam pochodzi ze specyfikacji JAX-RS (javax.ws.rs). W czystym Spring MVC (Spring Boot default) nie zadziała, chyba że mamy skonfigurowanego Jersey'a. To bardzo częsta pomyłka.
        B (DOBRZE): @PathVariable to natywna adnotacja Spring MVC.
        C (ŹLE): Taka adnotacja nie istnieje w standardowym Springu.

      */

    /*
    Pytanie 2: Którą z tych adnotacji można użyć nad metodą

        1. @SpringBootApplication - NIE
        2. @Async - TAK
        3. @Autowired - TAK ALE
        4. @RestController -NIE
        5. @Bean -TAK

    Czy @Bean działa bez @Configuration? Opowiedz o trybach.

Czy @Bean zadziała bez @Configuration?
    Krótka odpowiedź: TAK, zadziała. Ale działa wtedy w trybie "Lite Mode", co niesie ze sobą poważną pułapkę.
    Dłuższa odpowiedź (dla Seniora): Jeśli umieścisz metodę @Bean w zwykłej klasie (np. @Component albo w ogóle bez adnotacji klasowej, ale zaimportowanej), Spring zarejestruje tego beana w kontekście.
    Gdzie jest pułapka? (Lite Mode vs Full Mode)
    W klasie z @Configuration (Full Mode): Metody są "proxyowane" przez CGLIB. Jeśli metoda beanA() wywoła metodę beanB(), Spring przechwyci to wywołanie i sprawdzi w cache'u, czy beanB już istnieje. Jeśli tak – zwróci istniejącego Singletona.
    W klasie bez @Configuration (Lite Mode): Nie ma proxy CGLIB. Metody działają jak zwykłe metody w Javie. Jeśli beanA() wywoła beanB(), to beanB() wykona się normalnie i stworzy nową instancję obiektu, ignorując to, że Spring ma już takiego singletona w swoim kontekście.
    Przykład, który "wyłoży" kandydata:

Java

// Zwykła klasa (Lite Mode) - BRAK @Configuration
@Component
public class MyFactory {

    @Bean
    public DataSource dataSource() {
        return new DataSource(); // Tworzy połączenie do bazy
    }

    @Bean
    public TransactionManager txManager() {
        // PUŁAPKA: To wywołanie NIE przejdzie przez Springa!
        // Wykona się jak zwykła metoda Java.
        // Wynik: txManager dostanie INNĄ instancję DataSource niż reszta aplikacji.
        return new TransactionManager(dataSource());
    }
}

Czy konstruktor to metoda?
    Odpowiedź: NIE.
    Z technicznego punktu widzenia (Java Language Specification), konstruktor nie jest metodą, chociaż wygląda podobnie.
    Różnice (dla purysty językowego):
    Typ zwracany: Metoda musi mieć zadeklarowany typ zwracany (lub void). Konstruktor nie ma typu zwracanego (nawet void).
    Nazwa: Konstruktor musi nazywać się tak samo jak klasa. Metoda może mieć dowolną nazwę.
    Dziedziczenie: Metody są dziedziczone przez klasy potomne. Konstruktory nie są dziedziczone.
    Wywołanie: Metody wołamy na obiekcie (obiekt.metoda()), konstruktor wołamy operatorem new (zazwyczaj) przy tworzeniu obiektu.
    Dlaczego to pytanie pada przy @Autowired? Bo Spring pozwala użyć @Autowired zarówno nad konstruktorem, jak i nad metodą (setterem). Chociaż mechanizm wstrzykiwania jest ten sam (DI), to element języka Java, który dekorujemy, jest inny.
    W Reflection API też są to dwie różne klasy: java.lang.reflect.Method vs java.lang.reflect.Constructor.


    Pytanie 3: Masz pole w encji, które chcesz zignorować przy zapisie do bazy danych
    (nie ma kolumny w tabeli), ale chcesz je zwracać w JSON-ie do klienta. Czego użyjesz?

        1. @JsonIgnore
        2. @Transient
        3. @NotMapped

        A (ŹLE): @JsonIgnore (Jackson) robi coś odwrotnego – pole JEST w bazie (zazwyczaj), ale NIE MA go w JSON-ie.
        B (DOBRZE): @Transient (z pakietu jakarta.persistence lub javax.persistence) informuje JPA: "nie mapuj tego pola na kolumnę w bazie". Pole będzie w obiekcie Java i zostanie zserializowane do JSON (chyba że dodamy też @JsonIgnore).
        C (ŹLE): Taka adnotacja nie istnieje w standardzie JPA.
*/
}
/*

    Pytanie 4: Czy potrzebny gdzieś Autowired?

        @Service
        @Getter
        @Setter
        public class P1 {

            private final TransactionLogService transactionLogService;

            public P1(TransactionLogService transactionLogService) {
                this.transactionLogService = transactionLogService;
            }
        }

Pytanie 4: Czym sie różnią Adnotacje @Autowired i @Inject?

Autowired jest ze springa a Inject z Javy,
Jak oceniać odpowiedź kandydata?
Poziom Junior: "Obie służą do wstrzykiwania zależności, ale @Autowired jest ze Springa".
(To wystarczająca odpowiedź na start).

Poziom Mid: Powinien wspomnieć o tym, że @Inject to standard Java (JSR-330) i że
pozwala na uniezależnienie kodu od Springa.

Poziom Senior: Powinien wskazać różnicę w obsłudze braku zależności (required=false)
 w Autowired vs brak takiej opcji w Inject) oraz wspomnieć, że obecnie w Springu i
 tak preferuje się wstrzykiwanie przez konstruktor, gdzie żadna z tych adnotacji nie jest zazwyczaj potrzebna.


    Czy potrzebny gdzieś Autowired? Kiedy byłby potrzebny?
    Gdy mamy 2 konstruktory.


@Service
public class P1 {

    private final TransactionLogService transactionLogService;

    public P1(TransactionLogService transactionLogService) {
        this.transactionLogService = transactionLogService;
    }
}

    Pytanie 5: co jest napisane tutaj niepoprawnie? Jak naprawić?

        metoda sendConfirmationEmail wykona się na tym samym wątku (synchronicznie).

        Cała operacja potrwa ponad 5 sekund (zablokuje się).

        Dlaczego? (Wyjaśnienie techniczne): Spring używa wzorca Proxy, aby obsłużyć adnotacje takie jak
         @Async, @Transactional czy @Cacheable.

        Kiedy wstrzykujesz OrderService do innej klasy (np. Kontrolera), Spring wstrzykuje Proxy (wrapper),
        który przechwytuje wywołania.

        Kiedy jednak wywołujesz metodę this.sendConfirmationEmail(...) z wewnątrz tej samej klasy,
        pomijasz Proxy i wołasz "czystą" metodę Java bezpośrednio na obiekcie.
         Spring nie ma szansy "wtrącić się" i uruchomić nowego wątku.

        Jak to naprawić? (Pytanie ratunkowe)

        Jeśli kandydat poprawnie zidentyfikuje problem, zapytaj: "Jak byś to naprawił?".

        Dobre rozwiązania:
        Wydzielenie do innej klasy: Przenieść sendConfirmationEmail do osobnego serwisu (np. NotificationService) i wstrzyknąć go do OrderService. (Najczystsze rozwiązanie).
        Self-injection (rzadziej stosowane): Wstrzyknąć OrderService do samego siebie (np. przez @Lazy lub ObjectProvider) i wywołać metodę przez wstrzyknięty bean.
        Pytanie Bonusowe: Zwracanie wartości
        "A gdybyśmy chcieli z metody @Async zwrócić wynik (np. status wysyłki), jaki typ zwracany powinna mieć ta metoda?"
        Odp: CompletableFuture<String> (lub Future).
        Zwykły String zwróci null, ponieważ metoda asynchroniczna kończy działanie w głównym wątku natychmiast, zanim wynik zostanie obliczony w tle.
        Przechodzimy do głównego dania (Code Review)?

Odpowiedź (Ściąga dla Ciebie)
1. Jak działa domyślny mechanizm? Jeśli nie zdefiniujemy beana Executor (lub TaskExecutor), Spring domyślnie użyje SimpleAsyncTaskExecutor. To bardzo myląca nazwa. Ten executor nie używa puli wątków. Zamiast tego, dla każdego wywołania metody asynchronicznej tworzy nowy wątek, a po wykonaniu zadania go niszczy.

2. Ryzyko:

Wybuch pamięci (OOM): Przy 1000 zapytań na sekundę i 10-sekundowym oczekiwaniu, w pamięci powstanie 10 000 żywych wątków. Każdy wątek zajmuje pamięć na stos (domyślnie ok. 1MB). To szybko doprowadzi do OutOfMemoryError.
Zarżnięcie CPU: Sam koszt tworzenia i niszczenia wątków (context switching) zabije procesor, nawet jeśli wątki tylko "czekają".
Prawidłowe rozwiązanie: Kandydat powinien powiedzieć: "Musimy skonfigurować ThreadPoolTaskExecutor". Powinien zdefiniować:

corePoolSize (podstawowa liczba wątków),
maxPoolSize (maksymalna liczba),
queueCapacity (ile zadań może czekać w kolejce, zanim odrzucimy nowe).

Pytanie Bonusowe (Dla Seniora): Rozmiar Puli
Pytanie: "Ok, tworzymy własną pulę. Na jaką wartość ustawiłbyś liczbę wątków,
jeśli zadanie polega głównie na czekaniu na odpowiedź HTTP (I/O bound),
a na jaką, jeśli to skomplikowane obliczenia matematyczne (CPU bound)?"

Obliczenia (CPU bound): Liczba wątków zbliżona do liczby rdzeni procesora
(np. liczba_rdzeni + 1). Więcej wątków tylko spowolni system przez przełączanie kontekstu.

Czekanie na sieć/bazę (I/O bound): Pula może być dużo większa niż liczba rdzeni
(np. 50, 100, 200), ponieważ wątki większość czasu "śpią", czekając na odpowiedź, więc nie obciążają CPU.

Nowinka (Java 21+)
Jeśli kandydat wspomni o Wątkach Wirtualnych (Virtual Threads / Project Loom) – to ogromny plus.
Wtedy klasyczne pule wątków przestają być tak krytyczne przy operacjach I/O, bo wirtualne wątki są superlekkie.

  Pytanie 6: jak działa zarządzanie pulą wątków w domyślnej konfiguracji springa? (SimpleAsyncTaskExecutor)



 Pytanie 7: Zdefiniowaliśmy ThreadPoolExecutor, Kiedy uruchamiane są wątki z maxPoolSize?
Prawidłowa kolejność działania (algorytm Springa/Javy):

Jeśli liczba aktywnych wątków < corePoolSize -> Uruchom nowy wątek.

Jeśli liczba aktywnych wątków == corePoolSize -> Wstaw zadanie do KOLEJKI.

Jeśli KOLEJKA JEST PEŁNA i liczba wątków < maxPoolSize -> Dopiero teraz Uruchom nowy wątek (z puli max).

Jeśli kolejka pełna i liczba wątków == maxPoolSize -> Odrzuć zadanie (wyjątek RejectedExecutionException).

    Pytanie 8 Kod do analizy:
// 1. Standardowe Repozytorium JPA
public interface FeatureFlagRepository extends JpaRepository<FeatureFlagEntity, Long> {
    // Zwraca listę aktywnych flag z bazy (np. Postgres)
    @Query("SELECT f.name FROM FeatureFlagEntity f WHERE f.active = true")
    List<String> findAllActiveFlagNames();
}

// 2. Serwis z Cachem
@Service
public class FeatureService {

    @Autowired
    private FeatureFlagRepository repo;

    @Cacheable("active-features")
    public List<String> getActiveFeatures() {
        // Pobieramy z bazy raz, potem serwujemy z pamięci
        return repo.findAllActiveFlagNames();
    }
}

// 3. Kontroler (Tu dzieje się magia)
@RestController
@RequestMapping("/features")
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @GetMapping
    public List<String> getFeaturesForUser(@RequestParam(required = false) boolean isAdmin) {
        // Krok 1: Pobierz flagi z serwisu (z bazy lub cache)
        List<String> features = featureService.getActiveFeatures();

        // Krok 2: Jeśli to ADMIN, dodaj mu specjalną flagę 'BETA_TOOLS' do widoku
        if (isAdmin) {
            features.add("BETA_TOOLS");
        }

        return features;
    }
}

nastepne pytania:

Jak zabezpieczyć kod przed taką sytuacją?"

        Dobra odp: Zwracać kolekcje niemutowalne.

        Przykład: return Collections.unmodifiableList(repo.findAllActiveFlagNames());

        Przykład (Java 10+): return List.copyOf(repo.findAllActiveFlagNames());
        Efekt: Wtedy linia features.add(...) w kontrolerze rzuci UnsupportedOperationException
        przy pierwszym testowaniu, natychmiast sygnalizując programiście błąd logiczny ("Fail Fast").


A jak naprawić to w Kontrolerze, jeśli nie możemy ruszać Serwisu?"

Odp: Tworzyć obronną kopię (Defensive Copy) przed modyfikacją.
List<String> safeFeatures = new ArrayList<>(featureService.getActiveFeatures());
safeFeatures.add("BETA_TOOLS");

Czy ten błąd wystąpiłby, gdybyśmy używali Redisa zamiast domyślnego cache'u? Dlaczego?"

Odp: Nie wystąpiłby (zazwyczaj).

Wyjaśnienie: Redis przechowuje dane jako zserializowane (np. JSON).
Przy odczycie (get) biblioteka (np. Jackson) deserializuje JSON do nowego obiektu Java.
Kontroler modyfikowałby więc swoją własną, unikalną kopię listy, a nie to, co leży w Redisie.

Uwaga dla eksperta: To działa tak bezpiecznie tylko przy odczycie.
Jeśli zrobilibyśmy @CachePut po modyfikacji, to zepsulibyśmy dane w Redisie.

Jak długo żyje wpis w cache w domyślnej konfiguracji springa?
1. Domyślny Cache (ConcurrentMapCacheManager)
Jeśli kandydat nie skonfiguruje niczego (tylko doda @EnableCaching),
Spring użyje zwykłej ConcurrentHashMap w pamięci.

Jak długo żyje wpis?
Wiecznie. Aż do restartu aplikacji.
Nie ma mechanizmu usuwania starych wpisów.
Zagrożenie: Jeśli cache'ujemy dużo różnych danych (np. per użytkownik),
w końcu dostaniemy OutOfMemoryError, bo mapa będzie tylko rosła.
Jak czyścić?
 Tylko ręcznie, używając adnotacji @CacheEvict lub harmonogramu (@Scheduled), który czyści całą mapę.


Caffeine (Najpopularniejszy cache lokalny)
W profesjonalnych projektach (monolity, mikroserwisy bez Redisa) używa się biblioteki Caffeine. Tutaj konfigurujemy czas życia.
Konfiguracja: Zazwyczaj w application.properties:
Propertie
spring.cache.caffeine.spec=maximumSize=500,expireAfterWrite=10m
Strategie wygasania:
expireAfterWrite: Czas liczony od momentu zapisu/aktualizacji. (Najczęstsze).
expireAfterAccess: Czas liczony od ostatniego odczytu (każde pobranie przedłuża życie - dobre dla sesji).

Dajmy na to że mamy metode POST która wpisuje do bazy i GET którzy pobiera liste.
Get ma @Cachable, jak wymusić odświeżenie cache po wykonaniu POST

@PostMapping("/countries")
@CacheEvict(value = "countries", allEntries = true) // <--- Czyści cache przy zapisie
public void addCountry(CountryDto dto) {
    repository.save(dto);
}

Pytanie 9
Kod jak niżej, uruchomione processTransaction na liście 100 transakcji.
Jaki będzie stan bazy na koniec?

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final transactionRepository transactionRepo;
    private final AuditRepository auditRepo;

    @Transactional // <--- Główna transakcja
    public void processTransaction(List<Transaction> dtos)
        AuditLog log = new AuditLog("START_BATCH");
        auditRepo.save(log);
        dtos.parallelStream().forEach(dto -> {
            TransactionEntity transactionEntity = new TransactionEntity(dto);
            transactionEntity.calculateTax(); // To trwa długo

            transactionRepo.save(transactionEntity);
        });

        throw new RuntimeException("Błąd walidacji końcowej!"); <- tu jest jakaś metoda która rzuci nam wyjątek
    }
}

Odpowiedź (Ściąga dla Ciebie)
To jest podchwytliwe pytanie o zasięg transakcji w wątkach.
Wynik w bazie danych:

AuditLog: Zostanie WYCOFANY (Rollback). Zniknie.
Faktury: Zostaną ZAPISANE (Committed). Będą w bazie (niespójność danych!).

Dlaczego? (Analiza Techniczna):
ThreadLocal: W Springu kontekst transakcyjny jest przechowywany w ThreadLocal.
Oznacza to, że transakcja jest "przyklejona" do konkretnego wątku (tego głównego, który wszedł do metody).

Główny wątek: Otwiera transakcję, zapisuje AuditLog. Kiedy na końcu metody leci wyjątek,
Spring przechwytuje go i robi ROLLBACK na głównej transakcji. Dlatego AuditLog znika.

Parallel Stream: Używa on wspólnej puli wątków (ForkJoinPool.commonPool()). Te wątki są nowe/inne niż wątek główny.

Brak propagacji: Nowe wątki wewnątrz parallelStream nie widzą transakcji głównego wątku.
Nie mają one żadnego kontekstu transakcyjnego.

Repozytorium w nowym wątku: Kiedy wewnątrz streama wołamy invoiceRepo.save(invoice),
 repozytorium (Spring Data) widzi, że nie ma aktywnej transakcji w tym wątku. Co robi?
 Otwiera nową, malutką transakcję tylko na czas tego jednego zapisu (autocommit).

Efekt: Każda faktura zapisuje się natychmiast i niezależnie (COMMIT). Wyjątek w głównym wątku
 nie ma żadnego wpływu na te niezależne transakcje, które już dawno się zakończyły.

Zapytaj kandydata: "Widzisz, że mamy niespójność (Faktury są, logu nie ma, a miał być rollback wszystkiego).
Jak byś przepisał ten kod, żeby zachować wydajność (wielowątkowość), ale naprawić transakcyjność?"

Dobre rozwiązania:
Oddzielenie obliczeń od zapisu (Split Phase):
    Użyj parallelStream tylko do trudnych obliczeń (calculateTax()) i przygotowania obiektów w pamięci (List<Entity>).
    Po zakończeniu streama, w głównym wątku wykonaj invoiceRepo.saveAll(entities).
    Wtedy saveAll wykona się w głównej transakcji. Jeśli poleci wyjątek -> wszystko zniknie.

Transakcyjność programowa (TransactionTemplate) - dla Hardcorów:
    Ręczne zarządzanie transakcją wewnątrz wątków (skomplikowane, rzadko zalecane).

Złe rozwiązanie (Pułapka):

"Dodam @Transactional nad metodą wewnątrz streama" -> Nie zadziała (Self-invocation + brak propagacji między wątkami).
"Użyję @Async" -> Ten sam problem. Nowy wątek = nowa transakcja (domyślnie PROPAGATION_REQUIRED utworzy nową, bo nie widzi starej).

Bonus: LazyInitializationException (Jeśli kandydat to Senior)
Możesz dodać smaczku: "A co jeśli InvoiceDto miałoby pole z dociąganą leniwie (Lazy) listą produktów, i próbowalibyśmy się do niej odwołać wewnątrz parallelStream?"
Odp: Poleci LazyInitializationException.
Encja/DTO jest podpięta pod sesję Hibernate (Persistence Context) głównego wątku.
Wątki z parallelStream nie mają dostępu do tej sesji. Próba dociągnięcia danych w innym wątku zakończy się błędem "no session".
To zadanie idealnie zamyka Twój zestaw: sprawdziłeś REST, Spring Core, Cache i teraz najtrudniejsze – współbieżność w bazie danych. Powodzenia jutro!


"Jeśli użyjemy parallelStream w tym miejscu, a jednocześnie
w innym serwisie też go użyjemy do bardzo wolnej operacji – czy te dwa procesy będą na siebie wpływać?"
Jak działa póla wątków dla parallel jak sie ją definiuje i jakie są tego skutki
*/

