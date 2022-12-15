package br.com.victor.realmeet.utils;

import br.com.victor.realmeet.domain.entity.Allocation;
import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.domain.model.Employee;
import br.com.victor.realmeet.dto.request.AllocationRequest;
import br.com.victor.realmeet.dto.request.RoomRequest;

import static br.com.victor.realmeet.utils.TestConstants.*;

public final class TestDataCreator {
    private TestDataCreator() {}

    public static Room.RoomBuilder newRoomBuilder(){
        return Room.newBuilder().name(DEFAULT_ROOM_NAME).seats(DEFAULT_ROOM_SEATS);
    }
    public static RoomRequest.RoomRequestBuilder createRoomRequets(){
        return RoomRequest.newRoomRequestBuilder()
                .name(DEFAULT_ROOM_NAME)
                .seats(DEFAULT_ROOM_SEATS);
    }

    public static AllocationRequest.Builder newAllocationRequestBuilder(){
        return AllocationRequest.newBuilder()
                .roomId(DEFAULT_ROOM_ID)
                .employeeName(DEFAULT_EMPLOYEE_NAME)
                .employeeEmail(DEFAULT_EMPLOYEE_EMAIL)
                .subject(DEFAULT_ALLOCATION_SUBJECT)
                .startAt(DEFAULT_ALLOCATION_START_AT)
                .endAt(DEFAULT_ALLOCATION_END_AT);
    }

    public static Allocation.Builder newAllocationBuilder(Room room){
        return Allocation.newBuilder()
                .id(DEFAULT_ALLOCATION_ID)
                .room(room)
                .employee(Employee.newEmployee().name(DEFAULT_EMPLOYEE_EMAIL).email(DEFAULT_EMPLOYEE_EMAIL).build())
                .subject(DEFAULT_ALLOCATION_SUBJECT)
                .startAt(DEFAULT_ALLOCATION_START_AT)
                .endAt(DEFAULT_ALLOCATION_END_AT);
    }
}
