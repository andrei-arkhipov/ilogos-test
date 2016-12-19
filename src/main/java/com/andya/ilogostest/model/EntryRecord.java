package com.andya.ilogostest.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;


/**
 * Created by AndyA on 15.12.2016.
 */
@Entity
@Table(name = "entries")
public class EntryRecord implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name="content")
    private String content;

    @Column(name="creation_date")
    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file", nullable = false)
    private FileRecord fileRecord;

    public EntryRecord() {
        this.content = "";
        this.creationDate = new Date();
    }
    public EntryRecord(String content, Date creationDate) {
        this.content = content;
        this.creationDate = creationDate;
    }

    public EntryRecord(String content, Date creationDate, FileRecord fileRecord) {
        this(content, creationDate);
        this.fileRecord = fileRecord;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @XmlElement(name="content")
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    @XmlElement(name="creationDate")
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    public FileRecord getFileRecord() { return fileRecord; }
    public void setFileRecord(FileRecord fileRecord) { this.fileRecord = fileRecord; }

    @Override
    public String toString() {
        return "EntryRecord{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                ", fileRecord=" + fileRecord +
                '}';
    }
}
