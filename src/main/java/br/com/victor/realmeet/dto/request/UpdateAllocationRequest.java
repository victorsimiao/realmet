package br.com.victor.realmeet.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public class UpdateAllocationRequest {

    @NotBlank
    private String subject;

    @NotNull
    private OffsetDateTime startAt;

    @NotNull
    private OffsetDateTime endAt;

    public UpdateAllocationRequest(String subject, OffsetDateTime startAt, OffsetDateTime endAt) {
        this.subject = subject;
        this.startAt = startAt;
        this.endAt = endAt;
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
        private @NotBlank String subject;
        private @NotNull OffsetDateTime startAt;
        private @NotNull OffsetDateTime endAt;

        private Builder() {
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

        public UpdateAllocationRequest build() {
            return new UpdateAllocationRequest(subject, startAt, endAt);
        }
    }
}
