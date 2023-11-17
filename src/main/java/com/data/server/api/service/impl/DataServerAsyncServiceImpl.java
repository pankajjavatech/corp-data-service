package com.data.server.api.service.impl;

import com.data.server.api.model.DataEnvelope;
import com.data.server.api.service.DataServerAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class DataServerAsyncServiceImpl implements DataServerAsyncService {

    @Value("${client.datalake.push.data.url}")
    private String CLIENT_DATALAKE_PUSH_DATA_URL;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Async
    public CompletableFuture<Boolean> pushBigData(DataEnvelope dataEnvelope) throws InterruptedException {
        log.info("DataLake pushData starts");
        ResponseEntity<Boolean> response = restTemplate.postForEntity(CLIENT_DATALAKE_PUSH_DATA_URL, dataEnvelope.getDataBody().getDataBody(), Boolean.class);
        log.info("DataLake Response Status {}", response.getBody());
        log.info("DataLake pushData Response, {}", response);
        log.info("DataLake pushData  completed");
        return CompletableFuture.completedFuture(response.getBody());
    }


}
