package com.gfashion.data.service.impl;

import com.gfashion.data.GfCustomServiceEntity;
import com.gfashion.data.repository.mysql.GfCustomServiceRepository;
import com.gfashion.data.service.GfCustomServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GfCustomServiceServiceImpl implements GfCustomServiceService {

    @Autowired
    private GfCustomServiceRepository serviceRepository;


    @Override
    public GfCustomServiceEntity getInfoById(String serviceId){
        GfCustomServiceEntity entity = serviceRepository.getOne(serviceId);
        if(null == entity){
            entity = new GfCustomServiceEntity();
            entity.setServiceId(serviceId);
            serviceRepository.save(entity);
        }
        return entity;
    }

    @Override
    public GfCustomServiceEntity updateName(String serviceId, String name) {

        GfCustomServiceEntity entity =GfCustomServiceEntity.builder().serviceId(serviceId).name(name).build();
        serviceRepository.save(entity);

        return entity;
    }
}
