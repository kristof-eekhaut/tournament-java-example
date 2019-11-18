package be.eekhaut.kristof.tournament.tournament.app.usecase;

import be.eekhaut.kristof.tournament.tournament.domain.Tournament;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentId;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentRepository;
import be.eekhaut.kristof.tournament.tournament.domain.command.ChangeTournamentInformationCommand;
import be.eekhaut.kristof.tournament.tournament.domain.exception.TournamentNotFoundException;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class ChangeTournamentInformationUseCase {

    private final TournamentRepository tournamentRepository;

    public ChangeTournamentInformationUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = requireNonNull(tournamentRepository);
    }

    public void execute(TournamentId tournamentId, ChangeTournamentInformationCommand command) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException(tournamentId));
        tournament.changeInformation(command);
        tournamentRepository.update(tournament);
    }
}
