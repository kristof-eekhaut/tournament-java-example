package be.eekhaut.kristof.tournament.tournament.adapters.repo;

import be.eekhaut.kristof.tournament.common.repo.keyvalue.AbstractKeyValueRepositoryImpl;
import be.eekhaut.kristof.tournament.tournament.api.TournamentView;
import be.eekhaut.kristof.tournament.tournament.app.TournamentKeyValueStore;
import be.eekhaut.kristof.tournament.tournament.domain.Tournament;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentId;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import static be.eekhaut.kristof.tournament.tournament.domain.TournamentId.tournamentId;

@Component
public class TournamentRepositoryImpl extends AbstractKeyValueRepositoryImpl<Tournament, TournamentId, Long, TournamentView> implements TournamentRepository {

    private Long nextId = 1L;

    public TournamentRepositoryImpl(TournamentKeyValueStore keyValueStore,
                                    TournamentDomainToValueStoreMapper aggregateMapper,
                                    TournamentIdDomainToValueStoreMapper idMapper,
                                    ApplicationEventPublisher eventPublisher) {
        super(keyValueStore, aggregateMapper, idMapper, eventPublisher);
    }

    @Override
    public TournamentId nextId() {
        return tournamentId(nextId++);
    }
}
