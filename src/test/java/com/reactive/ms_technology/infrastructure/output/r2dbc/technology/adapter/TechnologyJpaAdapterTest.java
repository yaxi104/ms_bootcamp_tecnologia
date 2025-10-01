package com.reactive.ms_technology.infrastructure.output.r2dbc.technology.adapter;

import com.reactive.ms_technology.domain.model.PageInfo;
import com.reactive.ms_technology.domain.model.Technology;
import com.reactive.ms_technology.infrastructure.output.r2dbc.technology.entity.TechnologyEntity;
import com.reactive.ms_technology.infrastructure.output.r2dbc.technology.mapper.ITechnologyMapper;
import com.reactive.ms_technology.infrastructure.output.r2dbc.technology.repository.ITechnologyRepository;
import com.reactive.ms_technology.util.TechnologyMockFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class TechnologyJpaAdapterTest {
    private ITechnologyRepository tecnologyRepository;
    private ITechnologyMapper tecnologyMapper;
    private TechnologyJpaAdapter tecnologyJpaAdapter;

    @BeforeEach
    void setUp() {
        tecnologyRepository = Mockito.mock(ITechnologyRepository.class);
        tecnologyMapper = Mockito.mock(ITechnologyMapper.class);
        tecnologyJpaAdapter = new TechnologyJpaAdapter(tecnologyRepository, tecnologyMapper);
    }

    @Test
    void saveSuccessTest() {
        Technology domain = TechnologyMockFactory.createDefaultTecnology();
        TechnologyEntity entity = TechnologyMockFactory.createDefaultTecnologyEntity();

        when(tecnologyMapper.toEntity(domain)).thenReturn(entity);
        when(tecnologyRepository.save(entity)).thenReturn(Mono.just(entity));
        when(tecnologyMapper.toDomain(entity)).thenReturn(domain);

        StepVerifier.create(tecnologyJpaAdapter.save(domain))
                .expectNext(domain)
                .verifyComplete();
    }

    @Test
    void findAlSuccessTest() {
        PageInfo pageInfo = PageInfo.of(0, 2);

        TechnologyEntity entity1 = TechnologyMockFactory.createDefaultTecnologyEntity();
        TechnologyEntity entity2 = new TechnologyEntity();
        entity2.setId(2L);
        entity2.setName("Python");
        entity2.setDescription("Lenguaje versátil");

        Technology domain1 = new Technology();
        domain1.setId(1L);
        domain1.setName("Java");
        domain1.setDescription("Lenguaje OOP");

        Technology domain2 = new Technology();
        domain2.setId(2L);
        domain2.setName("Python");
        domain2.setDescription("Lenguaje versátil");

        when(tecnologyRepository.countAll()).thenReturn(Mono.just(10L));
        when(tecnologyRepository.findAllOrderedByName(2, 0)).thenReturn(Flux.just(entity1, entity2));
        when(tecnologyMapper.toDomain(entity1)).thenReturn(domain1);
        when(tecnologyMapper.toDomain(entity2)).thenReturn(domain2);

        StepVerifier.create(tecnologyJpaAdapter.findAll(pageInfo))
                .assertNext(result -> {
                    List<Technology> content = result.getContent();

                    List<String> names = content.stream().map(Technology::getName).toList();
                    List<String> descriptions = content.stream().map(Technology::getDescription).toList();

                    assertEquals(2, content.size());
                    assertTrue(names.contains("Java"));
                    assertTrue(names.contains("Python"));
                    assertTrue(descriptions.contains("Lenguaje OOP"));
                    assertTrue(descriptions.contains("Lenguaje versátil"));

                    assertEquals(0, result.getPage());
                    assertEquals(2, result.getSize());
                    assertEquals(10, result.getTotalElements());
                    assertEquals(5, result.getTotalPages());
                })
                .verifyComplete();
    }

    @Test
    void existsByNameSuccessTest() {
        when(tecnologyRepository.existsByName("Java")).thenReturn(Mono.just(true));

        StepVerifier.create(tecnologyJpaAdapter.existsByName("Java"))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void findAlByTechnologyIdSuccessTest() {
        TechnologyEntity entity1 = new TechnologyEntity();
        entity1.setId(1L);
        entity1.setName("Java");

        TechnologyEntity entity2 = new TechnologyEntity();
        entity2.setId(2L);
        entity2.setName("Python");

        when(tecnologyRepository.findAllById(Arrays.asList(1L, 2L)))
                .thenReturn(Flux.just(entity1, entity2));

        Flux<Long> idsFlux = Flux.just(1L, 2L);

        StepVerifier.create(tecnologyJpaAdapter.findAlByTechnologyId(idsFlux))
                .expectNextMatches(tc -> tc.getId().equals(1L) && tc.getName().equals("Java"))
                .expectNextMatches(tc -> tc.getId().equals(2L) && tc.getName().equals("Python"))
                .verifyComplete();
    }
}
