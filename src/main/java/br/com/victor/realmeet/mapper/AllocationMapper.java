package br.com.victor.realmeet.mapper;

import br.com.victor.realmeet.domain.entity.Allocation;
import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.dto.request.AllocationRequest;
import br.com.victor.realmeet.dto.response.AllocationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AllocationMapper {

    @Mapping(source = "room", target = "room")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "allocationRequest.employeeName", target = "employee.name")
    @Mapping(source = "allocationRequest.employeeEmail", target = "employee.email")
    Allocation fromAllocationRequestToEntity(AllocationRequest allocationRequest, Room room);

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "employee.name", target = "employeeName")
    @Mapping(source = "employee.email", target = "employeeEmail")
    AllocationResponse fromEntityToDto(Allocation allocation);

}
