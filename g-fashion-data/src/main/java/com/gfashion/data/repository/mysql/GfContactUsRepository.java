package com.gfashion.data.repository.mysql;

import com.gfashion.data.GfContactUsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface GfContactUsRepository extends JpaRepository<GfContactUsEntity, String>, JpaSpecificationExecutor<GfContactUsEntity> {


    @Query(value = "select * from gf_contact_us where gf_contact_us_id = :contactId", nativeQuery = true)
    GfContactUsEntity getOneById(String contactId);


}
