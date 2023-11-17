package com.data.server.api.service;

import com.data.server.api.exception.ResourceNotFoundException;
import com.data.server.api.persistence.BlockTypeEnum;
import com.data.server.api.persistence.model.DataHeaderEntity;

import java.util.List;
import java.util.Optional;

public interface DataHeaderService {
    void saveHeader(DataHeaderEntity entity);
    List<DataHeaderEntity> findByBlockTypeEnum(BlockTypeEnum blockType) throws ResourceNotFoundException;
    Boolean updateHeaderType(String name, String blockType) throws ResourceNotFoundException;
}
