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
    @Query(value = "select seq, infoType, infoWithExternal, picture,orderTime from gfContactUsLog where gfContactUsId = :gfContactUsId and orderTime > :orderTime", nativeQuery = true)
    List<Map<String, Object>> getDistinctByGfContactUsIdAndOrderTime(String gfContactUsId, Long orderTime);


    @Modifying
    @Query(value = "select seq, infoType, infoWithExternal, picture, createTime,orderTime from gfContactUsLog where gfContactUsId = :gfContactUsId ", nativeQuery = true)
    List<Map<String, Object>> getByGfContactUsId( String gfContactUsId);


    List<GfContactUsLogEntity> findAllByGfContactUsIdEqualsAndOrderTimeAfter(String gfCustacUsId, Long orderByTime);


    GfContactUsLogEntity findFirstByGfContactUsIdEqualsOrderByCreateTimeDesc(String gfCustacUsId);





}
