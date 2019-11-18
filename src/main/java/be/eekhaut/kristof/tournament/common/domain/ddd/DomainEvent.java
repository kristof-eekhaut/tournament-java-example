package be.eekhaut.kristof.tournament.common.domain.ddd;

import org.springframework.context.ApplicationEvent;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public abstract class DomainEvent extends ApplicationEvent {

    public DomainEvent(Identifier aggregateId) {
        super(aggregateId);
    }

    @Override
    public boolean equals(Object obj) {
        return reflectionEquals(obj, this);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }
}
