package be.eekhaut.kristof.tournament.tournament.domain;

import java.time.LocalDate;

import static be.eekhaut.kristof.tournament.tournament.domain.OrganizerId.organizerId;
import static be.eekhaut.kristof.tournament.tournament.domain.TournamentId.tournamentId;

public class TournamentTestData {

    public static Tournament.Builder tournament(int id) {
        return Tournament.builder()
                .id(tournamentId((long) id))
                .name("Tournament-" + id)
                .description("Description for Tournament-" + id)
                .organizerId(organizerId(10L))
                .startDate(LocalDate.of(2020, 2, 22));
    }
}