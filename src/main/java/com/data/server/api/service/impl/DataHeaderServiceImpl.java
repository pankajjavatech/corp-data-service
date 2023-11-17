package com.data.server.api.service.impl;

import com.data.server.api.exception.ResourceNotFoundException;
import com.data.server.api.persistence.BlockTypeEnum;
import com.data.server.api.persistence.model.DataHeaderEntity;
import com.data.server.api.persistence.repository.DataHeaderRepository;
import com.data.server.api.service.DataHeaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataHeaderServiceImpl implements DataHeaderService {

    @Autowired
    private DataHeaderRepository dataHeaderRepository;

    @Override
    public void saveHeader(DataHeaderEntity entity) {
        dataHeaderRepository.save(entity);
    }

    @Override
    public List<DataHeaderEntity> findByBlockTypeEnum(BlockTypeEnum blockType) throws ResourceNotFoundException {
        log.info("DataHeaderServiceImpl blockType.name() {}", blockType.name());
        return dataHeaderRepository.findByBlocktype(blockType).orElseThrow(() -> new ResourceNotFoundException("Data Header Entity not found not found for this blockType :: " + blockType.name()));
    }

    @Override
   // @Transactional
    public Boolean updateHeaderType(String name, String blockType) throws ResourceNotFoundException {
        log.info("DataHeaderServiceImpl name {}", name);
        DataHeaderEntity dataHeaderEntity = dataHeaderRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Data Header not found for this name :: " + name));
        log.info("DataHeaderServiceImpl dataHeaderEntity {}", dataHeaderEntity);

        try {
            if (dataHeaderEntity != null) {
                dataHeaderEntity.setBlocktype(BlockTypeEnum.getBlockByType(blockType).get());
                saveHeader(dataHeaderEntity);
                log.info("DataHeaderServiceImpl dataHeaderEntity {}", dataHeaderEntity);
                return true;
            }
        } catch (RuntimeException ex) {
            log.info("DataHeaderServiceImpl updateHeaderType error {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }

        return false;
    }

}


