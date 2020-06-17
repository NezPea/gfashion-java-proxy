package com.gfashion.data.repository.mysql;

import com.gfashion.data.GfContactUsLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GfContactUsLogRepository extends JpaRepository<GfContactUsLogEntity, String>{

    @Modifying
    @Query(value = "select seq, info_type as infoType, info_with_external as infoWithExternal, picture, order_time as orderTime from gf_contact_us_log where gf_contact_us_id = :gfContactUsId and order_time > :orderTime", nativeQuery = true)
    List<Map<String, Object>> getDistinctByGfContactUsIdAndOrderTime(String gfContactUsId, Long orderTime);


    @Modifying
    @Query(value = "select seq, info_type  as infoType, info_with_external as infoWithExternal, picture, create_time as createTime,order_time as orderTime from gf_contact_us_log where gf_contact_us_id = :gfContactUsId ", nativeQuery = true)
    List<Map<String, Object>> getByGfContactUsId( String gfContactUsId);


    List<GfContactUsLogEntity> findAllByGfContactUsIdEqualsAndOrderTimeAfter(String gfCustacUsId, Long orderByTime);


    GfContactUsLogEntity findFirstByGfContactUsIdEqualsOrderByCreateTimeDesc(String gfCustacUsId);





}
