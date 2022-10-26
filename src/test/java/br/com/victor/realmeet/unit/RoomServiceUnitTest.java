package br.com.victor.realmeet.unit;

import br.com.victor.realmeet.core.BaseUnitTest;
import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.domain.repository.RoomRepository;
import br.com.victor.realmeet.dto.response.RoomResponse;
import br.com.victor.realmeet.service.RoomService;
import br.com.victor.realmeet.utils.MapperUtils;
import br.com.victor.realmeet.utils.TestConstants;
import br.com.victor.realmeet.utils.TestDataCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RoomServiceUnitTest extends BaseUnitTest {

    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    public void setupEach() {
        roomService = new RoomService(roomRepository, MapperUtils.newRoomMapper());
    }

    @Test
    void testGetRoom(){
        Room room = TestDataCreator.newRoomBuilder().id(TestConstants.DEFAULT_ROOM_ID).build();
        when(roomRepository.findById(TestConstants.DEFAULT_ROOM_ID)).thenReturn(Optional.of(room));

        RoomResponse roomResponse = roomService.getRoom(TestConstants.DEFAULT_ROOM_ID);

        assertEquals(room.getName(),roomResponse.getName());
        assertEquals(room.getSeats(),roomResponse.getSeats());

    }
}
