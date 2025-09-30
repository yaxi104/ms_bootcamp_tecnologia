package com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.adapter;

import com.reactive.ms_tecnology.domain.model.PageInfo;
import com.reactive.ms_tecnology.domain.model.PageResult;
import com.reactive.ms_tecnology.domain.model.Tecnology;
import com.reactive.ms_tecnology.domain.spi.ITecnologyPersistencePort;
import com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.entity.TecnologyEntity;
import com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.mapper.ITecnologyMapper;
import com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.repository.ITecnologyRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class TecnologyJpaAdapter implements ITecnologyPersistencePort {

    private final ITecnologyRepository tecnologyRepository;
    private final ITecnologyMapper tecnologyMapper;

    @Override
    public Mono<Tecnology> save(Tecnology tecnology) {
        TecnologyEntity entity = tecnologyMapper.toEntity(tecnology);
        return tecnologyRepository.save(entity)
                .map(tecnologyMapper::toDomain);
    }

    @Override
    public Mono<PageResult<Tecnology>> findAll(PageInfo pageInfo) {
        int limit = pageInfo.getSize();
        int offset = pageInfo.getPage() * pageInfo.getSize();

        Mono<Long> totalCountMono = tecnologyRepository.countAll();

        Flux<TecnologyEntity> pagedResultsFlux = tecnologyRepository.findAllOrderedByName(limit, offset);

        Mono<List<Tecnology>> contentMono = pagedResultsFlux
                .map(tecnologyMapper::toDomain)
                .collectList();

        return Mono.zip(totalCountMono, contentMono)
                .map(tuple -> {
                    Long total = tuple.getT1();
                    List<Tecnology> content = tuple.getT2();
                    return new PageResult<>(content, pageInfo.getPage(), pageInfo.getSize(), total);
                });
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return tecnologyRepository.existsByName(name);
    }
}
