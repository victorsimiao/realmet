package br.com.victor.realmeet.core;

import br.com.victor.realmeet.RealmeetApplication;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "integration-test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RealmeetApplication.class)
public abstract class BaseIntegrationTest {

    @Autowired
    private Flyway flyway;

    @BeforeEach
    void setup() {
        setupFlyway();
    }

    private void setupFlyway() {
        flyway.clean();
        flyway.migrate();
    }


}
