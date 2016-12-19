package com.andya.ilogostest;

import com.andya.ilogostest.model.Entries;
import com.andya.ilogostest.model.EntryRecord;
import com.andya.ilogostest.model.FileRecord;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by AndyA on 17.12.2016.
 */
public class JAXBTest
{


    @Test
    public void testMarshallUnmarshalCollectionsExample() throws JAXBException {

        final JAXBContext context = JAXBContext.newInstance(Entries.class);

        final EntryRecord record1 = new EntryRecord();
        record1.setId(1L);
        record1.setContent("Hello World!");
        record1.setCreationDate(new Date());

        final EntryRecord record2 = new EntryRecord();
        record2.setId(2L);
        record2.setContent("Hello Java!");
        record2.setCreationDate(new Date());

        final Entries records = new Entries();
        records.addEntry(record1);
        records.addEntry(record2);

        final FileRecord fileRecord = new FileRecord();
        fileRecord.setId(1L);
        fileRecord.setFilename("text.xml");
        fileRecord.setEntries(records.getEntries());


        final StringWriter writer = new StringWriter();
        // this is where we convert the object to XML
        context.createMarshaller().marshal(records, writer);

        System.out.println(writer.toString());

        // this is where we convert the XML to object
        final Entries fromXML = (Entries) context.createUnmarshaller().unmarshal(
                new StringReader(writer.toString()));

        System.out.println(fromXML.toString());

    }

}