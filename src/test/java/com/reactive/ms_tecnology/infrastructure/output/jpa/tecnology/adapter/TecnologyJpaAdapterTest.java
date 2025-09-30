package com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.adapter;

import com.reactive.ms_tecnology.domain.model.PageInfo;
import com.reactive.ms_tecnology.domain.model.Tecnology;
import com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.entity.TecnologyEntity;
import com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.mapper.ITecnologyMapper;
import com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.repository.ITecnologyRepository;
import com.reactive.ms_tecnology.util.TecnologyMockFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TecnologyJpaAdapterTest {
    private ITecnologyRepository tecnologyRepository;
    private ITecnologyMapper tecnologyMapper;
    private TecnologyJpaAdapter tecnologyJpaAdapter;

    @BeforeEach
    void setUp() {
        tecnologyRepository = Mockito.mock(ITecnologyRepository.class);
        tecnologyMapper = Mockito.mock(ITecnologyMapper.class);
        tecnologyJpaAdapter = new TecnologyJpaAdapter(tecnologyRepository, tecnologyMapper);
    }

    @Test
    void saveSuccessTest() {
        Tecnology domain = TecnologyMockFactory.createDefaultTecnology();
        TecnologyEntity entity = TecnologyMockFactory.createDefaultTecnologyEntity();

        Mockito.when(tecnologyMapper.toEntity(domain)).thenReturn(entity);
        Mockito.when(tecnologyRepository.save(entity)).thenReturn(Mono.just(entity));
        Mockito.when(tecnologyMapper.toDomain(entity)).thenReturn(domain);

        StepVerifier.create(tecnologyJpaAdapter.save(domain))
                .expectNext(domain)
                .verifyComplete();
    }

    @Test
    void findAlSuccessTest() {
        PageInfo pageInfo = PageInfo.of(0, 2);

        TecnologyEntity entity1 = TecnologyMockFactory.createDefaultTecnologyEntity();
        TecnologyEntity entity2 = new TecnologyEntity();
        entity2.setId(2L);
        entity2.setName("Python");
        entity2.setDescription("Lenguaje versátil");

        Tecnology domain1 = new Tecnology();
        domain1.setId(1L);
        domain1.setName("Java");
        domain1.setDescription("Lenguaje OOP");

        Tecnology domain2 = new Tecnology();
        domain2.setId(2L);
        domain2.setName("Python");
        domain2.setDescription("Lenguaje versátil");

        Mockito.when(tecnologyRepository.countAll()).thenReturn(Mono.just(10L));
        Mockito.when(tecnologyRepository.findAllOrderedByName(2, 0)).thenReturn(Flux.just(entity1, entity2));
        Mockito.when(tecnologyMapper.toDomain(entity1)).thenReturn(domain1);
        Mockito.when(tecnologyMapper.toDomain(entity2)).thenReturn(domain2);

        StepVerifier.create(tecnologyJpaAdapter.findAll(pageInfo))
                .assertNext(result -> {
                    List<Tecnology> content = result.getContent();

                    List<String> names = content.stream().map(Tecnology::getName).toList();
                    List<String> descriptions = content.stream().map(Tecnology::getDescription).toList();

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
        Mockito.when(tecnologyRepository.existsByName("Java")).thenReturn(Mono.just(true));

        StepVerifier.create(tecnologyJpaAdapter.existsByName("Java"))
                .expectNext(true)
                .verifyComplete();
    }
}