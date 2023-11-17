package com.data.server.api.controller;

import com.data.server.api.exception.ResourceNotFoundException;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/dataserver")
@RequiredArgsConstructor
@Validated
public class DataServerController {

    @Autowired
    private DataEnvelopeService dataEnvelopeService;

    @Autowired
    private DataHeaderService dataHeaderService;

    @Autowired
    private DataBodyService dataBodyService;

    @Autowired
    private DataServerAsyncService dataServerAsyncService;


    @PostMapping(value = "/pushdata", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> pushData(@Valid @RequestBody DataEnvelope dataEnvelope) throws IOException, NoSuchAlgorithmException, InterruptedException {

        log.info("Data envelope received: {}", dataEnvelope.getDataHeader().getName());
        boolean checksumPass = dataEnvelopeService.saveDataEnvelope(dataEnvelope);


        if (checksumPass) {
            dataServerAsyncService.pushBigData(dataEnvelope);
        }

        log.info("Data envelope persisted. Attribute name: {}", dataEnvelope.getDataHeader().getName());
        return ResponseEntity.ok(checksumPass);
    }

    @GetMapping("/getData/{blockType}")
    public ResponseEntity<List<DataEnvelope>> getDataByBlockType(@PathVariable String blockType) throws ResourceNotFoundException {
        log.info("Query for data with header block type {}", blockType);


        List<DataBodyEntity> DataBodyEntities = dataBodyService.getDataByBlockType(BlockTypeEnum.getBlockByType(blockType).get());

        List<DataEnvelope> dataEnvelopeList = getDataEnvelopes(DataBodyEntities);

        log.info("dataEnvelopeList {}", dataEnvelopeList.size());

        if (!CollectionUtils.isEmpty(dataEnvelopeList)) {
            return new ResponseEntity<>(dataEnvelopeList, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/updateData/{blockName}/{newBlockType}")
    private ResponseEntity<Boolean> updateData(@PathVariable String blockName, @PathVariable String newBlockType) throws UnsupportedEncodingException, ResourceNotFoundException {
        log.info("DataServerController updateData: {} newBlockType: {}", blockName, newBlockType);
        Boolean isUpdated = dataHeaderService.updateHeaderType(blockName, newBlockType);
        if (isUpdated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(isUpdated, HttpStatus.NOT_FOUND);
    }

    private static List<DataEnvelope> getDataEnvelopes(List<DataBodyEntity> DataBodyEntities) {
        List<DataEnvelope> dataEnvelopeList = new ArrayList<DataEnvelope>();

        DataBodyEntities.forEach((dataBodyEntities) -> {

            DataBody dataBody = new DataBody(dataBodyEntities.getDataBody());

            DataHeader dataHeader = new DataHeader(dataBodyEntities.getDataHeaderEntity().getName(), dataBodyEntities.getDataHeaderEntity().getBlocktype());

            DataEnvelope dataEnvelope = new DataEnvelope(dataHeader, dataBody);

            dataEnvelopeList.add(dataEnvelope);

        });
        return dataEnvelopeList;
    }

}
