package be.eekhaut.kristof.tournament.tournament.app;

import be.eekhaut.kristof.tournament.common.repo.keyvalue.KeyValueStore;
import be.eekhaut.kristof.tournament.tournament.api.TournamentView;

public interface TournamentKeyValueStore extends KeyValueStore<Long, TournamentView> {

}
