package br.com.victor.realmeet.dto.request;

public class RoomRequest {

    private String name;

    private Integer seats;

    public RoomRequest(String name, Integer seats) {
        this.name = name;
        this.seats = seats;
    }

    public String getName() {
        return name;
    }

    public Integer getSeats() {
        return seats;
    }

    public static RoomRequestBuilder newRoomRequestBuilder() {
        return new RoomRequestBuilder();
    }

    public static final class RoomRequestBuilder {
        private String name;
        private Integer seats;

        private RoomRequestBuilder() {
        }

        public RoomRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RoomRequestBuilder seats(Integer seats) {
            this.seats = seats;
            return this;
        }

        public RoomRequest build() {
            return new RoomRequest(name, seats);
        }
    }
}
