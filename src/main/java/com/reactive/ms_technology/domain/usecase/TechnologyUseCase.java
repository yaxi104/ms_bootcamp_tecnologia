package com.reactive.ms_technology.domain.usecase;

import com.reactive.ms_technology.domain.api.ITechnologyServicePort;
import com.reactive.ms_technology.domain.exception.TechnologyAlreadyExistsException;
import com.reactive.ms_technology.domain.model.PageInfo;
import com.reactive.ms_technology.domain.model.PageResult;
import com.reactive.ms_technology.domain.model.Technology;
import com.reactive.ms_technology.domain.spi.ITechnologyPersistencePort;
import com.reactive.ms_technology.domain.utils.Constants;
import com.reactive.ms_technology.domain.utils.ValidateRequest;
import reactor.core.publisher.Mono;

public class TechnologyUseCase implements ITechnologyServicePort {

    private final ITechnologyPersistencePort technologyPersistencePort;

    public TechnologyUseCase(ITechnologyPersistencePort technologyPersistencePort) {
        this.technologyPersistencePort = technologyPersistencePort;
    }

    @Override
    public Mono<Technology> saveTechnology(Technology technology) {
        return ValidateRequest.checkNotBlank(technology.getName())
                .then(ValidateRequest.checkNotLengthValid(technology.getName(), Constants.MAX_LENGHT_NAME))
                .then(ValidateRequest.checkNotBlank(technology.getDescription()))
                .then(ValidateRequest.checkNotLengthValid(technology.getDescription(), Constants.MAX_LENGHT_DESCRIPTION))
                .then(technologyPersistencePort.existsByName(technology.getName()))
                .flatMap(exists -> exists
                        ? Mono.error(new TechnologyAlreadyExistsException())
                        : technologyPersistencePort.save(technology));
    }

    @Override
    public Mono<PageResult<Technology>> findAllTechnologyPage(PageInfo pageInfo) {
        return technologyPersistencePort.findAll(pageInfo);
    }
}
