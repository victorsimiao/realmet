package br.com.victor.realmeet.unit;

import br.com.victor.realmeet.core.BaseUnitTest;
import br.com.victor.realmeet.domain.entity.Allocation;
import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.dto.request.AllocationRequest;
import br.com.victor.realmeet.dto.response.AllocationResponse;
import br.com.victor.realmeet.mapper.AllocationMapper;
import br.com.victor.realmeet.utils.MapperUtils;
import br.com.victor.realmeet.utils.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static br.com.victor.realmeet.utils.TestDataCreator.*;
import static org.junit.jupiter.api.Assertions.*;

public class AllocationMapperUnitTest extends BaseUnitTest {

    private AllocationMapper allocationMapper;

    @BeforeEach
    void setupEach() {
        allocationMapper = MapperUtils.newAllocationMapper();
    }

    @Test
    void testFromAllocationRequestToAllocation() {
        AllocationRequest allocationRequest = newAllocationRequestBuilder().build();
        Room room = newRoomBuilder().build();

        Allocation allocation = allocationMapper.fromAllocationRequestToEntity(allocationRequest, room);

        assertNull(allocation.getId());
        assertEquals(allocationRequest.getEmployeeName(), allocation.getEmployee().getName());
        assertEquals(allocationRequest.getEmployeeEmail(), allocation.getEmployee().getEmail());
        assertEquals(allocationRequest.getSubject(), allocation.getSubject());
        assertEquals(allocationRequest.getStartAt(), allocation.getStartAt());
        assertEquals(allocationRequest.getEndAt() , allocation.getEndAt());

    }


    @Test
    void testFromAllocationToDto(){
        Room room = newRoomBuilder().id(TestConstants.DEFAULT_ROOM_ID).build();
        Allocation allocation = newAllocationBuilder(room).build();

        AllocationResponse allocationResponse = allocationMapper.fromEntityToDto(allocation);

        assertNotNull(allocationResponse.getRoomId());
        assertEquals(allocation.getEmployee().getName(), allocationResponse.getEmployeeName());
        assertEquals(allocation.getEmployee().getEmail(), allocationResponse.getEmployeeEmail());
        assertEquals(allocation.getSubject(), allocationResponse.getSubject());
        assertEquals(allocation.getStartAt(), allocationResponse.getStartAt());
        assertEquals(allocation.getEndAt(), allocationResponse.getEndAt());
    }
}
