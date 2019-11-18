package be.eekhaut.kristof.tournament.common.repo.keyvalue;

public interface DomainToValueStoreMapper<D, V> {

    V toValue(D domainObject);
    D toDomain(V value);
}
