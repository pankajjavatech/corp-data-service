package com.data.server.api.service;

import com.data.server.api.exception.ResourceNotFoundException;
import com.data.server.api.persistence.model.DataBodyEntity;
import com.data.server.api.persistence.BlockTypeEnum;

import java.util.List;
import java.util.Optional;

public interface DataBodyService {
    void saveDataBody(DataBodyEntity dataBody);
    List<DataBodyEntity> getDataByBlockType(BlockTypeEnum blockType) throws ResourceNotFoundException;
    Optional<DataBodyEntity> getDataByBlockName(String blockName);
}
