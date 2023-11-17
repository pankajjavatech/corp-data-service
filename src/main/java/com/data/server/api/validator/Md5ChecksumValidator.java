package com.data.server.api.validator;

import com.data.server.api.model.DataEnvelope;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5ChecksumValidator {
    public static final String MD5_CHECKSUM = "cecfd3953783df706878aaec2c22aa70";
    public static boolean validateDataBlockData(final DataEnvelope dataEnvelop) throws NoSuchAlgorithmException {
        byte[] data = dataEnvelop.getDataBody().getDataBody().getBytes();
        byte[] hash = MessageDigest.getInstance("MD5").digest(data);
        String incomingDataBodyHash = DatatypeConverter.printHexBinary(hash);

         return MD5_CHECKSUM.equalsIgnoreCase(incomingDataBodyHash);
    }
}
