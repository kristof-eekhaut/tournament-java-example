package be.eekhaut.kristof.tournament.tournament.adapters.controller;

import be.eekhaut.kristof.tournament.tournament.adapters.controller.dto.ChangeTournamentInformationForm;
import be.eekhaut.kristof.tournament.tournament.adapters.controller.dto.CreateTournamentForm;
import be.eekhaut.kristof.tournament.tournament.adapters.controller.dto.TournamentRepresentation;
import be.eekhaut.kristof.tournament.tournament.adapters.controller.dto.TournamentRepresentationAssembler;
import be.eekhaut.kristof.tournament.tournament.api.TournamentCommandService;
import be.eekhaut.kristof.tournament.tournament.api.TournamentQueryService;
import be.eekhaut.kristof.tournament.tournament.api.TournamentView;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentId;
import be.eekhaut.kristof.tournament.tournament.domain.command.ChangeTournamentInformationCommand;
import be.eekhaut.kristof.tournament.tournament.domain.command.CreateTournamentCommand;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static be.eekhaut.kristof.tournament.tournament.domain.OrganizerId.organizerId;
import static be.eekhaut.kristof.tournament.tournament.domain.TournamentId.tournamentId;
import static java.util.Objects.requireNonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(value = "/tournaments", produces = MediaTypes.HAL_JSON_VALUE)
public class TournamentController {

    private TournamentQueryService tournamentQueryService;
    private TournamentCommandService tournamentCommandService;
    private TournamentRepresentationAssembler tournamentRepresentationAssembler;

    public TournamentController(TournamentQueryService tournamentQueryService,
                                TournamentCommandService tournamentCommandService,
                                TournamentRepresentationAssembler tournamentRepresentationAssembler) {
        this.tournamentQueryService = requireNonNull(tournamentQueryService);
        this.tournamentCommandService = requireNonNull(tournamentCommandService);
        this.tournamentRepresentationAssembler = requireNonNull(tournamentRepresentationAssembler);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<TournamentRepresentation>> getTournaments() {
        List<TournamentView> tournaments = tournamentQueryService.findAll();
        return ok()
                .body(tournamentRepresentationAssembler.toCollectionModel(tournaments)
                        .add(linkTo(methodOn(TournamentController.class).getTournaments()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentRepresentation> getTournament(@PathVariable("id") String tournamentId) {
        Optional<TournamentView> tournament = tournamentQueryService.findById(tournamentId);

        return tournament
                .map(tournamentView -> ok().body(tournamentRepresentationAssembler.toModel(tournamentView)))
                .orElseGet(() -> notFound().build());

    }

    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> createTournament(@RequestBody CreateTournamentForm form) {
        TournamentId id = tournamentCommandService.createTournament(CreateTournamentCommand.builder()
                .name(form.getName())
                .description(form.getDescription())
                .organizerId(organizerId(form.getOrganizerId()))
                .startDate(form.getStartDate())
                .build());

        return created(linkTo(methodOn(TournamentController.class).getTournament(id.toString())).toUri()).build();
    }

    @PatchMapping(value = "/{id}/change-information", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> changeTournamentInformation(
            @PathVariable("id") String tournamentId,
            @RequestBody ChangeTournamentInformationForm form) {
        tournamentCommandService.changeTournamentInformation(tournamentId(tournamentId), ChangeTournamentInformationCommand.builder()
                .name(form.getName())
                .description(form.getDescription())
                .build());

        return ok().build();
    }
}
