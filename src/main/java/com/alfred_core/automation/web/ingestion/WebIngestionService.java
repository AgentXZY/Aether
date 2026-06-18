package com.alfred_core.automation.web.ingestion;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.alfred_core.automation.web.scraping.WebPage;
import com.alfred_core.service.EmbeddingService;
import com.alfred_core.util.TextChunker;

@Service
public class WebIngestionService {

	private final EmbeddingService embeddingService;
	
	public WebIngestionService(EmbeddingService embeddingService) {
		super();
		this.embeddingService = embeddingService;
	}
	
	public List<WebChunk> ingest(WebPage page) {

	    long totalStart = System.currentTimeMillis();

	    List<String> chunks =
	            TextChunker.chunkText(
	                    page.getContent(),
	                    300
	            );

	    System.out.println("Chunks created: " + chunks.size());

	    List<WebChunk> result = new ArrayList<>();

	    int i = 1;

	    for (String chunkText : chunks) {

	        long start = System.currentTimeMillis();

	        List<Double> embedding =
	                embeddingService.generateEmbedding(chunkText);
//	        		List.of();

	        long end = System.currentTimeMillis();

	        System.out.println(
	                "Chunk " + i +
	                " embedding time = " +
	                (end - start) + " ms"
	        );

	        WebChunk chunk = new WebChunk();

	        chunk.setSourceUrl(page.getUrl());
	        chunk.setText(chunkText);
	        chunk.setEmbedding(embedding);

	        result.add(chunk);

	        i++;
	    }

	    long totalEnd = System.currentTimeMillis();

	    System.out.println(
	            "TOTAL TIME = " +
	            (totalEnd - totalStart) + " ms"
	    );

	    return result;
	}
	
}
