package be.eekhaut.kristof.tournament.common.repo.keyvalue;

import be.eekhaut.kristof.tournament.common.domain.ddd.AbstractAggregateRoot;
import be.eekhaut.kristof.tournament.common.domain.ddd.DomainEvent;
import be.eekhaut.kristof.tournament.common.domain.ddd.Identifier;
import be.eekhaut.kristof.tournament.common.domain.ddd.SingleValueObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class AbstractKeyValueRepositoryImplTest {

    private TestRepository repository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        repository = new TestRepository(eventPublisher);
    }

    @Test
    void add_whenAddingAggregate_thenRegisteredEventsArePublished() {

        TestAggregate aggregate = new TestAggregate(1);
        repository.add(aggregate);

        ArgumentCaptor<DomainEvent> eventCaptor = ArgumentCaptor.forClass(DomainEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        assertThat(eventCaptor.getValue()).isInstanceOf(TestAggregateCreatedEvent.class);

        assertThat(aggregate.getRegisteredEvents()).isEmpty();

        verifyNoMoreInteractions(eventPublisher);
    }

    @Test
    void add_whenAddingAggregate_thenICanFindIt() {

        TestAggregate aggregate = new TestAggregate(1);
        repository.add(aggregate);

        List<TestAggregate> persistedAggregate = repository.findAll();

        assertThat(persistedAggregate).containsExactlyInAnyOrder(aggregate);
    }

    @Test
    void add_whenAddingMultipleAggregates_thenICanFindThemAll() {
        TestAggregate aggregate1 = new TestAggregate(1);
        repository.add(aggregate1);
        TestAggregate aggregate2 = new TestAggregate(2);
        repository.add(aggregate2);
        TestAggregate aggregate5 = new TestAggregate(5);
        repository.add(aggregate5);

        List<TestAggregate> persistedAggregates = repository.findAll();

        assertThat(persistedAggregates).containsExactlyInAnyOrder(aggregate1, aggregate2, aggregate5);
    }

    @Test
    void add_whenAddingNull_thenException() {
        assertThat(catchThrowable(() -> repository.add(null))).isInstanceOf(NullPointerException.class);
    }

    @Test
    void findById_whenLookingForAnExistingAggregate_thenItIsFound() {
        TestAggregate aggregate1 = new TestAggregate(1);
        repository.add(aggregate1);
        TestAggregate aggregate2 = new TestAggregate(2);
        repository.add(aggregate2);
        TestAggregate aggregate5 = new TestAggregate(5);
        repository.add(aggregate5);

        Optional<TestAggregate> foundAggregate = repository.findById(new TestId(2));

        assertThat(foundAggregate)
                .isPresent()
                .contains(aggregate2);
    }

    @Test
    void findById_whenLookingForANonExistingAggregate_thenNothingIsFound() {
        TestAggregate aggregate1 = new TestAggregate(1);
        repository.add(aggregate1);
        TestAggregate aggregate2 = new TestAggregate(2);
        repository.add(aggregate2);
        TestAggregate aggregate5 = new TestAggregate(5);
        repository.add(aggregate5);

        Optional<TestAggregate> foundAggregate = repository.findById(new TestId(4));

        assertThat(foundAggregate).isNotPresent();
    }

    @Test
    void findById_whenLookingForNull_thenException() {
        assertThat(catchThrowable(() -> repository.findById(null)))
                .isInstanceOf(NullPointerException.class);
    }

    private class TestId extends SingleValueObject<Integer> implements Identifier {
        TestId(int value) {
            super(value);
        }
    }

    private class TestAggregate extends AbstractAggregateRoot<TestId> {
        TestAggregate(int id) {
            super(new TestId(id));
            raise(new TestAggregateCreatedEvent(this));
        }
    }

    private class TestAggregateCreatedEvent extends DomainEvent {
        TestAggregateCreatedEvent(TestAggregate aggregate) {
            super(aggregate.getId());
        }
    }

    private class TestAggregateMapper implements DomainToValueStoreMapper<TestAggregate, String> {

        @Override
        public TestAggregate toDomain(String value) {
            return new TestAggregate(Integer.parseInt(value));
        }

        @Override
        public String toValue(TestAggregate domainObject) {
            return domainObject.getId().toString();
        }
    }

    private class TestAggregateIdMapper implements DomainToValueStoreMapper<TestId, Integer> {

        @Override
        public TestId toDomain(Integer value) {
            return new TestId(value);
        }

        @Override
        public Integer toValue(TestId domainObject) {
            return domainObject.getValue();
        }
    }

    private class TestRepository extends AbstractKeyValueRepositoryImpl<TestAggregate, TestId, Integer, String> {
        TestRepository(ApplicationEventPublisher eventPublisher) {
            super(new InMemoryKeyValueStore<>(), new TestAggregateMapper(), new TestAggregateIdMapper(), eventPublisher);
        }

        @Override
        public TestId nextId() {
            return new TestId(666);
        }
    }
}
