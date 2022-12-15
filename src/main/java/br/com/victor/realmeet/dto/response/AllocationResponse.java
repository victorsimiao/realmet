package br.com.victor.realmeet.dto.response;

import java.time.OffsetDateTime;

public class AllocationResponse {

    private Long id;

    private Long roomId;

    private String employeeName;

    private String employeeEmail;

    private String subject;

    private OffsetDateTime startAt;

    private OffsetDateTime endAt;

    public AllocationResponse(Long id, Long roomId, String employeeName, String employeeEmail, String subject, OffsetDateTime startAt, OffsetDateTime endAt) {
        this.id = id;
        this.roomId = roomId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.subject = subject;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public Long getId() {
        return id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
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

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Long roomId;
        private String employeeName;
        private String employeeEmail;
        private String subject;
        private OffsetDateTime startAt;
        private OffsetDateTime endAt;

        private Builder() {
        }


        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder roomId(Long roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder employeeName(String employeeName) {
            this.employeeName = employeeName;
            return this;
        }

        public Builder employeeEmail(String employeeEmail) {
            this.employeeEmail = employeeEmail;
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

        public AllocationResponse build() {
            return new AllocationResponse(id, roomId, employeeName, employeeEmail, subject, startAt, endAt);
        }
    }
}
