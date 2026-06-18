package com.alfred_core.automation.web.research;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alfred_core.automation.web.ingestion.WebChunk;
import com.alfred_core.service.EmbeddingService;

@Service
public class WebSemanticSearchService {

	private final EmbeddingService embeddingService;

	public WebSemanticSearchService(EmbeddingService embeddingService) {
		this.embeddingService = embeddingService;
	}

	public List<WebChunk> search(String query, List<WebChunk> chunks) {

		List<Double> queryEmbedding = embeddingService.generateEmbedding(query);

		return chunks.stream()

				.sorted((c1, c2) -> {

					double score1 = embeddingService.cosineSimilarity(queryEmbedding, c1.getEmbedding());

					double score2 = embeddingService.cosineSimilarity(queryEmbedding, c2.getEmbedding());

					return Double.compare(score2, score1);
				})

				.limit(5)

				.collect(Collectors.toList());
	}
}