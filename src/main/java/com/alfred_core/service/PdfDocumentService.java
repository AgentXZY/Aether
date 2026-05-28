package com.alfred_core.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.alfred_core.entity.PdfChunk;
import com.alfred_core.entity.PdfDocument;
import com.alfred_core.repository.PdfChunkRepository;
import com.alfred_core.repository.PdfDocumentRepository;
import com.alfred_core.util.TextChunker;

import jakarta.transaction.Transactional;

@Service
public class PdfDocumentService {

	private final PdfDocumentRepository pdfRepo;

	private final PdfChunkRepository chunkRepo;

	public PdfDocumentService(PdfDocumentRepository pdfRepo,
	                          PdfChunkRepository chunkRepo) {
	    this.pdfRepo = pdfRepo;
	    this.chunkRepo = chunkRepo;
	}

	@Transactional
	public PdfDocument savePdf(MultipartFile file) throws IOException {

		String uploadPath = System.getProperty("user.dir") + "/pdf-archives/";

		File folder = new File(uploadPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		String filePath = uploadPath + file.getOriginalFilename();

		File destination = new File(filePath);
		file.transferTo(destination);

		PdfDocument pdf = new PdfDocument();

		pdf.setFileName(file.getOriginalFilename());
		pdf.setFilePath(destination.getAbsolutePath());
		pdf.setFileSize(file.getSize());
		pdf.setUploadedAt(LocalDateTime.now());

		String text = extractText(destination.getAbsolutePath());
		pdf.setExtractedText(text);

		// 1. SAVE PDF FIRST (IMPORTANT)
		PdfDocument savedPdf = pdfRepo.save(pdf);

		saveChunks(savedPdf.getPdfId(), text);

		return savedPdf;
	}

	public String extractText(String filePath) throws IOException {
		File file = new File(filePath);
		try (PDDocument document = PDDocument.load(file)) {
			PDFTextStripper stripper = new PDFTextStripper();
			return stripper.getText(document);
		}
	}

	public void saveChunks(Long documentId, String extractedText) {

		List<String> chunks = TextChunker.chunkText(extractedText, 500);

		int index = 0;

		for (String c : chunks) {

			PdfChunk chunk = new PdfChunk();
			chunk.setDocumentId(documentId);
			chunk.setChunkText(c);
			chunk.setChunkIndex(index++);
			chunk.setPageNumber(0); // optional for now

			chunkRepo.save(chunk);
		}
	}

	public List<PdfDocument> getAllPdfs() {
		return pdfRepo.findAll();
	}

}