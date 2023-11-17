package com.data.server.api.service;


import com.data.server.api.TestDataHelper;
import com.data.server.api.model.DataBody;
import com.data.server.api.model.DataEnvelope;
import com.data.server.api.model.DataHeader;
import com.data.server.api.persistence.BlockTypeEnum;
import com.data.server.api.service.impl.DataHeaderServiceImpl;
import com.data.server.api.service.impl.DataServerAsyncServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.data.server.api.TestDataHelper.createTestDataHeaderEntity;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DataServerAsyncServiceTest {

    @Mock
    private RestTemplate restTemplate;

   @InjectMocks
    private DataServerAsyncServiceImpl dataServerAsyncServiceImpl;

   private DataEnvelope dataEnvelope;

    private ResponseEntity<Boolean> responseEntity;

    @Before
    public void setup() {
        dataEnvelope = TestDataHelper.createTestDataEnvelopeApiObject();
        responseEntity = new ResponseEntity<>(HttpStatus.OK);

    }

    @Test
    public void shouldSaveDataHeaderEntityAsExpected() throws InterruptedException {

       /* when(restTemplate.postForEntity(any(), any(DataEnvelope.class), any())).thenReturn(generateResponseEntityObject(true, HttpStatus.OK));
        CompletableFuture<Boolean> taskResult = dataServerAsyncServiceImpl.pushBigData(dataEnvelope);
        assertThat(taskResult).isNotNull();*/

    }

    private ResponseEntity<Object> generateResponseEntityObject(Boolean response, HttpStatus httpStatus){
        return new ResponseEntity<>(response, httpStatus);
    }

}
