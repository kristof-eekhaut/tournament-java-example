package be.eekhaut.kristof.tournament.tournament.domain.event;

import be.eekhaut.kristof.tournament.common.domain.ddd.DomainEvent;
import be.eekhaut.kristof.tournament.tournament.domain.OrganizerId;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentId;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString(callSuper = true)
public class TournamentCreatedEvent extends DomainEvent {

    private final String name;
    private final String description;
    private final OrganizerId organizerId;
    private final LocalDate startDate;

    private TournamentCreatedEvent(Builder builder) {
        super(builder.id);
        name = builder.name;
        description = builder.description;
        organizerId = builder.organizerId;
        startDate = builder.startDate;
    }

    public TournamentId getId() {
        return (TournamentId) getSource();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private TournamentId id;
        private String name;
        private String description;
        private OrganizerId organizerId;
        private LocalDate startDate;

        private Builder() {}

        public Builder id(TournamentId id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder organizerId(OrganizerId organizerId) {
            this.organizerId = organizerId;
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public TournamentCreatedEvent build() {
            return new TournamentCreatedEvent(this);
        }
    }
}
