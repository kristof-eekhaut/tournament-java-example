package be.eekhaut.kristof.tournament.common.domain.ddd;

import be.eekhaut.kristof.tournament.common.domain.ddd.SingleValueObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class SingleValueObjectTest {

    @Test
    void create() {
        TestValueObject valueObject = new TestValueObject(3);

        assertThat(valueObject.getValue()).isEqualTo(3);
    }

    @Test
    void create_whenValueIsNull_thenException() {
        assertThat(catchThrowable(() -> new TestValueObject(null))).isInstanceOf(NullPointerException.class);
    }

    @Test
    void equals_givenTwoValueObjectsWithTheSameValue_thenTheyAreEqual() {
        assertThat(new TestValueObject(1).equals(new TestValueObject(1))).isTrue();
    }

    @Test
    void equals_givenTwoValueObjectsWithDifferentValue_thenTheyAreNotEqual() {
        assertThat(new TestValueObject(1).equals(new TestValueObject(2))).isFalse();
    }

    @Test
    void hashcode_givenTwoValueObjectsWithTheSameValue_thenTheyHaveTheSameHashCode() {
        assertThat(new TestValueObject(4).hashCode()).isEqualTo(new TestValueObject(4).hashCode());
    }

    @Test
    void hashcode_givenTwoValueObjectsWithDifferentValue_thenTheyHaveDifferentHashCode() {
        assertThat(new TestValueObject(4).hashCode()).isNotEqualTo(new TestValueObject(7).hashCode());
    }

    @Test
    void toString_thenValueIsReturnedAsString() {
        assertThat(new TestValueObject(9).toString()).isEqualTo("9");
    }

    private class TestValueObject extends SingleValueObject<Integer> {

        TestValueObject(Integer value) {
            super(value);
        }
    }
}
