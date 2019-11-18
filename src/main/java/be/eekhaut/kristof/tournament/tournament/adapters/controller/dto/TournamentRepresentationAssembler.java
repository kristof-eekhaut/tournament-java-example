package be.eekhaut.kristof.tournament.tournament.adapters.controller.dto;

import be.eekhaut.kristof.tournament.tournament.api.TournamentView;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class TournamentRepresentationAssembler implements RepresentationModelAssembler<TournamentView, TournamentRepresentation> {

    @Override
    public TournamentRepresentation toModel(TournamentView entity) {
        return TournamentRepresentation.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .organizerId(entity.getOrganizerId())
                .startDate(entity.getStartDate())
                .build();
    }
}
