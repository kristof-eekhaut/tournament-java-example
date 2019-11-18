package be.eekhaut.kristof.tournament.tournament.api;

import java.util.List;
import java.util.Optional;

public interface TournamentQueryService {

    Optional<TournamentView> findById(String id);

    List<TournamentView> findAll();
}
