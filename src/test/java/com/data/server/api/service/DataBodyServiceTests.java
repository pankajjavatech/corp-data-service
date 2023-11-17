package com.data.server.api.service;

import com.data.server.api.TestDataHelper;
import com.data.server.api.exception.ResourceNotFoundException;
import com.data.server.api.persistence.BlockTypeEnum;
import com.data.server.api.persistence.model.DataBodyEntity;
import com.data.server.api.persistence.model.DataHeaderEntity;
import com.data.server.api.persistence.repository.DataStoreRepository;
import com.data.server.api.service.impl.DataBodyServiceImpl;
import com.data.server.api.service.impl.DataHeaderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.data.server.api.TestDataHelper.createTestDataBodyEntity;
import static com.data.server.api.TestDataHelper.createTestDataHeaderEntity;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DataBodyServiceTests {

    public static final String TEST_NAME_NO_RESULT = "TestNoResult";
    @Mock
    private DataStoreRepository dataStoreRepository;
    @Mock
    private DataHeaderService dataHeaderService;

    @InjectMocks
    private DataBodyServiceImpl dataBodyService;
    private DataBodyEntity expectedDataBodyEntity;

    @Before
    public void setup() {
        DataHeaderEntity testDataHeaderEntity = createTestDataHeaderEntity(Instant.now());
        expectedDataBodyEntity = createTestDataBodyEntity(testDataHeaderEntity);
    }

    @Test
    public void getDataByBlockType_returnsListOfAllDataBodyEntities_whenBlockTypeEnumPassedinParameter() throws ResourceNotFoundException {

        List<DataHeaderEntity> dataHeaderEntities = new ArrayList<>();
        DataHeaderEntity dataHeaderEntity = TestDataHelper.createTestDataHeaderEntity(Instant.now());
        dataHeaderEntities.add(dataHeaderEntity);

        List<DataBodyEntity> dataBodyEntiies = new ArrayList<>();
        DataBodyEntity dataBodyEntity =TestDataHelper.createTestDataBodyEntity(TestDataHelper.createTestDataHeaderEntity(Instant.now()));
        dataBodyEntiies.add(dataBodyEntity);

        when(dataHeaderService.findByBlockTypeEnum(BlockTypeEnum.BLOCKTYPEA)).thenReturn(dataHeaderEntities);
        when(dataStoreRepository.findByDataHeaderEntity(dataHeaderEntity)).thenReturn(dataBodyEntiies);

        List<DataBodyEntity> dataBodyEntitiesActual = dataBodyService.getDataByBlockType(BlockTypeEnum.BLOCKTYPEA);

        assertThat(dataBodyEntitiesActual).isNotNull();
    }

}
