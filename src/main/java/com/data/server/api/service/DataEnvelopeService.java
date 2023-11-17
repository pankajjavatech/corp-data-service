package com.data.server.api.service;

import com.data.server.api.model.DataEnvelope;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface DataEnvelopeService {
    boolean saveDataEnvelope(DataEnvelope envelope) throws IOException, NoSuchAlgorithmException;
}
