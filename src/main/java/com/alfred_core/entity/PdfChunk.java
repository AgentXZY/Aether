package com.alfred_core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PdfChunk {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long documentId;

	@Column(columnDefinition = "TEXT") //to be stored as a TEXT type in SQL, instead of a limited-length VARCHAR.
	private String chunkText;

	private int chunkIndex;

	private int pageNumber;

	public Long getId() {
		return id;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public String getChunkText() {
		return chunkText;
	}

	public int getChunkIndex() {
		return chunkIndex;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public void setChunkText(String chunkText) {
		this.chunkText = chunkText;
	}

	public void setChunkIndex(int chunkIndex) {
		this.chunkIndex = chunkIndex;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}