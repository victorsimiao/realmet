package br.com.victor.realmeet.integration;

import br.com.victor.realmeet.core.BaseIntegrationTest;
import br.com.victor.realmeet.domain.entity.Allocation;
import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.domain.repository.AllocationRepository;
import br.com.victor.realmeet.domain.repository.RoomRepository;
import br.com.victor.realmeet.dto.request.AllocationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static br.com.victor.realmeet.utils.TestConstants.*;
import static br.com.victor.realmeet.utils.TestDataCreator.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AllocationApiIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AllocationRepository allocationRepository;

    @Test
    void testCreateAllocationSuccess() throws Exception {
        Room room = roomRepository.saveAndFlush(newRoomBuilder().build());
        AllocationRequest allocationRequest = newAllocationRequestBuilder().roomId(room.getId()).build();
        mockApiKey();
        String payload = toJson(allocationRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/allocations")
                .header("api-key", TEST_CLIENT_API_KEY)
                .header("Accept-Language", "pt-br")
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

    private List<Allocation> persistAllocations(int numberOfAllocations) {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());

        return IntStream
                .range(0, numberOfAllocations)
                .mapToObj(
                        i ->
                                allocationRepository.saveAndFlush(
                                        newAllocationBuilder(room)
                                                .subject(DEFAULT_ALLOCATION_SUBJECT + "_" + (i + 1))
                                                .startAt(DEFAULT_ALLOCATION_START_AT.plusHours(i + 1))
                                                .endAt(DEFAULT_ALLOCATION_END_AT.plusHours(i + 1))
                                                .build()
                                )
                )
                .collect(Collectors.toList());
    }
}
