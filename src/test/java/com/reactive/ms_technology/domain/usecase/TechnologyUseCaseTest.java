package com.reactive.ms_technology.domain.usecase;

import com.reactive.ms_technology.domain.exception.TechnologyAlreadyExistsException;
import com.reactive.ms_technology.domain.model.PageInfo;
import com.reactive.ms_technology.domain.model.PageResult;
import com.reactive.ms_technology.domain.model.Technology;
import com.reactive.ms_technology.domain.spi.ITechnologyPersistencePort;
import com.reactive.ms_technology.util.TechnologyMockFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class TechnologyUseCaseTest {

    @Mock
    private ITechnologyPersistencePort tecnologyPersistencePort;

    private TechnologyUseCase tecnologyUseCase;

    @BeforeEach
    void setUp() {
        tecnologyUseCase = new TechnologyUseCase(tecnologyPersistencePort);
    }

    @Test
    void saveTecnologySuccessTest() {
        Technology tecnologyMock = TechnologyMockFactory.createDefaultTecnology();
        Mockito.when(tecnologyPersistencePort.existsByName(tecnologyMock.getName()))
                .thenReturn(Mono.just(false));
        Mockito.when(tecnologyPersistencePort.save(tecnologyMock))
                .thenReturn(Mono.just(tecnologyMock));

        StepVerifier.create(tecnologyUseCase.saveTechnology(tecnologyMock))
                .expectNext(tecnologyMock)
                .verifyComplete();
    }

    @Test
    void saveTecnologyExistsTest() {
        Technology tecnologyMock = TechnologyMockFactory.createDefaultTecnology();
        Mockito.when(tecnologyPersistencePort.existsByName(tecnologyMock.getName()))
                .thenReturn(Mono.just(true));

        StepVerifier.create(tecnologyUseCase.saveTechnology(tecnologyMock))
                .expectError(TechnologyAlreadyExistsException.class)
                .verify();
    }

    @Test
    void findAllTecnologyPageSuccessTest() {
        PageInfo pageInfo = PageInfo.of(0, 5);
        List<Technology> content = TechnologyMockFactory.createTecnologyList();
        PageResult<Technology> pageResult = new PageResult<>(content, 0, 5, 20);

        Mockito.when(tecnologyPersistencePort.findAll(pageInfo))
                .thenReturn(Mono.just(pageResult));

        StepVerifier.create(tecnologyUseCase.findAllTechnologyPage(pageInfo))
                .expectNext(pageResult)
                .verifyComplete();
    }
}