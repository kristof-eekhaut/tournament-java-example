package be.eekhaut.kristof.tournament.common.domain.ddd;

import java.util.List;
import java.util.Optional;

public interface Repository<A extends AggregateRoot<ID>, ID extends Identifier> {

    ID nextId();

    void add(A aggregate);

    void update(A aggregate);

    Optional<A> findById(ID id);

    List<A> findAll();
}
