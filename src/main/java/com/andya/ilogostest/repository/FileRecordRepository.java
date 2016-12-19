package com.andya.ilogostest.repository;

import org.springframework.data.repository.CrudRepository;

import com.andya.ilogostest.model.FileRecord;
import org.springframework.stereotype.Repository;


/**
 * Created by AndyA on 15.12.2016.
 */
@Repository
public interface FileRecordRepository extends CrudRepository<FileRecord, Long> {
}
