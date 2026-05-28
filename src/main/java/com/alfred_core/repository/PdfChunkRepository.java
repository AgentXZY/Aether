package com.alfred_core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.alfred_core.entity.PdfChunk;

public interface PdfChunkRepository extends JpaRepository<PdfChunk, Long> {
    List<PdfChunk> findByDocumentId(Long documentId);
    List<PdfChunk> findByChunkTextContainingIgnoreCase(String query);
}