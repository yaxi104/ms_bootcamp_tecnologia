package com.reactive.ms_tecnology.domain.usecase;

import com.reactive.ms_tecnology.domain.exception.TecnologyAlreadyExistsException;
import com.reactive.ms_tecnology.domain.model.PageInfo;
import com.reactive.ms_tecnology.domain.model.PageResult;
import com.reactive.ms_tecnology.domain.model.Tecnology;
import com.reactive.ms_tecnology.domain.spi.ITecnologyPersistencePort;
import com.reactive.ms_tecnology.util.TecnologyMockFactory;
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
class TecnologyUseCaseTest {

    @Mock
    private ITecnologyPersistencePort tecnologyPersistencePort;

    private TecnologyUseCase tecnologyUseCase;

    @BeforeEach
    void setUp() {
        tecnologyUseCase = new TecnologyUseCase(tecnologyPersistencePort);
    }

    @Test
    void saveTecnologySuccessTest() {
        Tecnology tecnologyMock = TecnologyMockFactory.createDefaultTecnology();
        Mockito.when(tecnologyPersistencePort.existsByName(tecnologyMock.getName()))
                .thenReturn(Mono.just(false));
        Mockito.when(tecnologyPersistencePort.save(tecnologyMock))
                .thenReturn(Mono.just(tecnologyMock));

        StepVerifier.create(tecnologyUseCase.saveTecnology(tecnologyMock))
                .expectNext(tecnologyMock)
                .verifyComplete();
    }

    @Test
    void saveTecnologyExistsTest() {
        Tecnology tecnologyMock = TecnologyMockFactory.createDefaultTecnology();
        Mockito.when(tecnologyPersistencePort.existsByName(tecnologyMock.getName()))
                .thenReturn(Mono.just(true));

        StepVerifier.create(tecnologyUseCase.saveTecnology(tecnologyMock))
                .expectError(TecnologyAlreadyExistsException.class)
                .verify();
    }

    @Test
    void findAllTecnologyPageSuccessTest() {
        PageInfo pageInfo = PageInfo.of(0, 5);
        List<Tecnology> content = TecnologyMockFactory.createTecnologyList();
        PageResult<Tecnology> pageResult = new PageResult<>(content, 0, 5, 20);

        Mockito.when(tecnologyPersistencePort.findAll(pageInfo))
                .thenReturn(Mono.just(pageResult));

        StepVerifier.create(tecnologyUseCase.findAllTecnologyPage(pageInfo))
                .expectNext(pageResult)
                .verifyComplete();
    }
}