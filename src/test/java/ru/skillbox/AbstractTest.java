package ru.skillbox;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.repository.AccountRepository;
import ru.skillbox.service.AccountService;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Testcontainers
//@RunWith(SpringRunner.class) // для JUnit4
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

    @BeforeEach
    public void setup() {
        KafkaAuthEvent kafkaAuthEvent = new KafkaAuthEvent();
        kafkaAuthEvent.setUuid(UUID.fromString("8416d06e-3cb9-4d6d-b96a-84f287d216fd"));
        kafkaAuthEvent.setEmail("test@example.com");
        kafkaAuthEvent.setFirstName("Test");
        kafkaAuthEvent.setLastName("User");

        accountService.createAccount(kafkaAuthEvent);
    }

    @AfterEach
    public void afterEach() { // Очищаем БД
        accountRepository.deleteAll();
    }

}
