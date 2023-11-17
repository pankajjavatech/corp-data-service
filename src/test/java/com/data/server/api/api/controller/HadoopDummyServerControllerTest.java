package com.data.server.api.api.controller;

import com.data.server.api.TestDataHelper;
import com.data.server.api.controller.HadoopDummyServerController;
import com.data.server.api.model.DataEnvelope;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class HadoopDummyServerControllerTest {
    @InjectMocks
    private HadoopDummyServerController hadoopDummyServerController;

    private ResponseEntity<HttpStatus> httpStatus;

    @BeforeEach
    public void setup() {
        httpStatus = new ResponseEntity<>(HttpStatus.OK);
    }

    @Test
    void pushBigData_savesPayloadToHadoopFileSystem_whenDataBodyStringPayloadInRequestIsPassed() throws Exception {

        ResponseEntity<HttpStatus> httpStatus = hadoopDummyServerController.pushBigData(TestDataHelper.DUMMY_DATA);

        assertThat(httpStatus).isNotNull();

        assertThat(httpStatus).isEqualTo(httpStatus);

    }

}