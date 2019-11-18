package be.eekhaut.kristof.tournament.tournament.domain;

import be.eekhaut.kristof.tournament.common.domain.ddd.Identifier;
import be.eekhaut.kristof.tournament.common.domain.ddd.SingleValueObject;

public class TournamentId extends SingleValueObject<Long> implements Identifier {

    public static TournamentId tournamentId(Long value) {
        return new TournamentId(value);
    }

    public static TournamentId tournamentId(String value) {
        return new TournamentId(Long.parseLong(value));
    }

    private TournamentId(Long value) {
        super(value);
    }
}
