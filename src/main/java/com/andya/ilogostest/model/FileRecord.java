package com.andya.ilogostest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;


/**
 * Created by AndyA on 15.12.2016.
 */
@Entity
@Table(name = "files")
public class FileRecord implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="filename")
    private String filename;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fileRecord",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<EntryRecord> entries = new ArrayList<>();

    public FileRecord() {
        this.filename = "";
    }
    public FileRecord(String filename) {
        this.filename = filename;
    }

    public FileRecord(String filename, List<EntryRecord> entries) {
        this(filename);
        this.entries = entries;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }

    public List<EntryRecord> getEntries() { return entries; }
    public void setEntries(Collection<EntryRecord> entries) {
        this.entries = new ArrayList<>(entries);
        for (EntryRecord entry : this.entries) {
            entry.setFileRecord(this);
        }
    }
}
