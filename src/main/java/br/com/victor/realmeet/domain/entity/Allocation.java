package br.com.victor.realmeet.domain.entity;

import br.com.victor.realmeet.domain.model.Employee;
import br.com.victor.realmeet.util.DateUtils;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
public class Allocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Embedded
    private Employee employee;

    private String subject;

    private OffsetDateTime startAt;

    private OffsetDateTime endAt;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @Deprecated
    public Allocation() {
    }

    private Allocation(Builder builder) {
        this.id = builder.id;
        this.room = builder.room;
        this.employee = builder.employee;
        this.subject = builder.subject;
        this.startAt = builder.startAt;
        this.endAt = builder.endAt;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    @PrePersist
    public void prePersist() {
        if (Objects.isNull(createdAt)) {
            createdAt = DateUtils.now();
        }
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = DateUtils.now();
    }

    public Long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getSubject() {
        return subject;
    }

    public OffsetDateTime getStartAt() {
        return startAt;
    }

    public OffsetDateTime getEndAt() {
        return endAt;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Allocation that = (Allocation) o;
        return Objects.equals(id, that.id) && Objects.equals(room, that.room) && Objects.equals(employee, that.employee) && Objects.equals(subject, that.subject) && Objects.equals(startAt, that.startAt) && Objects.equals(endAt, that.endAt) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, room, employee, subject, startAt, endAt, createdAt, updatedAt);
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Room room;
        private Employee employee;
        private String subject;
        private OffsetDateTime startAt;
        private OffsetDateTime endAt;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder room(Room room) {
            this.room = room;
            return this;
        }

        public Builder employee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder startAt(OffsetDateTime startAt) {
            this.startAt = startAt;
            return this;
        }

        public Builder endAt(OffsetDateTime endAt) {
            this.endAt = endAt;
            return this;
        }

        public Builder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Allocation build() {
            return new Allocation(this);
        }
    }
}