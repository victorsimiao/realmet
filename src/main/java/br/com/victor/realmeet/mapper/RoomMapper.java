package br.com.victor.realmeet.mapper;

import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.dto.request.RoomRequest;
import br.com.victor.realmeet.dto.response.RoomResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomResponse fromEntityToDto(Room room);
    Room fromDtoForEntity(RoomRequest roomRequest);
}
