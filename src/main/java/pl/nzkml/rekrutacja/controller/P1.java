package pl.nzkml.rekrutacja.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nzkml.transactionbankside.service.TransactionLogService;

//REST Controller (Spring MVC)

    /*

    Pytanie 1: Chcemy pobrać parametr id z adresu URL: /api/orders/123.
    Której adnotacji użyjesz w argumencie metody kontrolera?

    //Spring boot default
    public ResponseEntity<Object> methodName( ???1/2/3??? Long id){...}

        1. @PathParam("id")
        2. @PathVariable("id")
        3. @RouteParam("id")


    Pytanie 2: Którą z tych adnotacji można użyć nad metodą

        1. @SpringBootApplication
        2. @Async
        3. @Autowired
        4. @RestController
        5. @Bean

    Czy @Bean działa bez @Configuration? Opowiedz o trybach, jakie różnice poniżej?

    @Component <-> @Configuration
    public class MyFactory {

    @Bean
    public DataSource dataSource() {
        return new DataSource();
    }

    @Bean
    public TransactionManager txManager() {
        return new TransactionManager(dataSource());
    }
}

    Pytanie 3: Masz pole w encji, które chcesz zignorować przy zapisie do bazy danych
    (nie ma kolumny w tabeli), ale chcesz je zwracać w JSON-ie do klienta. Czego użyjesz?

        1. @JsonIgnore
        2. @Transient
        3. @NotMapped

    ***

    Pytanie 4: Czy potrzebny gdzieś Autowired?
    Czym sie różnią Adnotacje @Autowired i @Inject? Czy widzisz sytuacje zeby używać Inject?

        @Service
        @Getter
        @Setter
        public class P1 {

            private final TransactionLogService transactionLogService;

            public P1(TransactionLogService transactionLogService) {
                this.transactionLogService = transactionLogService;
            }
        }

    ***


    Pytanie 5: Co jest napisane tutaj niepoprawnie? Jak naprawić?
    Przewidywany ruch przychodzący 100 zapytan na sekunde. Konfiguracja domyślna z springa.

@Service
public class OrderService {

    public void createOrder(OrderDto orderDto) {
        System.out.println("Tworzenie zamówienia. Wątek: " + Thread.currentThread().getName());

        // 2. Chcemy wysłać maila w tle, żeby nie blokować użytkownika
        sendConfirmationEmail(orderDto.getEmail());

        System.out.println("Zamówienie przetworzone.");
    }

    @Async
    public void sendConfirmationEmail(String email) {
        try {
            Thread.sleep(5000); // Symulacja długiej wysyłki
            System.out.println("Wysyłanie maila... Wątek: " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


    Pytanie 6: jak działa zarządzanie pulą wątków w domyślnej konfiguracji springa? (SimpleAsyncTaskExecutor)

    Pytanie 7: Zdefiniowaliśmy ThreadPoolExecutor, Kiedy uruchamiane są wątki z maxPoolSize?
    W jakiej sytuacji nowe zadanie bedzie odrzucone?
    Dajmy konfiguracje, jakie ryzyko?

    corePoolSize = 5
    maxPoolSize = 1500
    queueCapacity = 5000000


Pytanie 8: Kod do analizy, Domyślna konfiguracja springa,

pierwsze wywołanie /features?isAdmin=true
drugie wywołanie /features?isAdmin=false

Co dokładnie zwróci GET /features?isAdmin=false i ile razy zobaczymy log --- Pobieram


public interface FeatureFlagRepository extends JpaRepository<FeatureFlagEntity, Long> {
    // Zwraca listę aktywnych flag z bazy (np. Postgres) - zwraca "DARK_MODE" i "LIVE_CHAT"
    @Query("SELECT f.name FROM FeatureFlagEntity f WHERE f.active = true")
    List<String> findAllActiveFlagNames();
}

@Service
@RequiredArgsConstructor
public class FeatureService {

    @Autowired
    private final FeatureFlagRepository repo;

    @Cacheable("active-features")
    public List<String> getActiveFeatures() {
        System.out.println("--- POBIERAM Z BAZY ---");
        return repo.findAllActiveFlagNames();
    }
}

@RestController
@RequestMapping("/features")
@RequiredArgsConstructor
public class FeatureController {


    private final FeatureService featureService;

    @GetMapping
    public List<String> getFeaturesForUser(@RequestParam(required = false) boolean isAdmin) {
        List<String> features = featureService.getActiveFeatures();

        if (isAdmin) {
            features.add("BETA_TOOLS");
        }

        return features;
    }
}

Jak długo żyje wpis w cache w domyślnej konfiguracji springa? Jak zdefiniować własny czas życia?
Jakich providerów można uzyc w tym przypadku co poprawiłoby sytuacje?

Dajmy na to że mamy metode POST która wpisuje do bazy i GET którzy pobiera liste.
Get ma @Cachable, jak wymusić odświeżenie cache po wykonaniu POST

   */

/*
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
            transactionEntity.calculateTax(); // To trwa długo i zmienia transactionEntity

            transactionRepo.save(transactionEntity);
        });

        throw new RuntimeException("Błąd walidacji końcowej!"); <- tu jest jakaś metoda która rzuci nam wyjątek
    }
}


*/