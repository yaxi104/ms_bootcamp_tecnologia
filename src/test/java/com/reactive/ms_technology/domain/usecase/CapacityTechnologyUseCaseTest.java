package com.reactive.ms_technology.domain.usecase;

import com.reactive.ms_technology.domain.exception.CapacityTecnologyAlreadyExistsException;
import com.reactive.ms_technology.domain.model.CapacityTechnology;
import com.reactive.ms_technology.domain.model.TechnologyCapacity;
import com.reactive.ms_technology.domain.spi.ICapacityTechnologyPersistencePort;
import com.reactive.ms_technology.domain.spi.ITechnologyPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CapacityTechnologyUseCaseTest {

    @Mock
    private ICapacityTechnologyPersistencePort capacityTechnologyPersistencePort;

    @Mock
    private ITechnologyPersistencePort technologyPersistencePort;

    private CapacityTechnologyUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new CapacityTechnologyUseCase(capacityTechnologyPersistencePort, technologyPersistencePort);
    }

    @Test
    void saveAllCapacityTechnologyCorrectly() {
        CapacityTechnology capTech = new CapacityTechnology(null, 1L, 100L);

        when(capacityTechnologyPersistencePort.findByTechnologyIdAndCapacityId(capTech)).thenReturn(Mono.empty());
        when(capacityTechnologyPersistencePort.saveAllCapacityTechnology(any())).thenReturn(Mono.empty());

        Mono<Void> result = useCase.saveAllCapacityTechnology(Flux.just(capTech));

        StepVerifier.create(result)
                .verifyComplete();

        verify(capacityTechnologyPersistencePort).findByTechnologyIdAndCapacityId(capTech);

        ArgumentCaptor<Flux<CapacityTechnology>> captor = ArgumentCaptor.forClass(Flux.class);
        verify(capacityTechnologyPersistencePort).saveAllCapacityTechnology(captor.capture());

        StepVerifier.create(captor.getValue())
                .expectNextMatches(ct -> ct.getTechnologyId().equals(1L) && ct.getCapacityId().equals(100L))
                .verifyComplete();
    }

    @Test
    void saveAllCapacityTechnologyThrowWhenRelationExists() {
        CapacityTechnology capTech = new CapacityTechnology(null, 1L, 100L);

        when(capacityTechnologyPersistencePort.findByTechnologyIdAndCapacityId(capTech)).thenReturn(Mono.just(capTech));

        Mono<Void> result = useCase.saveAllCapacityTechnology(Flux.just(capTech));

        StepVerifier.create(result)
                .expectError(CapacityTecnologyAlreadyExistsException.class)
                .verify();

        verify(capacityTechnologyPersistencePort).findByTechnologyIdAndCapacityId(capTech);
        verify(capacityTechnologyPersistencePort, never()).saveAllCapacityTechnology(any());
    }

    @Test
    void findTechnologiesByCapacityIdShouldReturnEmptyWhenNoRelations() {
        Long capacityId = 100L;

        when(capacityTechnologyPersistencePort.findByCapacityId(capacityId)).thenReturn(Flux.empty());

        Flux<TechnologyCapacity> result = useCase.findTechnologiesByCapacityId(capacityId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(capacityTechnologyPersistencePort).findByCapacityId(capacityId);
        verify(technologyPersistencePort, never()).findAlByTechnologyId(any());
    }

    @Test
    void findTechnologiesByCapacityIdShouldReturnTechnologyCapacities() {
        Long capacityId = 100L;

        CapacityTechnology capTech1 = new CapacityTechnology(null, 1L, capacityId);
        CapacityTechnology capTech2 = new CapacityTechnology(null, 2L, capacityId);

        TechnologyCapacity techCap1 = new TechnologyCapacity(1L, "Java");
        TechnologyCapacity techCap2 = new TechnologyCapacity(2L, "Python");

        when(capacityTechnologyPersistencePort.findByCapacityId(capacityId)).thenReturn(Flux.just(capTech1, capTech2));
        when(technologyPersistencePort.findAlByTechnologyId(any())).thenReturn(Flux.just(techCap1, techCap2));

        Flux<TechnologyCapacity> result = useCase.findTechnologiesByCapacityId(capacityId);

        StepVerifier.create(result)
                .expectNext(techCap1)
                .expectNext(techCap2)
                .verifyComplete();

        verify(capacityTechnologyPersistencePort).findByCapacityId(capacityId);

        ArgumentCaptor<Flux<Long>> captor = ArgumentCaptor.forClass(Flux.class);
        verify(technologyPersistencePort).findAlByTechnologyId(captor.capture());

        StepVerifier.create(captor.getValue())
                .expectNext(1L, 2L)
                .verifyComplete();
    }
}