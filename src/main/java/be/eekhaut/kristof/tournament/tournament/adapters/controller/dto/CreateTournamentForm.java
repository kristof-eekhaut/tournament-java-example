package be.eekhaut.kristof.tournament.tournament.adapters.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder(builderClassName = "Builder")
@EqualsAndHashCode
@ToString
public class CreateTournamentForm {

    private final String name;
    private final String description;
    private final String organizerId;
    private final LocalDate startDate;

    @JsonCreator
    public CreateTournamentForm(
            @JsonProperty("name") final String name,
            @JsonProperty("description") final String description,
            @JsonProperty("organizerId") final String organizerId,
            @JsonProperty("startDate") final LocalDate startDate
    ) {
        this.name = name;
        this.description = description;
        this.organizerId = organizerId;
        this.startDate = startDate;
    }
}
