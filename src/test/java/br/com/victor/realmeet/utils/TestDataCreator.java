package br.com.victor.realmeet.utils;

import br.com.victor.realmeet.domain.entity.Room;

public final class TestDataCreator {
    private TestDataCreator() {}

    public static Room.RoomBuilder newRoomBuilder(){
        return Room.newBuilder().name(TestConstants.DEFAULT_ROOM_NAME).seats(TestConstants.DEFAULT_ROOM_SEATS);
    }
}
