package com.gfashion.data.repository.mysql;

import com.gfashion.data.GfCustomServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GfCustomServiceRepository extends JpaRepository<GfCustomServiceEntity, String>, JpaSpecificationExecutor<GfCustomServiceEntity> {


        @Query(value = "select * from gfCustomService where serviceId in (:serviceId)", nativeQuery = true)
        List<Map<String,Object>> getByServiceIdIn(List<String> serviceId);
}
