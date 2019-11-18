package be.eekhaut.kristof.tournament.common.domain.ddd;

import be.eekhaut.kristof.tournament.common.domain.ddd.AbstractAggregateRoot;
import be.eekhaut.kristof.tournament.common.domain.ddd.Identifier;
import be.eekhaut.kristof.tournament.common.domain.ddd.SingleValueObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractAggregateRootTest {

    @Test
    void equals_givenTwoAggregatesWithTheSameId_thenTheyAreEqual() {
        PizzaAggregate aggregate1 = new PizzaAggregate(3, "Margherita");
        PizzaAggregate aggregate2 = new PizzaAggregate(3, "Pepperoni");

        assertThat(aggregate1.equals(aggregate2)).isTrue();
        assertThat(aggregate2.equals(aggregate1)).isTrue();
    }

    @Test
    void equals_givenTwoAggregatesWithTheDifferentId_thenTheyAreNotEqual() {
        PizzaAggregate aggregate1 = new PizzaAggregate(2, "Margherita");
        PizzaAggregate aggregate2 = new PizzaAggregate(3, "Margherita");

        assertThat(aggregate1.equals(aggregate2)).isFalse();
        assertThat(aggregate2.equals(aggregate1)).isFalse();
    }

    @Test
    void hashCode_givenTwoAggregatesWithTheSameId_thenTheyHaveTheSameHashCode() {
        PizzaAggregate aggregate1 = new PizzaAggregate(3, "Margherita");
        PizzaAggregate aggregate2 = new PizzaAggregate(3, "Pepperoni");

        assertThat(aggregate1.hashCode()).isEqualTo(aggregate2.hashCode());
    }

    @Test
    void hashCode_givenTwoAggregatesWithTheDifferentId_thenTheyHaveADifferentHashCode() {
        PizzaAggregate aggregate1 = new PizzaAggregate(2, "Margherita");
        PizzaAggregate aggregate2 = new PizzaAggregate(3, "Margherita");

        assertThat(aggregate1.hashCode()).isNotEqualTo(aggregate2.hashCode());
    }

    private class PizzaId extends SingleValueObject<Integer> implements Identifier {
        PizzaId(int value) {
            super(value);
        }
    }

    private class PizzaAggregate extends AbstractAggregateRoot<PizzaId> {

        private String name;

        PizzaAggregate(int id, String name) {
            super(new PizzaId(id));
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}