package br.com.victor.realmeet.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public class AllocationRequest {

    @NotNull
    private Long roomId;

    @NotBlank
    private String employeeName;

    @NotBlank
    @Email
    private String employeeEmail;

    @NotBlank
    private String subject;

    @NotNull
    private OffsetDateTime startAt;

    @NotNull
    private OffsetDateTime endAt;

    public AllocationRequest(Long roomId, String employeeName, String employeeEmail, String subject, OffsetDateTime startAt, OffsetDateTime endAt) {
        this.roomId = roomId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.subject = subject;
        this.startAt = startAt;
        this.endAt = endAt;
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
        private @NotNull Long roomId;
        private @NotBlank String employeeName;
        private @NotBlank @Email String employeeEmail;
        private @NotBlank String subject;
        private @NotNull OffsetDateTime startAt;
        private @NotNull OffsetDateTime endAt;

        private Builder() {
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

        public AllocationRequest build() {
            return new AllocationRequest(roomId, employeeName, employeeEmail, subject, startAt, endAt);
        }
    }
}
