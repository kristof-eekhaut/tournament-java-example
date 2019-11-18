package be.eekhaut.kristof.tournament.tournament.domain;

import org.assertj.core.api.AbstractAssert;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class TournamentAssertion extends AbstractAssert<TournamentAssertion, Tournament> {

    TournamentAssertion(Tournament tournament) {
        super(tournament, TournamentAssertion.class);
    }

    public static TournamentAssertion assertThatTournament(Tournament tournament) {
        return new TournamentAssertion(tournament);
    }

    public void has(Fields fields) {
        isNotNull();
        assertThat(actual).hasFieldOrPropertyWithValue("id", fields.id);
        assertThat(actual).hasFieldOrPropertyWithValue("name", fields.name);
        assertThat(actual).hasFieldOrPropertyWithValue("description", fields.description);
        assertThat(actual).hasFieldOrPropertyWithValue("organizerId", fields.organizerId);
        assertThat(actual).hasFieldOrPropertyWithValue("startDate", fields.startDate);
    }

    public static Fields fields() {
        return new Fields();
    }

    public static Fields fieldsOfTournament(Tournament tournament) {
        return fields()
                .id(tournament.getId())
                .name(tournament.getName())
                .description(tournament.getDescription())
                .organizerId(tournament.getOrganizerId())
                .startDate(tournament.getStartDate());
    }

    public static final class Fields {

        private TournamentId id;
        private String name;
        private String description;
        private OrganizerId organizerId;
        private LocalDate startDate;

        public Fields id(TournamentId id) {
            this.id = id;
            return this;
        }

        public Fields name(String name) {
            this.name = name;
            return this;
        }

        public Fields description(String description) {
            this.description = description;
            return this;
        }

        public Fields organizerId(OrganizerId organizerId) {
            this.organizerId = organizerId;
            return this;
        }

        public Fields startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }
    }
}
