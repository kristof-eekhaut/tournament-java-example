package be.eekhaut.kristof.tournament.tournament.domain.event;

import be.eekhaut.kristof.tournament.common.domain.ddd.DomainEvent;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentId;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class TournamentInformationChangedEvent extends DomainEvent {

    private final String name;
    private final String description;

    private TournamentInformationChangedEvent(Builder builder) {
        super(builder.id);
        name = builder.name;
        description = builder.description;
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

        public TournamentInformationChangedEvent build() {
            return new TournamentInformationChangedEvent(this);
        }
    }
}
