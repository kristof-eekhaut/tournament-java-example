package be.eekhaut.kristof.tournament.tournament.domain;

import be.eekhaut.kristof.tournament.common.domain.ddd.AbstractAggregateRoot;
import be.eekhaut.kristof.tournament.tournament.domain.command.ChangeTournamentInformationCommand;
import be.eekhaut.kristof.tournament.tournament.domain.command.CreateTournamentCommand;
import be.eekhaut.kristof.tournament.tournament.domain.event.TournamentCreatedEvent;
import be.eekhaut.kristof.tournament.tournament.domain.event.TournamentInformationChangedEvent;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.hasText;

public class Tournament extends AbstractAggregateRoot<TournamentId> {

    private String name;
    private String description;
    private OrganizerId organizerId;
    private LocalDate startDate;

    public Tournament(TournamentId id, CreateTournamentCommand command) {
        this(Tournament.builder()
                .id(id)
                .name(command.getName())
                .description(command.getDescription())
                .organizerId(command.getOrganizerId())
                .startDate(command.getStartDate()));

        raise(TournamentCreatedEvent.builder()
                .id(getId())
                .name(name)
                .description(description)
                .organizerId(organizerId)
                .startDate(startDate)
                .build());
    }

    private Tournament(Builder builder) {
        super(builder.id);
        setName(builder.name);
        setDescription(builder.description);
        setOrganizerId(builder.organizerId);
        setStartDate(builder.startDate);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public OrganizerId getOrganizerId() {
        return organizerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void changeInformation(ChangeTournamentInformationCommand command) {
        setName(command.getName());
        setDescription(command.getDescription());

        raise(TournamentInformationChangedEvent.builder()
                .id(getId())
                .name(name)
                .description(description)
                .build());
    }

    private void setName(String name) {
        hasText(name, "Tournament name should not be empty!");
        this.name = name;
    }

    private void setDescription(String description) {
        hasText(description, "Tournament description should not be empty!");
        this.description = description;
    }

    private void setOrganizerId(OrganizerId organizerId) {
        this.organizerId = requireNonNull(organizerId);
    }

    private void setStartDate(LocalDate startDate) {
        this.startDate = requireNonNull(startDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", name)
                .append("description", description)
                .append("organizerId", organizerId)
                .append("startDate", startDate)
                .toString();
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

        private Builder() {
        }

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

        public Tournament build() {
            return new Tournament(this);
        }
    }
}
