package br.com.victor.realmeet.domain.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;

@Entity
public class Room {
    public static final List<String> SORTABLE_FIELDS = List.of("name","seats");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer seats;

    private Boolean active;

    @Deprecated
    public Room() {
    }

    private Room(Long id, String name, Integer seats, Boolean active) {
        this.id = id;
        this.name = name;
        this.seats = seats;
        this.active = active;
    }

    @PrePersist
    public void prePersist() {
        if (isNull(active)) {
            active = true;
        }
    }


    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(name, room.name) && Objects.equals(seats, room.seats) && Objects.equals(active, room.active);
    }

    @Override
    public int hashCode() {
        return hash(id, name, seats, active);
    }

    public static RoomBuilder newBuilder() {
        return new RoomBuilder();
    }

    public static final class RoomBuilder {
        private Long id;
        private String name;
        private Integer seats;
        private Boolean active;

        private RoomBuilder() {
        }

        public RoomBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RoomBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RoomBuilder seats(Integer seats) {
            this.seats = seats;
            return this;
        }

        public RoomBuilder active(Boolean active) {
            this.active = active;
            return this;
        }

        public Room build() {
            return new Room(id, name, seats, active);
        }
    }
}
