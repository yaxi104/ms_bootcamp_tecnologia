package com.reactive.ms_tecnology.domain.usecase;

import com.reactive.ms_tecnology.domain.api.ITecnologyServicePort;
import com.reactive.ms_tecnology.domain.exception.TecnologyAlreadyExistsException;
import com.reactive.ms_tecnology.domain.model.PageInfo;
import com.reactive.ms_tecnology.domain.model.PageResult;
import com.reactive.ms_tecnology.domain.model.Tecnology;
import com.reactive.ms_tecnology.domain.spi.ITecnologyPersistencePort;
import com.reactive.ms_tecnology.domain.utils.Constants;
import com.reactive.ms_tecnology.domain.utils.ValidateRequest;
import reactor.core.publisher.Mono;

public class TecnologyUseCase implements ITecnologyServicePort {

    private final ITecnologyPersistencePort tecnologyPersistencePort;

    public TecnologyUseCase(ITecnologyPersistencePort tecnologyPersistencePort) {
        this.tecnologyPersistencePort = tecnologyPersistencePort;

    }

    @Override
    public Mono<Tecnology> saveTecnology(Tecnology tecnology) {
        String nameTecnology = tecnology.getName();
        String descriptionTecnology = tecnology.getDescription();

        return ValidateRequest.checkNotBlank(nameTecnology)
                .then(ValidateRequest.checkNotLengthValid(nameTecnology, Constants.MAX_LENGHT_NAME))
                .then(ValidateRequest.checkNotBlank(descriptionTecnology))
                .then(ValidateRequest.checkNotLengthValid(descriptionTecnology, Constants.MAX_LENGHT_DESCRIPTION))
                .then(tecnologyPersistencePort.existsByName(nameTecnology))
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new TecnologyAlreadyExistsException());
                    } else {
                        return tecnologyPersistencePort.save(tecnology);
                    }
                });
    }

    @Override
    public Mono<PageResult<Tecnology>> findAllTecnologyPage(PageInfo pageInfo) {
        return tecnologyPersistencePort.findAll(pageInfo);
    }
}
