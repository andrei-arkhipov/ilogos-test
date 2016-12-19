package com.andya.ilogostest.service;

import com.andya.ilogostest.model.Entries;
import com.andya.ilogostest.model.EntryRecord;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndyA on 18.12.2016.
 */
@Service
public class XmlParserServiceImpl implements XmlParserService {

    @Override
    public List<EntryRecord> parseXml(File file) throws IOException, JAXBException {
        InputStream inputStream = null;
        List<EntryRecord> entryRecordList = new ArrayList<>();

        try {
            JAXBContext context = JAXBContext.newInstance(Entries.class);
            Unmarshaller um = context.createUnmarshaller();
            inputStream = new FileInputStream(file);
            Entries entries = (Entries) um.unmarshal(inputStream);
            entryRecordList = entries.getEntries();
        } finally {
            if(inputStream != null) {
                inputStream.close();
            }
        }

        return entryRecordList;
    }
}
