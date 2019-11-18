package be.eekhaut.kristof.tournament.tournament.domain.command;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(builderClassName = "Builder")
@EqualsAndHashCode
@ToString
public class ChangeTournamentInformationCommand {

    private final String name;
    private final String description;
}
