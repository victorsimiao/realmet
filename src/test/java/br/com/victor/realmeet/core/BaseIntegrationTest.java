package br.com.victor.realmeet.core;

import br.com.victor.realmeet.RealmeetApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(profiles = "integration-test")
@AutoConfigureMockMvc
@SpringBootTest(classes = RealmeetApplication.class)
public abstract class BaseIntegrationTest {

    @Autowired
    private Flyway flyway;

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        setupFlyway();
    }

    private void setupFlyway() {
        flyway.clean();
        flyway.migrate();
    }


}
