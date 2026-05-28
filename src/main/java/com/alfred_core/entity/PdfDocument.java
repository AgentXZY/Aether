package com.alfred_core.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PdfDocument {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pdfId;
	private String fileName;
	private String filePath;
	private Long fileSize;
	private LocalDateTime uploadedAt;
	private String extractedText;

	public PdfDocument() {
	}

	public Long getPdfId() {
		return pdfId;
	}

	public void setPdfId(Long pdfId) {
		this.pdfId = pdfId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public LocalDateTime getUploadedAt() {
		return uploadedAt;
	}

	public void setUploadedAt(LocalDateTime uploadedAt) {
		this.uploadedAt = uploadedAt;
	}

	public void setExtractedText(String extractedText) {
		this.extractedText = extractedText;
	}

	public String getExtractedText() {
		return extractedText;
	}
}