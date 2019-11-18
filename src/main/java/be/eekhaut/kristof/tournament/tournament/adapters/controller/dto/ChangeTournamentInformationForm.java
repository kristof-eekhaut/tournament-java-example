package be.eekhaut.kristof.tournament.tournament.adapters.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(builderClassName = "Builder")
@EqualsAndHashCode
@ToString
public class ChangeTournamentInformationForm {

    private final String name;
    private final String description;

    @JsonCreator
    public ChangeTournamentInformationForm(
            @JsonProperty("name") final String name,
            @JsonProperty("description") final String description
    ) {
        this.name = name;
        this.description = description;
    }
}
