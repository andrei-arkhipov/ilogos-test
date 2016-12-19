package com.andya.ilogostest.service;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.andya.ilogostest.model.EntryRecord;


/**
 * Created by AndyA on 18.12.2016.
 */
public interface XmlParserService {
    List<EntryRecord> parseXml(File file) throws IOException, JAXBException;
}
