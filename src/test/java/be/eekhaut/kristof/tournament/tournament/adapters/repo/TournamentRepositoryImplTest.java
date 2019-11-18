package be.eekhaut.kristof.tournament.tournament.adapters.repo;

import be.eekhaut.kristof.tournament.tournament.app.TournamentKeyValueStore;
import be.eekhaut.kristof.tournament.tournament.domain.Tournament;
import be.eekhaut.kristof.tournament.tournament.domain.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static be.eekhaut.kristof.tournament.tournament.domain.TournamentTestData.tournament;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TournamentRepositoryImplTest {

    private TournamentRepository tournamentRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private TournamentKeyValueStore keyValueStore;

    @BeforeEach
    void setUp() {
        keyValueStore = new TournamentKeyValueStoreImpl();
        tournamentRepository = new TournamentRepositoryImpl(
                keyValueStore,
                new TournamentDomainToValueStoreMapper(),
                new TournamentIdDomainToValueStoreMapper(),
                eventPublisher);
    }

    @Test
    void add_whenAddingTournament_thenICanFindIt() {

        Tournament tournament = tournament(1).build();
        tournamentRepository.add(tournament);

        List<Tournament> persistedTournaments = tournamentRepository.findAll();

        assertThat(persistedTournaments).containsExactlyInAnyOrder(tournament);
    }
}
