package com.reactive.ms_technology.domain.usecase;

import com.reactive.ms_technology.domain.api.ICapacityTechnologyServicePort;
import com.reactive.ms_technology.domain.exception.CapacityTecnologyAlreadyExistsException;
import com.reactive.ms_technology.domain.model.CapacityTechnology;
import com.reactive.ms_technology.domain.model.TechnologyCapacity;
import com.reactive.ms_technology.domain.spi.ICapacityTechnologyPersistencePort;
import com.reactive.ms_technology.domain.spi.ITechnologyPersistencePort;
import com.reactive.ms_technology.domain.utils.ValidateRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CapacityTechnologyUseCase implements ICapacityTechnologyServicePort {

    private final ICapacityTechnologyPersistencePort capacityTechnologyPersistencePort;
    private final ITechnologyPersistencePort technologyPersistencePort;

    public CapacityTechnologyUseCase(ICapacityTechnologyPersistencePort capacityTechnologyPersistencePort, ITechnologyPersistencePort technologyPersistencePort) {
        this.capacityTechnologyPersistencePort = capacityTechnologyPersistencePort;
        this.technologyPersistencePort = technologyPersistencePort;
    }

    @Override
    public Mono<Void> saveAllCapacityTechnology(Flux<CapacityTechnology> capacityTechnologyFlux) {
        return capacityTechnologyFlux
                .flatMap(capTech ->
                        ValidateRequest.checkId(capTech.getTechnologyId())
                                .then(ValidateRequest.checkId(capTech.getCapacityId()))
                                .then(
                                        capacityTechnologyPersistencePort
                                                .findByTechnologyIdAndCapacityId(capTech)
                                                .flatMap(existing -> Mono.error(new CapacityTecnologyAlreadyExistsException()))
                                                .switchIfEmpty(Mono.just(capTech))
                                )
                )
                .cast(CapacityTechnology.class)
                .collectList()
                .filter(list -> !list.isEmpty())
                .flatMapMany((List<CapacityTechnology> list) ->
                        capacityTechnologyPersistencePort.saveAllCapacityTechnology(Flux.fromIterable(list))
                )
                .then();
    }

    @Override
    public Flux<TechnologyCapacity> findTechnologiesByCapacityId(Long capacityId) {
        return capacityTechnologyPersistencePort.findByCapacityId(capacityId)
                .map(CapacityTechnology::getTechnologyId)
                .collectList()
                .flatMapMany(ids -> ids.isEmpty()
                        ? Flux.empty()
                        : technologyPersistencePort.findAlByTechnologyId(Flux.fromIterable(ids)));
    }

    @Override
    public Mono<Map<Long, List<TechnologyCapacity>>> findByCapacityIds(List<Long> capacityIds) {
        if (capacityIds == null || capacityIds.isEmpty()) {
            return Mono.just(Map.of());
        }

        return Flux.fromIterable(capacityIds)
                .flatMap(capacityId ->
                        capacityTechnologyPersistencePort.findByCapacityId(capacityId)
                                .map(CapacityTechnology::getTechnologyId)
                                .collectList()
                                .flatMap(techIds -> {
                                    if (techIds.isEmpty()) {
                                        return Mono.just(new ArrayList<TechnologyCapacity>());
                                    }
                                    return technologyPersistencePort.findAlByTechnologyId(Flux.fromIterable(techIds))
                                            .collectList()
                                            .map(techList -> techList);
                                })
                                .map(techList -> Map.entry(capacityId, techList))
                )
                .collectMap(Map.Entry::getKey, Map.Entry::getValue);
    }

}
