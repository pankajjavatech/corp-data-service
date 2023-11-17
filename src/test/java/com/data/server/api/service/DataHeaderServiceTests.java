package com.data.server.api.service;

import com.data.server.api.TestDataHelper;
import com.data.server.api.exception.ResourceNotFoundException;
import com.data.server.api.persistence.BlockTypeEnum;
import com.data.server.api.persistence.model.DataBodyEntity;
import com.data.server.api.persistence.model.DataHeaderEntity;
import com.data.server.api.persistence.repository.DataHeaderRepository;
import com.data.server.api.service.impl.DataHeaderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.data.server.api.TestDataHelper.createTestDataHeaderEntity;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DataHeaderServiceTests {

    @Mock
    private DataHeaderRepository dataHeaderRepositoryMock;

    //private DataHeaderService dataHeaderService;
    private DataHeaderEntity expectedDataHeaderEntity;

    @InjectMocks
    private DataHeaderServiceImpl dataHeaderService;

    @Before
    public void setup() {
        expectedDataHeaderEntity = createTestDataHeaderEntity(Instant.now());
        //dataHeaderService = new DataHeaderServiceImpl(dataHeaderRepositoryMock);
    }

    @Test
    public void shouldSaveDataHeaderEntityAsExpected(){
        dataHeaderService.saveHeader(expectedDataHeaderEntity);

        verify(dataHeaderRepositoryMock, times(1))
                .save(eq(expectedDataHeaderEntity));
    }


    @Test
    public void findByBlockTypeEnum_returnsAllDataHeaderEntities_whenBlockTypeIsPassedinParameter() throws ResourceNotFoundException {

        List<DataHeaderEntity> dataHeaderEntities = new ArrayList<>();
        DataHeaderEntity dataHeaderEntity = TestDataHelper.createTestDataHeaderEntity(Instant.now());
        dataHeaderEntities.add(dataHeaderEntity);

        when(dataHeaderRepositoryMock.findByBlocktype(BlockTypeEnum.BLOCKTYPEA)).thenReturn(Optional.of(dataHeaderEntities));

        List<DataHeaderEntity> dataHeaderEntitiesActual = dataHeaderService.findByBlockTypeEnum( BlockTypeEnum.BLOCKTYPEA);

        assertThat(dataHeaderEntitiesActual).isNotNull();

        assertThat(dataHeaderEntitiesActual.size()).isEqualTo(1);

    }


    @Test
    public void updateHeaderType_returnsTrueIfBlockTypeIsUpdatedForBlockName_whenBlockNameIsPassedinParameter() throws ResourceNotFoundException {

        List<DataHeaderEntity> dataHeaderEntities = new ArrayList<>();
        DataHeaderEntity dataHeaderEntity = TestDataHelper.createTestDataHeaderEntity(Instant.now());
        dataHeaderEntities.add(dataHeaderEntity);

        when(dataHeaderRepositoryMock.findByName(TestDataHelper.TEST_NAME)).thenReturn(Optional.of(dataHeaderEntity));

        Boolean isUpdated = dataHeaderService.updateHeaderType(TestDataHelper.TEST_NAME, BlockTypeEnum.BLOCKTYPEA.name());

        assertThat(isUpdated).isNotNull();

        assertThat(isUpdated).isEqualTo(true);

    }

}
