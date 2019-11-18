package be.eekhaut.kristof.tournament.common.repo.keyvalue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Objects.requireNonNull;

public class InMemoryKeyValueStore<K, V> implements KeyValueStore<K, V> {

    private final Map<K, V> aggregates = new HashMap<>();

    @Override
    public void save(K key, V value) {
        aggregates.put(key, value);
    }

    @Override
    public List<V> findAll() {
        return newArrayList(aggregates.values());
    }

    @Override
    public Optional<V> findByKey(K key) {
        requireNonNull(key);
        return Optional.ofNullable(aggregates.get(key));
    }
}
