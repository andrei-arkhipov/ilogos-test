package com.andya.ilogostest.model;


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * Created by AndyA on 15.12.2016.
 *
 * Helper class for top element of entries xml file.
 */
@XmlRootElement(name = "Entries")
public class Entries
{
    private List<EntryRecord> entries = new ArrayList<>();

    @XmlElements(value = {@XmlElement(name = "Entry", type = EntryRecord.class)})
    public List<EntryRecord> getEntries() {
        return entries;
    }

    public void addEntry(EntryRecord entryRecord) {
        entries.add(entryRecord);
    }
}