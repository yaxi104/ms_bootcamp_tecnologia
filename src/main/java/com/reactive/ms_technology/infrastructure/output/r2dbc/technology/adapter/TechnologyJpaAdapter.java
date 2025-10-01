package com.reactive.ms_technology.infrastructure.output.r2dbc.technology.adapter;

import com.reactive.ms_technology.domain.model.PageInfo;
import com.reactive.ms_technology.domain.model.PageResult;
import com.reactive.ms_technology.domain.model.Technology;
import com.reactive.ms_technology.domain.model.TechnologyCapacity;
import com.reactive.ms_technology.domain.spi.ITechnologyPersistencePort;
import com.reactive.ms_technology.infrastructure.output.r2dbc.technology.entity.TechnologyEntity;
import com.reactive.ms_technology.infrastructure.output.r2dbc.technology.mapper.ITechnologyMapper;
import com.reactive.ms_technology.infrastructure.output.r2dbc.technology.repository.ITechnologyRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class TechnologyJpaAdapter implements ITechnologyPersistencePort {

    private final ITechnologyRepository technologyRepository;
    private final ITechnologyMapper technologyMapper;

    @Override
    public Mono<Technology> save(Technology technology) {
        TechnologyEntity entity = technologyMapper.toEntity(technology);
        return technologyRepository.save(entity)
                .map(technologyMapper::toDomain);
    }

    @Override
    public Mono<PageResult<Technology>> findAll(PageInfo pageInfo) {
        int limit = pageInfo.getSize();
        int offset = pageInfo.getPage() * pageInfo.getSize();

        Mono<Long> totalCountMono = technologyRepository.countAll();

        Flux<TechnologyEntity> pagedResultsFlux = technologyRepository.findAllOrderedByName(limit, offset);

        Mono<List<Technology>> contentMono = pagedResultsFlux
                .map(technologyMapper::toDomain)
                .collectList();

        return Mono.zip(totalCountMono, contentMono)
                .map(tuple -> {
                    Long total = tuple.getT1();
                    List<Technology> content = tuple.getT2();
                    return new PageResult<>(content, pageInfo.getPage(), pageInfo.getSize(), total);
                });
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return technologyRepository.existsByName(name);
    }

    @Override
    public Flux<TechnologyCapacity> findAlByTechnologyId(Flux<Long> technologyIdFlux) {
        return technologyIdFlux
                .collectList()
                .flatMapMany(technologyRepository::findAllById)
                .sort((t1, t2) -> t1.getName().compareToIgnoreCase(t2.getName()))
                .map(entity -> new TechnologyCapacity(entity.getId(), entity.getName()));
    }
}
