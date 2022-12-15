package br.com.victor.realmeet.unit;

import br.com.victor.realmeet.core.BaseUnitTest;
import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.dto.response.RoomResponse;
import br.com.victor.realmeet.mapper.RoomMapper;
import br.com.victor.realmeet.utils.MapperUtils;
import br.com.victor.realmeet.utils.TestDataCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RoomMapperUnitTest extends BaseUnitTest {

    private RoomMapper roomMapper;

    @BeforeEach
    void setupEach() {
        roomMapper = MapperUtils.newRoomMapper();
    }

    @Test
    void testFromEntityToDto() {
        Room room = TestDataCreator.newRoomBuilder().build();

        RoomResponse roomResponse = roomMapper.fromEntityToDto(room);

        assertEquals(room.getName(),roomResponse.getName());
        assertEquals(room.getSeats(),roomResponse.getSeats());
    }
}