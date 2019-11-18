package be.eekhaut.kristof.tournament.common.domain.ddd;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import static java.util.Objects.requireNonNull;

public abstract class SingleValueObject<T> implements ValueObject {

    private final T value;

    protected SingleValueObject(T value) {
        this.value = requireNonNull(value);
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SingleValueObject that = (SingleValueObject) o;

        return new EqualsBuilder()
                .append(value, that.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .toHashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
