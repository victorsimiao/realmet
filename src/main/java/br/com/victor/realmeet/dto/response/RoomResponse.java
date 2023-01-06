package br.com.victor.realmeet.dto.response;

public class RoomResponse {

    private Long id;

    private String name;

    private Integer seats;

    private Boolean active;

    public RoomResponse(Long id,String name, Integer seats, Boolean active) {
        this.id = id;
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

    public Long getId() {
        return id;
    }
}
