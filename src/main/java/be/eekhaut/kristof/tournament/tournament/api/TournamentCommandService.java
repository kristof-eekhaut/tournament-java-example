package be.eekhaut.kristof.tournament.tournament.api;

import be.eekhaut.kristof.tournament.tournament.domain.TournamentId;
import be.eekhaut.kristof.tournament.tournament.domain.command.ChangeTournamentInformationCommand;
import be.eekhaut.kristof.tournament.tournament.domain.command.CreateTournamentCommand;

public interface TournamentCommandService {

    TournamentId createTournament(CreateTournamentCommand command);

    void changeTournamentInformation(TournamentId tournamentId, ChangeTournamentInformationCommand command);
}
