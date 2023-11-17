package com.data.server.api.service;

import com.data.server.api.model.DataEnvelope;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface DataServerAsyncService {

    CompletableFuture<Boolean> pushBigData(DataEnvelope dataEnvelope) throws InterruptedException;
}
