package com.alfred_core.service;

import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import com.alfred_core.dto.SearchResultDto;
import com.alfred_core.entity.PdfChunk;
import com.alfred_core.repository.PdfChunkRepository;

@Service
public class SemanticSearchService {

    private final PdfChunkRepository chunkRepo;
    private final EmbeddingService embeddingService;

    public SemanticSearchService(
            PdfChunkRepository chunkRepo,
            EmbeddingService embeddingService) {

        this.chunkRepo = chunkRepo;
        this.embeddingService = embeddingService;
    }

    public List<SearchResultDto> search(String question) {

        List<Double> queryEmbedding =
                embeddingService.generateEmbedding(question);

        return chunkRepo.findAll()
                .stream()

                .sorted((c1, c2) -> {

                    double score1 =
                            embeddingService.cosineSimilarity(
                                    queryEmbedding,
                                    embeddingService.stringToEmbedding(
                                            c1.getEmbedding()
                                    )
                            );

                    double score2 =
                            embeddingService.cosineSimilarity(
                                    queryEmbedding,
                                    embeddingService.stringToEmbedding(
                                            c2.getEmbedding()
                                    )
                            );

                    return Double.compare(score2, score1);
                })

                .limit(5)

                .map(chunk -> new SearchResultDto(
                        chunk.getDocumentId(),
                        chunk.getChunkText()
                ))

                .toList();
    }
}