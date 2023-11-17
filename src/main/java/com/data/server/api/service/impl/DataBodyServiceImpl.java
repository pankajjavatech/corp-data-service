package com.data.server.api.service.impl;

import com.data.server.api.exception.ResourceNotFoundException;
import com.data.server.api.persistence.BlockTypeEnum;
import com.data.server.api.persistence.model.DataBodyEntity;
import com.data.server.api.persistence.model.DataHeaderEntity;
import com.data.server.api.persistence.repository.DataHeaderRepository;
import com.data.server.api.persistence.repository.DataStoreRepository;
import com.data.server.api.service.DataBodyService;
import com.data.server.api.service.DataHeaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataBodyServiceImpl implements DataBodyService {


    @Autowired
    private DataStoreRepository dataStoreRepository;

    @Autowired
    private DataHeaderService dataHeaderService;

    @Override
    public void saveDataBody(DataBodyEntity dataBody) {
        dataStoreRepository.save(dataBody);
    }

    @Override
    public List<DataBodyEntity> getDataByBlockType(BlockTypeEnum blockType) throws ResourceNotFoundException {

        List<DataHeaderEntity> dataHeaderEntities = dataHeaderService.findByBlockTypeEnum(blockType);
        log.info("DataBodyServiceImpl dataHeaderEntities size{}", dataHeaderEntities.size());

        if (!CollectionUtils.isEmpty(dataHeaderEntities)) {

            List<DataBodyEntity>  dataBodyEntityList = dataHeaderEntities
                    .stream()
                    .map(dataHeaderEntity -> dataStoreRepository.findByDataHeaderEntity(dataHeaderEntity))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

            log.info("DataBodyServiceImpl dataBodyEntityList size{}", dataBodyEntityList.size());
            return dataBodyEntityList;
        }

        return null;
    }

    @Override
    public Optional<DataBodyEntity> getDataByBlockName(String blockName) {
        return null;
    }
}
