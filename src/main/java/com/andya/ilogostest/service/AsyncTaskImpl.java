package com.andya.ilogostest.service;

import com.andya.ilogostest.CustomExceptionRootMessage;
import com.andya.ilogostest.model.EntryRecord;
import com.andya.ilogostest.model.FileRecord;
import com.andya.ilogostest.repository.EntryRecordRepository;
import com.andya.ilogostest.repository.FileRecordRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndyA on 18.12.2016.
 */
@Service
public class AsyncTaskImpl implements AsyncTask {
    private static final Logger LOG = Logger.getLogger(AsyncTaskImpl.class);

    @Value("${finished.dir}")
    private String finishedPath;

    @Value("${failed.dir}")
    private String errorPath;

    @Autowired
    EntryRecordRepository entryRecordService;

    @Autowired
    FileRecordRepository fileRecordService;

    @Autowired
    XmlParserService parserService;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void startProcessing(final File file) {
        LOG.info("Running task on thread: " + Thread.currentThread().getName());

        try {
            try {
                List<EntryRecord> entryRecordList = retrieveRecords(file);
                if( entryRecordList != null ) {
                    FileRecord fileRecord = new FileRecord(file.getName());
                    fileRecord.setEntries(entryRecordList != null ? entryRecordList : new ArrayList<>());
                    fileRecordService.save(fileRecord);
                }
            } catch (DataAccessException de) {
                LOG.error("Caught database exception during xml parsing : " + CustomExceptionRootMessage.getCause(de));
                LOG.info("will try to process file " + file.getName() + " again later");
            }

            LOG.info("moving file " + file.getName() + " to finished directory");
            Files.move(file.toPath(), Paths.get(getSuccessFileName(file)), StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException ie) {
            LOG.error("Caught IO exception check file permissions etc: " + CustomExceptionRootMessage.getCause(ie));
        }
    }

    List<EntryRecord> retrieveRecords(final File file) throws IOException {
        List<EntryRecord> result = null;
        try {
            result = parserService.parseXml(file);
        } catch (JAXBException e) {
            LOG.error("Caught exception during xml parsing : " + CustomExceptionRootMessage.getCause(e));
            LOG.info("moving file " + file.getName() + " to failed directory");
            Files.move(file.toPath(), Paths.get(getFailedFileName(file)), StandardCopyOption.ATOMIC_MOVE);
        }
        return result;
    }

    protected String getFailedFileName(File file) {
        return this.errorPath + File.separator + file.getName();
    }

    protected String getSuccessFileName(File file) {
        return this.finishedPath + File.separator + file.getName();
    }
}
