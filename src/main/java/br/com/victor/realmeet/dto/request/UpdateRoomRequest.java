package br.com.victor.realmeet.dto.request;

public class UpdateRoomRequest {

    private String name;

    private Integer seats;

    public UpdateRoomRequest(String name, Integer seats) {
        this.name = name;
        this.seats = seats;
    }

    public String getName() {
        return name;
    }

    public Integer getSeats() {
        return seats;
    }

    public static UpdateRoomRequestBuilder newUpdateRoomRequest() {
        return new UpdateRoomRequestBuilder();
    }

    public static final class UpdateRoomRequestBuilder {
        private String name;
        private Integer seats;

        private UpdateRoomRequestBuilder() {
        }


        public UpdateRoomRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UpdateRoomRequestBuilder seats(Integer seats) {
            this.seats = seats;
            return this;
        }

        public UpdateRoomRequest build() {
            return new UpdateRoomRequest(name, seats);
        }
    }
}
