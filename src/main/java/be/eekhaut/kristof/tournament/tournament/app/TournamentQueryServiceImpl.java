package be.eekhaut.kristof.tournament.tournament.app;

import be.eekhaut.kristof.tournament.tournament.api.TournamentQueryService;
import be.eekhaut.kristof.tournament.tournament.api.TournamentView;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.Long.parseLong;
import static java.util.Objects.requireNonNull;

@Service
public class TournamentQueryServiceImpl implements TournamentQueryService {

    private final TournamentKeyValueStore tournamentKeyValueStore;

    public TournamentQueryServiceImpl(TournamentKeyValueStore tournamentKeyValueStore) {
        this.tournamentKeyValueStore = requireNonNull(tournamentKeyValueStore);
    }

    @Override
    public Optional<TournamentView> findById(String id) {
        requireNonNull(id);
        return tournamentKeyValueStore.findByKey(parseLong(id));
    }

    @Override
    public List<TournamentView> findAll() {
        return tournamentKeyValueStore.findAll();
    }
}
