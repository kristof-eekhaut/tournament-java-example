package be.eekhaut.kristof.tournament.tournament.adapters.repo;

import be.eekhaut.kristof.tournament.common.repo.keyvalue.DomainToValueStoreMapper;
import be.eekhaut.kristof.tournament.tournament.api.TournamentView;
import be.eekhaut.kristof.tournament.tournament.domain.Tournament;
import org.springframework.stereotype.Component;

import static be.eekhaut.kristof.tournament.tournament.domain.OrganizerId.organizerId;
import static be.eekhaut.kristof.tournament.tournament.domain.TournamentId.tournamentId;
import static java.util.Objects.requireNonNull;

@Component
public class TournamentDomainToValueStoreMapper implements DomainToValueStoreMapper<Tournament, TournamentView> {

    @Override
    public TournamentView toValue(Tournament tournament) {
        requireNonNull(tournament);
        return TournamentView.builder()
                .id(tournament.getId().toString())
                .name(tournament.getName())
                .description(tournament.getDescription())
                .organizerId(tournament.getOrganizerId().toString())
                .startDate(tournament.getStartDate())
                .build();
    }

    @Override
    public Tournament toDomain(TournamentView value) {
        requireNonNull(value);
        return Tournament.builder()
                .id(tournamentId(value.getId()))
                .name(value.getName())
                .description(value.getDescription())
                .organizerId(organizerId(value.getOrganizerId()))
                .startDate(value.getStartDate())
                .build();
    }
}
