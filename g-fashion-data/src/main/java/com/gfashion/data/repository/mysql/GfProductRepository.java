package com.gfashion.data.repository.mysql;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface GfProductRepository extends CrudRepository<GfProductMySqlEntity, Integer> {
    GfProductMySqlEntity findById(String id);
    GfProductMySqlEntity deleteById(String id);
    Iterable<GfProductMySqlEntity> findByName(String name);
}