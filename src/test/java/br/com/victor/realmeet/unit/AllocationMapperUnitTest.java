package br.com.victor.realmeet.unit;

import br.com.victor.realmeet.core.BaseUnitTest;
import br.com.victor.realmeet.domain.entity.Allocation;
import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.dto.request.AllocationRequest;
import br.com.victor.realmeet.dto.response.AllocationResponse;
import br.com.victor.realmeet.mapper.AllocationMapper;
import br.com.victor.realmeet.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static br.com.victor.realmeet.utils.TestDataCreator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        Room room = newRoomBuilder().build();
        Allocation allocation = newAllocationBuilder(room).build();

        AllocationResponse allocationResponse = allocationMapper.fromEntityToDto(allocation);

        assertEquals(allocationResponse.getEmployeeName(), allocation.getEmployee().getName());
        assertEquals(allocationResponse.getEmployeeEmail(), allocation.getEmployee().getEmail());
        assertEquals(allocationResponse.getSubject(), allocation.getSubject());
        assertEquals(allocationResponse.getStartAt(), allocation.getStartAt());
        assertEquals(allocationResponse.getEndAt() , allocation.getEndAt());
    }
}
