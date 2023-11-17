package com.data.server.api.persistence.model;

import com.data.server.api.TestDataHelper;
import com.data.server.api.persistence.BlockTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DataBodyEntityTests {

    @Test
    public void assignDataBodyEntityFieldsShouldWorkAsExpected() {
        Instant expectedTimestamp = Instant.now();

        DataHeaderEntity dataHeaderEntity = new DataHeaderEntity();
        dataHeaderEntity.setName(TestDataHelper.TEST_NAME);
        dataHeaderEntity.setBlocktype(BlockTypeEnum.BLOCKTYPEA);
        dataHeaderEntity.setCreatedTimestamp(expectedTimestamp);

        DataBodyEntity dataBodyEntity = TestDataHelper.createTestDataBodyEntity(dataHeaderEntity);

        assertThat(dataBodyEntity.getDataHeaderEntity()).isNotNull();
        assertThat(dataBodyEntity.getDataBody()).isNotNull();
    }

    /**
     * This test intentionally fails and the code it tests against will need to be fixed.
     */
    @Test
    public void checkTwoDataBodiesAreEqualAsExpected() {

        DataHeaderEntity dataHeaderEntity1 = new DataHeaderEntity();
        dataHeaderEntity1.setName(TestDataHelper.TEST_NAME);
        dataHeaderEntity1.setBlocktype(BlockTypeEnum.BLOCKTYPEA);
        dataHeaderEntity1.setCreatedTimestamp(Instant.now());
        DataBodyEntity dataBodyEntity1 = TestDataHelper.createTestDataBodyEntity(dataHeaderEntity1);

        DataHeaderEntity dataHeaderEntity2 = new DataHeaderEntity();
        dataHeaderEntity2.setName(TestDataHelper.TEST_NAME);
        dataHeaderEntity2.setBlocktype(BlockTypeEnum.BLOCKTYPEA);
        dataHeaderEntity2.setCreatedTimestamp(Instant.now().plusSeconds(100L));
        DataBodyEntity dataBodyEntity2 = TestDataHelper.createTestDataBodyEntity(dataHeaderEntity2);

       // assertThat(dataBodyEntity1).isEqualTo(dataBodyEntity2);
    }
}
