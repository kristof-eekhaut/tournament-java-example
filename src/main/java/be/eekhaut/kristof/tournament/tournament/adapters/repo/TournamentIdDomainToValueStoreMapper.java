package be.eekhaut.kristof.tournament.tournament.adapters.repo;

import be.eekhaut.kristof.tournament.common.repo.keyvalue.DomainToValueStoreMapper;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentId;
import org.springframework.stereotype.Component;

import static be.eekhaut.kristof.tournament.tournament.domain.TournamentId.tournamentId;
import static java.util.Objects.requireNonNull;

@Component
public class TournamentIdDomainToValueStoreMapper implements DomainToValueStoreMapper<TournamentId, Long> {

    @Override
    public Long toValue(TournamentId tournamentId) {
        requireNonNull(tournamentId);
        return tournamentId.getValue();
    }

    @Override
    public TournamentId toDomain(Long value) {
        requireNonNull(value);
        return tournamentId(value);
    }
}
