package com.andya.ilogostest.service;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.andya.ilogostest.CustomExceptionRootMessage;


/**
 * Created by AndyA on 15.12.2016.
 */
@Service
public class SchedulerServiceImpl implements SchedulerService {
    private static final Logger LOG = Logger.getLogger(SchedulerServiceImpl.class);

    @Autowired
    private Environment env;

    @Autowired
    AsyncTask asyncTask;

    @Override
    @Scheduled(fixedDelayString="${schedule.interval.ms}")
    public void importData() {
        // Directory where the files are located
        Path directory = Paths.get(env.getProperty("unprocessed.dir"));
        try {
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    LOG.info("processing file " + file);
                    try {
                        if (Files.isSymbolicLink(file)) {
                            file = Files.readSymbolicLink(file);
                        }
                        asyncTask.startProcessing(file.toFile());
                    } catch (IOException | SecurityException ex) {
                        LOG.error("Cannot obtain file", ex);
                    }
                    return FileVisitResult.CONTINUE;
                }

                public FileVisitResult visitFileFailed (Path file, IOException exc) throws IOException {
                    if (exc instanceof FileSystemLoopException) {
                        LOG.error("Circular symlink detected: " + file.toString());
                    }
                    else if (exc != null) {
                        throw exc;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ie) {
            LOG.error("Caught IO exception: " + CustomExceptionRootMessage.getCause(ie));
        }

    }


}

