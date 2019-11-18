package be.eekhaut.kristof.tournament.test.contracts;

import be.eekhaut.kristof.tournament.TournamentApplication;
import be.eekhaut.kristof.tournament.tournament.api.TournamentCommandService;
import be.eekhaut.kristof.tournament.tournament.api.TournamentQueryService;
import be.eekhaut.kristof.tournament.tournament.api.TournamentView;
import be.eekhaut.kristof.tournament.tournament.domain.command.CreateTournamentCommand;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static be.eekhaut.kristof.tournament.tournament.api.TournamentViewTestData.tournamentView;
import static be.eekhaut.kristof.tournament.tournament.domain.TournamentId.tournamentId;
import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TournamentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class TournamentsBase {

    private static final TournamentView TOURNAMENT_5 = tournamentView(5).build();
    private static final TournamentView TOURNAMENT_9 = tournamentView(9).build();

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TournamentQueryService tournamentQueryService;
    @MockBean
    private TournamentCommandService tournamentCommandService;

    @BeforeEach
    void setUp() {

        when(tournamentQueryService.findAll())
                .thenReturn(newArrayList(
                        TOURNAMENT_5,
                        TOURNAMENT_9
                ));

        when(tournamentQueryService.findById("5"))
                .thenReturn(Optional.of(TOURNAMENT_5));

        when(tournamentCommandService.createTournament(any(CreateTournamentCommand.class)))
                .thenReturn(tournamentId("5"));

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .alwaysDo(print())
                .build();
        RestAssuredMockMvc.mockMvc(mockMvc);
    }
}
