package be.eekhaut.kristof.tournament.tournament.app.usecase;

import be.eekhaut.kristof.tournament.tournament.domain.Tournament;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentId;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentRepository;
import be.eekhaut.kristof.tournament.tournament.domain.command.CreateTournamentCommand;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class CreateTournamentUseCase {

    private final TournamentRepository tournamentRepository;

    public CreateTournamentUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = requireNonNull(tournamentRepository);
    }

    public TournamentId execute(CreateTournamentCommand command) {
        TournamentId tournamentId = tournamentRepository.nextId();
        Tournament tournament = new Tournament(tournamentId, command);
        tournamentRepository.add(tournament);
        return tournament.getId();
    }
}
