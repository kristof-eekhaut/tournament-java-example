package be.eekhaut.kristof.tournament.tournament.adapters.controller.dto;

import be.eekhaut.kristof.tournament.tournament.adapters.controller.TournamentController;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
@Relation(itemRelation = "tournament", collectionRelation = "tournaments")
public class TournamentRepresentation extends RepresentationModel<TournamentRepresentation> {

    private final String id;
    private final String name;
    private final String description;
    private final String organizerId;
    private final LocalDate startDate;

    private TournamentRepresentation(Builder builder) {
        super(linkTo(methodOn(TournamentController.class).getTournament(builder.id)).withSelfRel());

        id = builder.id;
        name = builder.name;
        description = builder.description;
        organizerId = builder.organizerId;
        startDate = builder.startDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private String description;
        private String organizerId;
        private LocalDate startDate;

        private Builder() {
        }

        public Builder id(String id) {
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

        public Builder organizerId(String organizerId) {
            this.organizerId = organizerId;
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public TournamentRepresentation build() {
            return new TournamentRepresentation(this);
        }
    }
}
