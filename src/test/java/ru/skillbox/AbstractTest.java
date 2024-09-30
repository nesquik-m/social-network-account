package ru.skillbox;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;
import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.entity.Account;
import ru.skillbox.repository.AccountRepository;
import ru.skillbox.service.AccountService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Testcontainers
public class AbstractTest {

    protected static PostgreSQLContainer postgreSQLContainer;
    static {
        DockerImageName postgres = DockerImageName.parse("postgres:12.3");
        postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer(postgres)
                .withReuse(true);
        postgreSQLContainer.start(); // стартанем контейнер с БД
    }

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.url", () -> jdbcUrl);
    }

    @Autowired
    protected AccountService accountService;

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected MockMvc mockMvc;

    @PersistenceContext
    protected EntityManager entityManager;

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected static final String SUCCESSFULLY = "Successfully";

    protected static final String UUID_200_1 = "10000000-0000-0000-0000-000000000200";
    protected static final String UUID_200_2 = "20000000-0000-0000-0000-000000000200";
    protected static final String UUID_200_3 = "30000000-0000-0000-0000-000000000200";

    protected static final String UUID_200 = "0000000-0000-0000-0000-000000000200";

    protected static final String UUID_404 = "00000000-0000-0000-0000-000000000404";

    @BeforeEach
    public void setup() {

        List<Account> accounts = new ArrayList<>();

        for (int accountNum = 1; accountNum <= 5; accountNum++) {
            Account account = Account.builder()
                    .id(UUID.fromString(accountNum + UUID_200))
                    .email("test" + accountNum + "@example.com")
                    .firstName("Name" + accountNum)
                    .lastName("Surname" + accountNum)
                    .birthDate(LocalDateTime.now().minusYears(20 + (accountNum * 2)))
                    .city("Москва")
                    .country("Россия")
                    .isBlocked(false)
                    .isDeleted(false)
                    .isOnline(true)
                    .createdOn(LocalDateTime.now())
                    .updatedOn(LocalDateTime.now())
                    .lastOnlineTime(LocalDateTime.now())
                    .build();
            accounts.add(account);
        }

        accountRepository.saveAll(accounts);

//        KafkaAuthEvent kafkaAuthEvent1 = new KafkaAuthEvent();
//        kafkaAuthEvent1.setUuid(UUID.fromString(UUID_200_1));
//        kafkaAuthEvent1.setEmail("test1@example.com");
//        kafkaAuthEvent1.setFirstName("Test1");
//        kafkaAuthEvent1.setLastName("User1");
//        accountService.createAccount(kafkaAuthEvent1);
//
//        KafkaAuthEvent kafkaAuthEvent2 = new KafkaAuthEvent();
//        kafkaAuthEvent2.setUuid(UUID.fromString(UUID_200_2));
//        kafkaAuthEvent2.setEmail("test2@example.com");
//        kafkaAuthEvent2.setFirstName("Test2");
//        kafkaAuthEvent2.setLastName("User2");
//        accountService.createAccount(kafkaAuthEvent2);
//
//        KafkaAuthEvent kafkaAuthEvent3 = new KafkaAuthEvent();
//        kafkaAuthEvent3.setUuid(UUID.fromString(UUID_200_3));
//        kafkaAuthEvent3.setEmail("test3@example.com");
//        kafkaAuthEvent3.setFirstName("Test3");
//        kafkaAuthEvent3.setLastName("User3");
//        accountService.createAccount(kafkaAuthEvent3);
//
//        KafkaAuthEvent kafkaAuthEvent4 = new KafkaAuthEvent();
//        kafkaAuthEvent4.setUuid(UUID.fromString(UUID_200_4));
//        kafkaAuthEvent4.setEmail("email4@example.com");
//        kafkaAuthEvent4.setFirstName("Name");
//        kafkaAuthEvent4.setLastName("Surname");
//        accountService.createAccount(kafkaAuthEvent4);
    }

    @AfterEach
    public void afterEach() { // Очищаем БД
        accountRepository.deleteAll();
    }

}
