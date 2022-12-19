package br.com.victor.realmeet.integration;

import br.com.victor.realmeet.core.BaseIntegrationTest;
import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.domain.repository.RoomRepository;
import br.com.victor.realmeet.dto.request.AllocationRequest;
import br.com.victor.realmeet.utils.TestDataCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AllocationApiIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private RoomRepository roomRepository;

    @Test
    void testCreateAllocationSuccess() throws Exception {
        Room room = roomRepository.saveAndFlush(TestDataCreator.newRoomBuilder().build());
        AllocationRequest allocationRequest = TestDataCreator.newAllocationRequestBuilder().roomId(room.getId()).build();
        String payload = toJson(allocationRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/allocations")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "pt-br")
                .contentType(APPLICATION_JSON)
                .content(payload);

        ResultActions response = mockMvc.perform(request);

        response.andExpectAll(
                status().isCreated(),
                jsonPath("$.roomId").value(room.getId()),
                jsonPath("$.employeeName").value(allocationRequest.getEmployeeName()),
                jsonPath("$.employeeEmail").value(allocationRequest.getEmployeeEmail()),
                jsonPath("$.subject").value(allocationRequest.getSubject())
        );


    }

    private <T> String toJson(T objetct) throws JsonProcessingException {
        return objectMapper.writeValueAsString(objetct);
    }
}
