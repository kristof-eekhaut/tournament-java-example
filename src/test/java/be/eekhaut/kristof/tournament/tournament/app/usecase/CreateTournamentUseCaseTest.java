package be.eekhaut.kristof.tournament.tournament.app.usecase;

import be.eekhaut.kristof.tournament.tournament.domain.Tournament;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentId;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentRepository;
import be.eekhaut.kristof.tournament.tournament.domain.command.CreateTournamentCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static be.eekhaut.kristof.tournament.tournament.domain.OrganizerId.organizerId;
import static be.eekhaut.kristof.tournament.tournament.domain.TournamentAssertion.assertThatTournament;
import static be.eekhaut.kristof.tournament.tournament.domain.TournamentAssertion.fields;
import static be.eekhaut.kristof.tournament.tournament.domain.TournamentId.tournamentId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTournamentUseCaseTest {

    private CreateTournamentUseCase useCase;

    @Mock
    private TournamentRepository tournamentRepository;

    @BeforeEach
    void setUp() {
        useCase = new CreateTournamentUseCase(tournamentRepository);
    }

    @Test
    void whenCreatingNewTournament_thenTournamentAggregateIsCreatedAndAddedToRepository() {
        // Given
        TournamentId newTournamentId = tournamentId(3L);
        when(tournamentRepository.nextId()).thenReturn(newTournamentId);

        // When
        CreateTournamentCommand command = CreateTournamentCommand.builder()
                .name("My Tournament")
                .description("My Tournament Description")
                .organizerId(organizerId(28L))
                .startDate(LocalDate.of(2020, 6, 22))
                .build();
        TournamentId id = useCase.execute(command);

        // Then
        assertThat(id).isEqualTo(newTournamentId);

        ArgumentCaptor<Tournament> tournamentCaptor = ArgumentCaptor.forClass(Tournament.class);
        verify(tournamentRepository).add(tournamentCaptor.capture());
        assertThatTournament(tournamentCaptor.getValue()).has(fields()
                .id(tournamentId(3L))
                .name("My Tournament")
                .description("My Tournament Description")
                .organizerId(organizerId(28L))
                .startDate(LocalDate.of(2020, 6, 22))
        );

        verifyNoMoreInteractions(tournamentRepository);
    }
}
