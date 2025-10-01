package com.reactive.ms_technology.infrastructure.configuration;

import com.reactive.ms_technology.domain.api.ICapacityTechnologyServicePort;
import com.reactive.ms_technology.domain.api.ITechnologyServicePort;
import com.reactive.ms_technology.domain.spi.ICapacityTechnologyPersistencePort;
import com.reactive.ms_technology.domain.spi.ITechnologyPersistencePort;
import com.reactive.ms_technology.domain.usecase.CapacityTechnologyUseCase;
import com.reactive.ms_technology.domain.usecase.TechnologyUseCase;
import com.reactive.ms_technology.infrastructure.output.r2dbc.capacitytecnology.adapter.CapacityTechnologyJpaAdapter;
import com.reactive.ms_technology.infrastructure.output.r2dbc.capacitytecnology.mapper.ICapacityTechnologyMapper;
import com.reactive.ms_technology.infrastructure.output.r2dbc.capacitytecnology.repository.ICapacityTechnologyRepository;
import com.reactive.ms_technology.infrastructure.output.r2dbc.technology.adapter.TechnologyJpaAdapter;
import com.reactive.ms_technology.infrastructure.output.r2dbc.technology.mapper.ITechnologyMapper;
import com.reactive.ms_technology.infrastructure.output.r2dbc.technology.repository.ITechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ITechnologyRepository technologyRepository;
    private final ITechnologyMapper technologyMapper;
    private final ICapacityTechnologyRepository capacityTechnologyRepository;
    private final ICapacityTechnologyMapper capacityTechnologyMapper;

    @Bean
    public ITechnologyPersistencePort technologyPersistencePort() {
        return new TechnologyJpaAdapter(technologyRepository, technologyMapper);
    }

    @Bean
    public ICapacityTechnologyPersistencePort capacityTechnologyPersistencePort() {
        return new CapacityTechnologyJpaAdapter(capacityTechnologyRepository, capacityTechnologyMapper);
    }

    @Bean
    public ITechnologyServicePort tecnologyServicePort() {
        return new TechnologyUseCase(technologyPersistencePort());
    }

    @Bean
    public ICapacityTechnologyServicePort capacityTechnologyServicePort() {
        return new CapacityTechnologyUseCase(capacityTechnologyPersistencePort(), technologyPersistencePort());
    }

}
