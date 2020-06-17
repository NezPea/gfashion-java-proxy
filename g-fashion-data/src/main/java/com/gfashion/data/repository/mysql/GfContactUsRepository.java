package com.gfashion.data.repository.mysql;

import com.gfashion.data.GfContactUsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GfContactUsRepository extends JpaRepository<GfContactUsEntity, String>, JpaSpecificationExecutor<GfContactUsEntity> {


    @Query(value = "select * from gfContactUs where gfContactUsId = :contactId", nativeQuery = true)
    GfContactUsEntity getOneById(String contactId);

/*
    @Query(value = "select  gcu.gfContactUsId, gcu.gfCustomerId,gcu.gfOrderId," +
            "gcu.serviceId,gcu.contentOfInquiry,gcu.updateTime," +
            "gcu.brandId,gcu.brandName,gcu.isRead,gcu.customName " +
            "from gfContactUs gcu where gcu.serviceId = :serviceId limit :fromNum, 10", nativeQuery = true)
    List<Map<String,Object>> selectByServiceId(String serviceId, Integer fromNum);


    @Query(value = "select  gcu.gfContactUsId, gcu.gfCustomerId,gcu.gfOrderId," +
            "gcu.serviceId,gcu.contentOfInquiry,gcu.updateTime," +
            "gcu.brandId,gcu.brandName,gcu.isRead,gcu.customName " +
            "from gfContactUs gcu where gcu.serviceId = :serviceId " +
            "and gcu.brandName like :brandName and gcu.gfOrderId like :gfOrderId and gcu.customName like :customName" +
            " limit :fromNum, 10", nativeQuery = true)
    List<Map<String,Object>> selectByServiceIdLike(String serviceId, String brandName, String gfOrderId, String customName, Integer fromNum);


    @Query(value = "select  gcu.gfContactUsId, gcu.gfCustomerId,gcu.gfOrderId," +
            "gcu.serviceId,gcu.contentOfInquiry,gcu.updateTime," +
            "gcu.brandId,gcu.brandName,gcu.isRead,gcu.customName " +
            "from gfContactUs gcu limit :fromNum, 10", nativeQuery = true)
    List<Map<String,Object>> selectByAll(Integer fromNum);

    @Query(value = "select  gcu.gfContactUsId, gcu.gfCustomerId,gcu.gfOrderId," +
            "gcu.serviceId,gcu.contentOfInquiry,gcu.updateTime," +
            "gcu.brandId,gcu.brandName,gcu.isRead,gcu.customName " +
            "from gfContactUs gcu where gcu. limit :fromNum, 10", nativeQuery = true)
    List<Map<String,Object>> selectByAllWithLanguage(String language, Integer fromNum);*/

}
