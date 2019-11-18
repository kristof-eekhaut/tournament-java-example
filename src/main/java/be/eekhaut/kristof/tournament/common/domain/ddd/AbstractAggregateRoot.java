package be.eekhaut.kristof.tournament.common.domain.ddd;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public abstract class AbstractAggregateRoot<ID extends Identifier> implements AggregateRoot<ID> {

    private final ID id;
    private final List<DomainEvent> registeredEvents = new ArrayList<>();

    protected AbstractAggregateRoot(ID id) {
        this.id = requireNonNull(id);
    }

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public List<DomainEvent> getRegisteredEvents() {
        return newArrayList(registeredEvents);
    }

    @Override
    public void clearRegisteredEvents() {
        registeredEvents.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AbstractAggregateRoot that = (AbstractAggregateRoot) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }

    protected void raise(DomainEvent domainEvent) {
        registeredEvents.add(domainEvent);
    }
}
