package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.LogEntity;

public interface DDBLogRepository {

    LogEntity createGfLogEntity(LogEntity logEntity);

    LogEntity readGfLogEntityById(String logEntityId);

    LogEntity updateGfLogEntity(LogEntity logEntity);

    void deleteGfLogEntity(String logEntityId);
}
