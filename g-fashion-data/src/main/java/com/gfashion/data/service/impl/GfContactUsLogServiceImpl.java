package com.gfashion.data.service.impl;

import com.gfashion.data.GfContactUsLogEntity;
import com.gfashion.data.repository.mysql.GfContactUsLogRepository;
import com.gfashion.data.service.GfContactUsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GfContactUsLogServiceImpl implements GfContactUsLogService {

    @Autowired
    private GfContactUsLogRepository gfContactUsLogRepository;





}
