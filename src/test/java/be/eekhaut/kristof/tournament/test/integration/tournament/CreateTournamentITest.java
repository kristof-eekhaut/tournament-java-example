package be.eekhaut.kristof.tournament.test.integration.tournament;

import be.eekhaut.kristof.tournament.tournament.adapters.controller.dto.CreateTournamentForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.hamcrest.Matchers.matchesRegex;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateTournamentITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenCreatingTournament_thenICanFindIt() throws Exception {

        CreateTournamentForm form = CreateTournamentForm.builder()
                .name("New Tournament")
                .description("Description of the tournament")
                .organizerId("18")
                .startDate(LocalDate.of(2020, 3, 19))
                .build();

        MvcResult result = mockMvc.perform(post("/tournaments")
                .contentType(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, matchesRegex("^http://localhost/tournaments/[0-9]+$")))
                .andReturn();

        String createdTournamentLocation = result.getResponse().getHeader(HttpHeaders.LOCATION);
        mockMvc.perform(get(createdTournamentLocation))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Tournament"))
                .andExpect(jsonPath("$.description").value("Description of the tournament"))
                .andExpect(jsonPath("$.organizerId").value("18"))
                .andExpect(jsonPath("$.startDate").value("2020-03-19"));
    }
}
