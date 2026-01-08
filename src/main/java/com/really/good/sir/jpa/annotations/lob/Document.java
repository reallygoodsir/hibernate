package com.really.good.sir.jpa.annotations.lob;

import javax.persistence.*;

@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    @Column(name = "text_content")
    private String textContent; // CLOB

    @Lob
    @Column(name = "binary_content")
    private byte[] binaryContent; // BLOB

    public Document() {}

    public Document(String name, String textContent, byte[] binaryContent) {
        this.name = name;
        this.textContent = textContent;
        this.binaryContent = binaryContent;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getTextContent() { return textContent; }
    public byte[] getBinaryContent() { return binaryContent; }

    public void setName(String name) { this.name = name; }
    public void setTextContent(String textContent) { this.textContent = textContent; }
    public void setBinaryContent(byte[] binaryContent) { this.binaryContent = binaryContent; }
}
