package com.reactive.ms_technology.infrastructure.output.r2dbc.capacitytecnology.adapter;

import com.reactive.ms_technology.domain.model.CapacityTechnology;
import com.reactive.ms_technology.domain.spi.ICapacityTechnologyPersistencePort;
import com.reactive.ms_technology.infrastructure.output.r2dbc.capacitytecnology.mapper.ICapacityTechnologyMapper;
import com.reactive.ms_technology.infrastructure.output.r2dbc.capacitytecnology.repository.ICapacityTechnologyRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CapacityTechnologyJpaAdapter implements ICapacityTechnologyPersistencePort {

    private final ICapacityTechnologyRepository capacityTechnologyRepository;
    private final ICapacityTechnologyMapper capacityTechnologyMapper;

    @Override
    public Mono<Void> saveAllCapacityTechnology(Flux<CapacityTechnology> capacityTechnologyFlux) {
        return capacityTechnologyFlux
                .map(capacityTechnologyMapper::toEntity)
                .collectList()
                .flatMapMany(capacityTechnologyRepository::saveAll)
                .then();
    }

    @Override
    public Flux<CapacityTechnology> findByCapacityId(Long capacityId) {
        return capacityTechnologyRepository.findByCapacityId(capacityId)
                .map(capacityTechnologyMapper::toDomain);
    }

    @Override
    public Mono<CapacityTechnology> findByTechnologyIdAndCapacityId(CapacityTechnology capacityTechnology) {
        return capacityTechnologyRepository
                .findByTechnologyIdAndCapacityId(capacityTechnology.getTechnologyId(), capacityTechnology.getCapacityId())
                .map(capacityTechnologyMapper::toDomain);
    }
}
