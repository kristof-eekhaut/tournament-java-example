package be.eekhaut.kristof.tournament.common.domain.ddd;

import java.util.List;

public interface AggregateRoot<ID extends Identifier> {

    ID getId();

    List<DomainEvent> getRegisteredEvents();

    void clearRegisteredEvents();
}
