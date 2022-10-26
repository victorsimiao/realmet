package br.com.victor.realmeet.mapper;

import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.dto.response.RoomResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomResponse fromEntityForDto(Room room);
}
