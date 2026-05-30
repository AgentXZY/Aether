package com.alfred_core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alfred_core.dto.SearchResultDto;
import com.alfred_core.entity.PdfChunk;
import com.alfred_core.repository.PdfChunkRepository;

@Service
public class PdfSearchService {

    private final PdfChunkRepository chunkRepo;

    public PdfSearchService(PdfChunkRepository chunkRepo) {
        this.chunkRepo = chunkRepo;
    }

    public List<SearchResultDto> search(String query) {

        List<PdfChunk> chunks =
                chunkRepo.findByChunkTextContainingIgnoreCase(query);
        
      //Spring reads that method name and automatically builds this SQL:
      //	SELECT * FROM pdf_chunk 
      //	WHERE LOWER(chunk_text) LIKE LOWER('%your query%')

        return chunks.stream()
                .limit(5)
                .map(chunk -> new SearchResultDto(
                        chunk.getDocumentId(),
                        chunk.getChunkText()
                ))
                .toList();
    }
}