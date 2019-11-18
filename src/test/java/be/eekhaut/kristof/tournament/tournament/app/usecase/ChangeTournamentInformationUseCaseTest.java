package be.eekhaut.kristof.tournament.tournament.app.usecase;

import be.eekhaut.kristof.tournament.tournament.domain.Tournament;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentId;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentRepository;
import be.eekhaut.kristof.tournament.tournament.domain.command.ChangeTournamentInformationCommand;
import be.eekhaut.kristof.tournament.tournament.domain.exception.TournamentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static be.eekhaut.kristof.tournament.tournament.domain.OrganizerId.organizerId;
import static be.eekhaut.kristof.tournament.tournament.domain.TournamentAssertion.assertThatTournament;
import static be.eekhaut.kristof.tournament.tournament.domain.TournamentAssertion.fields;
import static be.eekhaut.kristof.tournament.tournament.domain.TournamentId.tournamentId;
import static be.eekhaut.kristof.tournament.tournament.domain.TournamentTestData.tournament;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangeTournamentInformationUseCaseTest {

    private ChangeTournamentInformationUseCase useCase;

    @Mock
    private TournamentRepository tournamentRepository;

    @BeforeEach
    void setUp() {
        useCase = new ChangeTournamentInformationUseCase(tournamentRepository);
    }

    @Test
    void givenTournament_whenUpdatingTournamentInformation_thenTournamentAggregateIsChangedAndUpdatedInTheRepository() {
        // Given
        Tournament tournament = tournament(3).build();
        TournamentId tournamentId = tournament.getId();
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        // When
        ChangeTournamentInformationCommand command = ChangeTournamentInformationCommand.builder()
                .name("Updated Tournament")
                .description("Updated Tournament Description")
                .build();
        useCase.execute(tournamentId, command);

        // Then
        ArgumentCaptor<Tournament> tournamentCaptor = ArgumentCaptor.forClass(Tournament.class);
        verify(tournamentRepository).update(tournamentCaptor.capture());
        assertThatTournament(tournamentCaptor.getValue()).has(fields()
                .id(tournamentId(3L))
                .name("Updated Tournament")
                .description("Updated Tournament Description")
                .organizerId(organizerId(10L))
                .startDate(LocalDate.of(2020, 2, 22))
        );

        verifyNoMoreInteractions(tournamentRepository);
    }

    @Test
    void givenNoTournamentForId_whenUpdatingTournamentInformation_thenException() {
        // Given
        TournamentId tournamentId = tournamentId(3L);
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.empty());

        // When
        ChangeTournamentInformationCommand command = ChangeTournamentInformationCommand.builder()
                .name("Updated Tournament")
                .description("Updated Tournament Description")
                .build();
        assertThat(catchThrowable(() -> useCase.execute(tournamentId, command))).isInstanceOf(TournamentNotFoundException.class);

        verifyNoMoreInteractions(tournamentRepository);
    }
}
