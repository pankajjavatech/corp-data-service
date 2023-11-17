package com.data.server.api.persistence.repository;

import com.data.server.api.persistence.BlockTypeEnum;
import com.data.server.api.persistence.model.DataHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataHeaderRepository extends JpaRepository<DataHeaderEntity, Long> {
    Optional<List<DataHeaderEntity>> findByBlocktype(BlockTypeEnum blockType);
    Optional<DataHeaderEntity> findByName(String name);

}
