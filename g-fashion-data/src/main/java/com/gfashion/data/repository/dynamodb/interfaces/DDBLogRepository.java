package com.gfashion.data.repository.dynamodb.interfaces;

import com.gfashion.data.repository.dynamodb.entity.LogEntity;

public interface DDBLogRepository {

    LogEntity createGfLogEntity(LogEntity logEntity);

    LogEntity readGfLogEntityById(String logEntityId);

    LogEntity updateGfLogEntity(LogEntity logEntity);

    void deleteGfLogEntity(String logEntityId);
}
