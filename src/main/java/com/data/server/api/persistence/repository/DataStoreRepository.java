package com.data.server.api.persistence.repository;

import com.data.server.api.persistence.model.DataBodyEntity;
import com.data.server.api.persistence.model.DataHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataStoreRepository extends JpaRepository<DataBodyEntity, Long> {
    List<DataBodyEntity> findByDataHeaderEntity(DataHeaderEntity headerId);

}
