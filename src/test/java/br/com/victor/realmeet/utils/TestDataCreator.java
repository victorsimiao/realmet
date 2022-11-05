package br.com.victor.realmeet.utils;

import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.dto.request.RoomRequest;

public final class TestDataCreator {
    private TestDataCreator() {}

    public static Room.RoomBuilder newRoomBuilder(){
        return Room.newBuilder().name(TestConstants.DEFAULT_ROOM_NAME).seats(TestConstants.DEFAULT_ROOM_SEATS);
    }
    public static RoomRequest.RoomRequestBuilder createRoomRequets(){
        return RoomRequest.newRoomRequestBuilder()
                .name(TestConstants.DEFAULT_ROOM_NAME)
                .seats(TestConstants.DEFAULT_ROOM_SEATS);
    }
}
