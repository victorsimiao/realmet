package br.com.victor.realmeet.integration;

import br.com.victor.realmeet.core.BaseIntegrationTest;
import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.domain.repository.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static br.com.victor.realmeet.utils.TestDataCreator.newRoomBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class RoomApiIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void shouldFindRoomByIdWithSuccess() throws Exception {
        Room room = newRoomBuilder().build();
        roomRepository.saveAndFlush(room);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/rooms/" + room.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Room Test"))
                .andExpect(jsonPath("$.active").value(true));

        assertThat(roomRepository.findById(room.getId())).isPresent();
        assertThat(roomRepository.findAll()).size().isEqualTo(1);


    }
}
