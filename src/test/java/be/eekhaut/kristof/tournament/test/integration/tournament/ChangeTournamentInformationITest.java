package be.eekhaut.kristof.tournament.test.integration.tournament;

import be.eekhaut.kristof.tournament.tournament.adapters.controller.dto.ChangeTournamentInformationForm;
import be.eekhaut.kristof.tournament.tournament.app.TournamentKeyValueStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static be.eekhaut.kristof.tournament.tournament.api.TournamentViewTestData.tournamentView;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChangeTournamentInformationITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TournamentKeyValueStore tournamentKeyValueStore;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenCreatingTournament_thenICanFindIt() throws Exception {

        tournamentKeyValueStore.save(5L, tournamentView(5).build());

        ChangeTournamentInformationForm form = ChangeTournamentInformationForm.builder()
                .name("Updated Tournament")
                .description("Updated Description of the tournament")
                .build();

        mockMvc.perform(patch("/tournaments/5/change-information")
                .contentType(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/tournaments/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("5"))
                .andExpect(jsonPath("$.name").value("Updated Tournament"))
                .andExpect(jsonPath("$.description").value("Updated Description of the tournament"))
                .andExpect(jsonPath("$.organizerId").value("10"))
                .andExpect(jsonPath("$.startDate").value("2020-02-22"));
    }
}
