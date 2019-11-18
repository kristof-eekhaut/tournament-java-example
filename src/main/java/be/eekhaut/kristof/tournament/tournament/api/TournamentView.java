package be.eekhaut.kristof.tournament.tournament.api;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder(builderClassName = "Builder")
@EqualsAndHashCode
@ToString
public class TournamentView {

    private final String id;
    private final String name;
    private final String description;
    private final String organizerId;
    private final LocalDate startDate;
}
