package be.eekhaut.kristof.tournament.tournament.adapters.repo;

import be.eekhaut.kristof.tournament.common.repo.keyvalue.InMemoryKeyValueStore;
import be.eekhaut.kristof.tournament.tournament.api.TournamentView;
import be.eekhaut.kristof.tournament.tournament.app.TournamentKeyValueStore;
import org.springframework.stereotype.Component;

@Component
public class TournamentKeyValueStoreImpl extends InMemoryKeyValueStore<Long, TournamentView> implements TournamentKeyValueStore {

}
