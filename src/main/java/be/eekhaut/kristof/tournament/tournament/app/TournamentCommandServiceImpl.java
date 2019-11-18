package be.eekhaut.kristof.tournament.tournament.app;

import be.eekhaut.kristof.tournament.tournament.api.TournamentCommandService;
import be.eekhaut.kristof.tournament.tournament.app.usecase.ChangeTournamentInformationUseCase;
import be.eekhaut.kristof.tournament.tournament.app.usecase.CreateTournamentUseCase;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentId;
import be.eekhaut.kristof.tournament.tournament.domain.command.ChangeTournamentInformationCommand;
import be.eekhaut.kristof.tournament.tournament.domain.command.CreateTournamentCommand;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
//@Transactional // TODO: add when datasource is configured
public class TournamentCommandServiceImpl implements TournamentCommandService {

    private CreateTournamentUseCase createTournamentUseCase;

    private ChangeTournamentInformationUseCase changeTournamentInformationUseCase;

    private TournamentCommandServiceImpl(
            CreateTournamentUseCase createTournamentUseCase,
            ChangeTournamentInformationUseCase changeTournamentInformationUseCase
    ) {
        this.createTournamentUseCase = requireNonNull(createTournamentUseCase);
        this.changeTournamentInformationUseCase = requireNonNull(changeTournamentInformationUseCase);
    }

    @Override
    public TournamentId createTournament(CreateTournamentCommand createTournament) {
        return createTournamentUseCase.execute(createTournament);
    }

    @Override
    public void changeTournamentInformation(TournamentId tournamentId, ChangeTournamentInformationCommand command) {
        changeTournamentInformationUseCase.execute(tournamentId, command);
    }
}
