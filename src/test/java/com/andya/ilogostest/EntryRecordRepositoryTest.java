package com.andya.ilogostest;

/**
 * Created by AndyA on 17.12.2016.
 */
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.andya.ilogostest.config.AppConfig;
import com.andya.ilogostest.model.FileRecord;
import com.andya.ilogostest.repository.FileRecordRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.andya.ilogostest.repository.EntryRecordRepository;
import com.andya.ilogostest.model.EntryRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= AppConfig.class)
public class EntryRecordRepositoryTest {

    @Autowired
    EntryRecordRepository entryRecordRepository;

    @Autowired
    FileRecordRepository fileRecordRepository;
    @Test
    public void saveEntry() {
        FileRecord fileRecord = fileRecordRepository.save(new FileRecord("test-file.xml"));
        assertNotNull(fileRecord);
        EntryRecord entryRecord = entryRecordRepository.save(new EntryRecord("Test entry content", new Date(), fileRecord));
        assertNotNull(entryRecord);
        assertEquals("Test entry content", entryRecord.getContent());
    }

}