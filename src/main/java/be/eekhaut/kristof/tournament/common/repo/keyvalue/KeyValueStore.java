package be.eekhaut.kristof.tournament.common.repo.keyvalue;

import java.util.List;
import java.util.Optional;

public interface KeyValueStore<K, V> {

    void save(K key, V value);

    List<V> findAll();

    Optional<V> findByKey(K key);
}
