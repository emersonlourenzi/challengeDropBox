package com.challange.impl.upload.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

//@Repository
public interface UploadRepository extends MongoRepository<UploadEntity, String> {
}
