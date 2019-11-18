package be.eekhaut.kristof.tournament.tournament.app;

import be.eekhaut.kristof.tournament.tournament.adapters.repo.TournamentKeyValueStoreImpl;
import be.eekhaut.kristof.tournament.tournament.api.TournamentView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static be.eekhaut.kristof.tournament.tournament.api.TournamentViewTestData.tournamentView;
import static org.assertj.core.api.Assertions.assertThat;

class TournamentQueryServiceImplTest {

    private TournamentQueryServiceImpl tournamentQueryServiceImpl;

    private TournamentKeyValueStore tournamentKeyValueStore;

    @BeforeEach
    void setUp() {
        tournamentKeyValueStore = new TournamentKeyValueStoreImpl();
        tournamentQueryServiceImpl = new TournamentQueryServiceImpl(tournamentKeyValueStore);
    }

    @Test
    void findById_givenStoredTournament_whenSearchingById_thenTournamentIsReturned() {
        TournamentView tournament = tournamentView(1).build();

        tournamentKeyValueStore.save(Long.parseLong(tournament.getId()), tournament);

        Optional<TournamentView> foundTournament = tournamentQueryServiceImpl.findById("1");

        assertThat(foundTournament).isPresent()
                .contains(tournament);
    }

    @Test
    void findById_givenStoredTournament_whenSearchingByAnotherId_thenTournamentIsNotReturned() {
        TournamentView tournament = tournamentView(1).build();

        tournamentKeyValueStore.save(Long.parseLong(tournament.getId()), tournament);

        Optional<TournamentView> foundTournament = tournamentQueryServiceImpl.findById("2");

        assertThat(foundTournament).isNotPresent();
    }
}