package be.eekhaut.kristof.tournament.tournament.api;

import java.time.LocalDate;

public class TournamentViewTestData {

    public static TournamentView.Builder tournamentView(int id) {
        return TournamentView.builder()
                .id(""+id)
                .name("Tournament-" + id)
                .description("Description for Tournament-" + id)
                .organizerId("10")
                .startDate(LocalDate.of(2020, 2, 22));
    }
}