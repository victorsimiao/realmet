package br.com.victor.realmeet.core;

import br.com.victor.realmeet.RealmeetApplication;
import br.com.victor.realmeet.domain.entity.Client;
import br.com.victor.realmeet.domain.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static br.com.victor.realmeet.utils.TestConstants.TEST_CLIENT_API_KEY;
import static br.com.victor.realmeet.utils.TestConstants.TEST_CLIENT_DESCRIPTION;
import static org.mockito.BDDMockito.given;

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

    @MockBean
    private ClientRepository clientRepository;

    @BeforeEach
    void setup() {
        setupFlyway();
    }

    private void setupFlyway() {
        flyway.clean();
        flyway.migrate();
    }

    protected void mockApiKey() {
        given(clientRepository.findById(TEST_CLIENT_API_KEY))
                .willReturn(
                        Optional.of(
                                Client
                                        .newBuilder()
                                        .apiKey(TEST_CLIENT_API_KEY)
                                        .description(TEST_CLIENT_DESCRIPTION)
                                        .active(true)
                                        .build()
                        )
                );


    }
}
