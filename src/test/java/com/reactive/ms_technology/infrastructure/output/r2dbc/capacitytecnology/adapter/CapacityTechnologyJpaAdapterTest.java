package com.reactive.ms_technology.infrastructure.output.r2dbc.capacitytecnology.adapter;

import com.reactive.ms_technology.domain.model.CapacityTechnology;
import com.reactive.ms_technology.infrastructure.output.r2dbc.capacitytecnology.entity.CapacityTechnologyEntity;
import com.reactive.ms_technology.infrastructure.output.r2dbc.capacitytecnology.mapper.ICapacityTechnologyMapper;
import com.reactive.ms_technology.infrastructure.output.r2dbc.capacitytecnology.repository.ICapacityTechnologyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CapacityTechnologyJpaAdapterTest {
    private ICapacityTechnologyRepository capacityTechnologyRepository;
    private ICapacityTechnologyMapper capacityTechnologyMapper;
    private CapacityTechnologyJpaAdapter capacityTechnologyJpaAdapter;

    @BeforeEach
    void setUp() {
        capacityTechnologyRepository = mock(ICapacityTechnologyRepository.class);
        capacityTechnologyMapper = mock(ICapacityTechnologyMapper.class);
        capacityTechnologyJpaAdapter = new CapacityTechnologyJpaAdapter(
                capacityTechnologyRepository,
                capacityTechnologyMapper
        );
    }

    @Test
    void saveAllCapacityTechnologyCompleteSuccessfully() {
        CapacityTechnology domain1 = new CapacityTechnology(1L, 1L, 1L);
        CapacityTechnology domain2 = new CapacityTechnology(2L, 2L, 1L);

        CapacityTechnologyEntity entity1 = new CapacityTechnologyEntity(1L, 1L, 1L);
        CapacityTechnologyEntity entity2 = new CapacityTechnologyEntity(2L, 2L, 1L);

        Flux<CapacityTechnology> domainFlux = Flux.just(domain1, domain2);
        List<CapacityTechnologyEntity> entityList = List.of(entity1, entity2);

        when(capacityTechnologyMapper.toEntity(domain1)).thenReturn(entity1);
        when(capacityTechnologyMapper.toEntity(domain2)).thenReturn(entity2);
        when(capacityTechnologyRepository.saveAll(entityList)).thenReturn(Flux.fromIterable(entityList));

        Mono<Void> result = capacityTechnologyJpaAdapter.saveAllCapacityTechnology(domainFlux);

        StepVerifier.create(result)
                .verifyComplete();

        verify(capacityTechnologyMapper).toEntity(domain1);
        verify(capacityTechnologyMapper).toEntity(domain2);
        verify(capacityTechnologyRepository).saveAll(entityList);
    }

    @Test
    void findByCapacityIdReturnsCapacityTechnologies() {
        Long capacityId = 100L;
        CapacityTechnologyEntity entity1 = new CapacityTechnologyEntity();
        CapacityTechnologyEntity entity2 = new CapacityTechnologyEntity();

        CapacityTechnology domain1 = new CapacityTechnology(1L, 10L, capacityId);
        CapacityTechnology domain2 = new CapacityTechnology(2L, 20L, capacityId);

        when(capacityTechnologyRepository.findByCapacityId(capacityId)).thenReturn(Flux.just(entity1, entity2));
        when(capacityTechnologyMapper.toDomain(entity1)).thenReturn(domain1);
        when(capacityTechnologyMapper.toDomain(entity2)).thenReturn(domain2);

        Flux<CapacityTechnology> result = capacityTechnologyJpaAdapter.findByCapacityId(capacityId);

        StepVerifier.create(result)
                .expectNext(domain1)
                .expectNext(domain2)
                .verifyComplete();

        verify(capacityTechnologyRepository).findByCapacityId(capacityId);
        verify(capacityTechnologyMapper, times(1)).toDomain(entity1);
        verify(capacityTechnologyMapper, times(1)).toDomain(entity2);
    }

    @Test
    void findByTechnologyIdAndCapacityIdReturnsCapacityTechnology() {
        CapacityTechnology domain = new CapacityTechnology(1L, 10L, 100L);
        CapacityTechnologyEntity entity = new CapacityTechnologyEntity();

        when(capacityTechnologyRepository.findByTechnologyIdAndCapacityId(domain.getTechnologyId(), domain.getCapacityId()))
                .thenReturn(Mono.just(entity));
        when(capacityTechnologyMapper.toDomain(entity)).thenReturn(domain);

        Mono<CapacityTechnology> result = capacityTechnologyJpaAdapter.findByTechnologyIdAndCapacityId(domain);

        StepVerifier.create(result)
                .expectNext(domain)
                .verifyComplete();

        verify(capacityTechnologyRepository).findByTechnologyIdAndCapacityId(domain.getTechnologyId(), domain.getCapacityId());
        verify(capacityTechnologyMapper).toDomain(entity);
    }
}