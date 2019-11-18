package be.eekhaut.kristof.tournament.tournament.domain;

import be.eekhaut.kristof.tournament.tournament.domain.command.ChangeTournamentInformationCommand;
import be.eekhaut.kristof.tournament.tournament.domain.command.CreateTournamentCommand;
import be.eekhaut.kristof.tournament.tournament.domain.event.TournamentCreatedEvent;
import be.eekhaut.kristof.tournament.tournament.domain.event.TournamentInformationChangedEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static be.eekhaut.kristof.tournament.tournament.domain.OrganizerId.organizerId;
import static be.eekhaut.kristof.tournament.tournament.domain.TournamentAssertion.assertThatTournament;
import static be.eekhaut.kristof.tournament.tournament.domain.TournamentAssertion.fieldsOfTournament;
import static be.eekhaut.kristof.tournament.tournament.domain.TournamentId.tournamentId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class TournamentTest {

    private static final Long TOURNAMENT_ID_VALUE = 3L;
    private static final TournamentId TOURNAMENT_ID = tournamentId(TOURNAMENT_ID_VALUE);
    private static final String TOURNAMENT_NAME = "Tournament-1";
    private static final String TOURNAMENT_DESCRIPTION = "My description";
    private static final OrganizerId ORGANIZER_ID = organizerId(5L);
    private static final LocalDate TOURNAMENT_START_DATE = LocalDate.of(2019, 12, 1);

    @Test
    void create() {
        // When
        CreateTournamentCommand command = createTournamentCommandBuilder().build();
        Tournament tournament = new Tournament(TOURNAMENT_ID, command);

        // Then
        assertThatTournament(tournament).has(expectedFields());

        // And
        assertThat(tournament.getRegisteredEvents()).hasSize(1);
        assertThat(tournament.getRegisteredEvents().get(0)).isInstanceOf(TournamentCreatedEvent.class);
        TournamentCreatedEvent event = (TournamentCreatedEvent) tournament.getRegisteredEvents().get(0);
        assertThat(event.getId()).isEqualTo(TOURNAMENT_ID);
        assertThat(event.getName()).isEqualTo(TOURNAMENT_NAME);
        assertThat(event.getDescription()).isEqualTo(TOURNAMENT_DESCRIPTION);
        assertThat(event.getOrganizerId()).isEqualTo(ORGANIZER_ID);
        assertThat(event.getStartDate()).isEqualTo(TOURNAMENT_START_DATE);
    }

    @Test
    void create_whenCreatingTournamentWithoutName_thenException() {
        assertThat(catchThrowable(() -> new Tournament(TOURNAMENT_ID, createTournamentCommandBuilder()
                .name("")
                .build()))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void create_whenCreatingTournamentWithoutDescription_thenException() {
        assertThat(catchThrowable(() -> new Tournament(TOURNAMENT_ID, createTournamentCommandBuilder()
                .description("")
                .build()))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void create_whenCreatingTournamentWithoutOrganizer_thenException() {
        assertThat(catchThrowable(() -> new Tournament(TOURNAMENT_ID, createTournamentCommandBuilder()
                .organizerId(null)
                .build()))).isInstanceOf(NullPointerException.class);
    }

    @Test
    void create_whenCreatingTournamentWithoutStartDate_thenException() {
        assertThat(catchThrowable(() -> new Tournament(TOURNAMENT_ID, createTournamentCommandBuilder()
                .startDate(null)
                .build()))).isInstanceOf(NullPointerException.class);
    }

    @Test
    void changeTournamentInformation() {
        // Given
        Tournament tournament = tournament().build();

        // When
        String newName = "Updated Tournament Name";
        String newDescription = "Updated Tournament Description";
        ChangeTournamentInformationCommand command = ChangeTournamentInformationCommand.builder()
                .name(newName)
                .description(newDescription)
                .build();
        tournament.changeInformation(command);

        // Then
        assertThatTournament(tournament).has(expectedFields()
                .name(newName)
                .description(newDescription)
        );

        // And
        assertThat(tournament.getRegisteredEvents()).hasSize(1);
        assertThat(tournament.getRegisteredEvents().get(0)).isInstanceOf(TournamentInformationChangedEvent.class);
        TournamentInformationChangedEvent event = (TournamentInformationChangedEvent) tournament.getRegisteredEvents().get(0);
        assertThat(event.getId()).isEqualTo(TOURNAMENT_ID);
        assertThat(event.getName()).isEqualTo(newName);
        assertThat(event.getDescription()).isEqualTo(newDescription);
    }

    @Test
    void changeTournamentInformation_whenNameIsMissing_thenException() {
        // Given
        Tournament tournament = tournament().build();

        // When
        assertThat(catchThrowable(() -> tournament.changeInformation(ChangeTournamentInformationCommand.builder()
                .name("")
                .description("abc")
                .build()))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void changeTournamentInformation_whenDescriptionIsMissing_thenException() {
        // Given
        Tournament tournament = tournament().build();

        // When
        assertThat(catchThrowable(() -> tournament.changeInformation(ChangeTournamentInformationCommand.builder()
                .name("tournament-name")
                .description("")
                .build()))).isInstanceOf(IllegalArgumentException.class);
    }

    private Tournament.Builder tournament() {
        return Tournament.builder()
                .id(TOURNAMENT_ID)
                .name(TOURNAMENT_NAME)
                .description(TOURNAMENT_DESCRIPTION)
                .organizerId(ORGANIZER_ID)
                .startDate(TOURNAMENT_START_DATE);
    }

    private CreateTournamentCommand.Builder createTournamentCommandBuilder() {
        return CreateTournamentCommand.builder()
                .name(TOURNAMENT_NAME)
                .description(TOURNAMENT_DESCRIPTION)
                .organizerId(ORGANIZER_ID)
                .startDate(TOURNAMENT_START_DATE);
    }

    private TournamentAssertion.Fields expectedFields() {
        return fieldsOfTournament(tournament().build());
    }
}
