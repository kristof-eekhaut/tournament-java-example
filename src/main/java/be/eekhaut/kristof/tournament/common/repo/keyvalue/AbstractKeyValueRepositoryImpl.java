package be.eekhaut.kristof.tournament.common.repo.keyvalue;

import be.eekhaut.kristof.tournament.common.domain.ddd.AggregateRoot;
import be.eekhaut.kristof.tournament.common.domain.ddd.Identifier;
import be.eekhaut.kristof.tournament.common.domain.ddd.Repository;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public abstract class AbstractKeyValueRepositoryImpl<A extends AggregateRoot<ID>, ID extends Identifier, K, V> implements Repository<A, ID> {

    private final KeyValueStore<K, V> keyValueStore;
    private final DomainToValueStoreMapper<A, V> aggregateMapper;
    private final DomainToValueStoreMapper<ID, K> idMapper;
    private final ApplicationEventPublisher eventPublisher;

    protected AbstractKeyValueRepositoryImpl(KeyValueStore<K, V> keyValueStore,
                                             DomainToValueStoreMapper<A, V> aggregateMapper,
                                             DomainToValueStoreMapper<ID, K> idMapper,
                                             ApplicationEventPublisher eventPublisher) {
        this.keyValueStore = requireNonNull(keyValueStore);
        this.aggregateMapper = requireNonNull(aggregateMapper);
        this.idMapper = requireNonNull(idMapper);
        this.eventPublisher = requireNonNull(eventPublisher);
    }

    @Override
    public void add(A aggregate) {
        addOrUpdate(aggregate);
    }

    @Override
    public void update(A aggregate) {
        addOrUpdate(aggregate);
    }

    @Override
    public List<A> findAll() {
        return keyValueStore.findAll().stream()
                .map(aggregateMapper::toDomain)
                .collect(toList());
    }

    @Override
    public Optional<A> findById(ID id) {
        requireNonNull(id);
        K key = idMapper.toValue(id);
        return keyValueStore.findByKey(key)
                .map(aggregateMapper::toDomain);
    }

    private void addOrUpdate(A aggregate) {
        requireNonNull(aggregate);

        K key = idMapper.toValue(aggregate.getId());
        V value = aggregateMapper.toValue(aggregate);
        keyValueStore.save(key, value);

        publishRegisteredEvents(aggregate);
    }

    private void publishRegisteredEvents(AggregateRoot<?> aggregateRoot) {
        aggregateRoot.getRegisteredEvents()
                .forEach(eventPublisher::publishEvent);
        aggregateRoot.clearRegisteredEvents();
    }
}
