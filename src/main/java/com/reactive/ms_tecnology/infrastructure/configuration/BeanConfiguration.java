package com.reactive.ms_tecnology.infrastructure.configuration;

import com.reactive.ms_tecnology.domain.api.ITecnologyServicePort;
import com.reactive.ms_tecnology.domain.spi.ITecnologyPersistencePort;
import com.reactive.ms_tecnology.domain.usecase.TecnologyUseCase;
import com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.adapter.TecnologyJpaAdapter;
import com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.mapper.ITecnologyMapper;
import com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.repository.ITecnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ITecnologyRepository tecnologyRepository;
    private final ITecnologyMapper tecnologyMapper;

    @Bean
    public ITecnologyPersistencePort tecnologyPersistencePort() {
        return new TecnologyJpaAdapter(tecnologyRepository, tecnologyMapper);
    }


    @Bean
    public ITecnologyServicePort tecnologyServicePort() {
        return new TecnologyUseCase(tecnologyPersistencePort());
    }

}
