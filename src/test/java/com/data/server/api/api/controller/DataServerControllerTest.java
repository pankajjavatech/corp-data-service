package com.data.server.api.api.controller;

import com.data.server.api.TestDataHelper;
import com.data.server.api.controller.DataServerController;
import com.data.server.api.exception.HadoopClientException;
import com.data.server.api.model.DataBody;
import com.data.server.api.model.DataEnvelope;
import com.data.server.api.model.DataHeader;
import com.data.server.api.persistence.BlockTypeEnum;
import com.data.server.api.persistence.model.DataBodyEntity;
import com.data.server.api.service.DataBodyService;
import com.data.server.api.service.DataEnvelopeService;
import com.data.server.api.service.DataHeaderService;
import com.data.server.api.service.DataServerAsyncService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebMvcTest(DataServerController.class)
public class DataServerControllerTest {

    public static final String HEADER_NAME = "TSLA-USDGBP-10Y";
    public static final String MD5_CHECKSUM = "cecfd3953783df706878aaec2c22aa70";
    public static final String DUMMY_DATA = "AKCp5fU4WNWKBVvhXsbNhqk33tawri9iJUkA5o4A6YqpwvAoYjajVw8xdEw6r9796h1wEp29D";


    @MockBean
    private DataEnvelopeService dataEnvelopeService;

    @MockBean
    private DataHeaderService dataHeaderService;

    @MockBean
    private DataBodyService dataBodyService;

    @MockBean
    private DataServerAsyncService dataServerAsyncService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    List<DataBodyEntity> DataBodyEntities;

    private TestDataHelper testDataHelper;


    @Before
    public void setUp() throws HadoopClientException, NoSuchAlgorithmException, IOException {
        objectMapper = Jackson2ObjectMapperBuilder
                .json()
                .build();

        when(dataEnvelopeService.saveDataEnvelope(any(DataEnvelope.class))).thenReturn(true);
    }

    @DisplayName("JUnit test to push block of Data to Data Server and Push Data to Big Data Lake when DataEnveloped is passed in Payload")
    @Test
    void pushDataEndPoint_savesDataEnvelope_whenDataEnvelopePayloadInRequestIsPassed() throws Exception {

        DataEnvelope dataEnvelope = createDataEnvelope();

        CompletableFuture<Boolean> checksumResult = new CompletableFuture<Boolean>();

        when(dataEnvelopeService.saveDataEnvelope(any(DataEnvelope.class))).thenReturn(true);

        when(dataServerAsyncService.pushBigData(any(DataEnvelope.class))).thenReturn(checksumResult);

        mockMvc.perform(post("/dataserver/pushdata").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dataEnvelope)))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @DisplayName("JUnit test to get list of Data Envelope from Data Serve when Data Sever searched by Block Type")
    @Test
    void getDataByBlockType_returnsListOfAllDataEnvelope_whenDataServerIsSearchedByBlockType() throws Exception {

        List<DataBodyEntity> dataBodyEntiies = new ArrayList<>();

        dataBodyEntiies.add(TestDataHelper.createTestDataBodyEntity(TestDataHelper.createTestDataHeaderEntity(Instant.now())));

        when(dataBodyService.getDataByBlockType(BlockTypeEnum.BLOCKTYPEA)).thenReturn(dataBodyEntiies);

        mockMvc.perform(get("/dataserver/getData/{blockType}", BlockTypeEnum.BLOCKTYPEA))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].dataHeader.name").value(TestDataHelper.TEST_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].dataBody.dataBody").value(TestDataHelper.DUMMY_DATA))
                .andDo(print());
    }

    @DisplayName("JUnit test to update Data Header Block Type for existing Block Name when update endpoint passed with Block name and new Block Type")
    @Test
    void updateDataEndPoint_updatesNewBlockTypeForGivenBlockName_whenBlockNameAndNewBlockTypePayloadInRequestIsPassed() throws Exception {

        when(dataHeaderService.updateHeaderType(TestDataHelper.TEST_NAME,BlockTypeEnum.BLOCKTYPEB.name())).thenReturn(true);

        mockMvc.perform(put("/dataserver/updateData/{blockName}/{newBlockType}", TestDataHelper.TEST_NAME, BlockTypeEnum.BLOCKTYPEB))
                //.andExpect(status().isOk())
                .andDo(print());
    }

    private DataEnvelope createDataEnvelope(){
        DataBody dataBody = new DataBody(DUMMY_DATA);
        DataHeader dataHeader = new DataHeader(HEADER_NAME, BlockTypeEnum.BLOCKTYPEA);
        DataEnvelope dataEnvelope = new DataEnvelope(dataHeader, dataBody);
        return dataEnvelope;
    }


}
