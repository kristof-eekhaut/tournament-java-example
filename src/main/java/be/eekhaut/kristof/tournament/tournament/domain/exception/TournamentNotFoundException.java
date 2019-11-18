package be.eekhaut.kristof.tournament.tournament.domain.exception;

import be.eekhaut.kristof.tournament.tournament.domain.TournamentId;

public class TournamentNotFoundException extends RuntimeException {

    public TournamentNotFoundException(TournamentId tournamentId) {
        super("No tournament found for id: " + tournamentId);
    }
}
