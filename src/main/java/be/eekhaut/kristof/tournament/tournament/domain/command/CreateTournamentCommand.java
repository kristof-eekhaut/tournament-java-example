package be.eekhaut.kristof.tournament.tournament.domain.command;

import be.eekhaut.kristof.tournament.tournament.domain.OrganizerId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder(builderClassName = "Builder")
@EqualsAndHashCode
@ToString
public class CreateTournamentCommand {

    private final String name;
    private final String description;
    private final OrganizerId organizerId;
    private final LocalDate startDate;
}
