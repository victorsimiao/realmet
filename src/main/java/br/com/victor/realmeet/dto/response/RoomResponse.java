package br.com.victor.realmeet.dto.response;

public class RoomResponse {

    private String name;

    private Integer seats;

    private Boolean active;

    public RoomResponse(String name, Integer seats, Boolean active) {
        this.name = name;
        this.seats = seats;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public Integer getSeats() {
        return seats;
    }

    public Boolean getActive() {
        return active;
    }
}
