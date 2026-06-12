package com.alfred_core.testcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test-minilm")
public class MiniLMTestController {

    private final RestTemplate restTemplate;

    // --- INTENT EXAMPLES ---
    // These are the reference phrases for each intent
    // MiniLM will compare your query against these
    private static final Map<String, List<String>> EXAMPLES = Map.of(

        "WEB_SEARCH", List.of(
        	"latest news",
        	"recent updates",
        	"find information",
        	"search internet",
        	"look up online",
        	"research this topic",
        	"latest developments",
        	"what's new in technology",
        	"show me current trends",
        	"find product reviews"
        ),

        "FILE_SYSTEM", List.of(
        	"create folder",
        	"create directory",
        	"make workspace",
        	"build project structure",
        	"scaffold application",
        	"generate project structure"
        ),

        "OPEN_APP", List.of(
            "open chrome",
            "launch vscode",
            "start spotify",
            "open application",
            "run program"
        ),

        "RAG_CHAT", List.of(
            "what is deadlock",
            "explain polymorphism",
            "tell me about threads",
            "how does java work",
            "describe operating system"
        ),
        
        "MEMORY_STORE", List.of(
        	"my favorite movie is",
            "i like programming",
       	    "remember that i prefer",
       	    "store this about me",
       	    "my birthday is june",
       	    "i enjoy science fiction",
       	    "i love batman",
       	    "keep this in mind about me",
       	    "note that i usually",
       	    "my name is",
            "i prefer dark themes"
        ),

        "MEMORY_RECALL", List.of(
        	"what was my favorite movie",
        	"what did i tell you",
        	"what project did i choose",
        	"do you remember what i said",
        	"recall what i told you",
        	"what do you know about me",
            "what was the name i mentioned",
       	    "remind me what i decided",
       	    "what did i say earlier",
       	    "can you recall my preference"
        )
       );

    public MiniLMTestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // STEP 1: Just test if all-minilm:33m generates embeddings at all
    @GetMapping("/ping")
    public String ping(@RequestParam String text) {
        try {
            List<Double> embedding = getEmbedding(text);
            return "all-minilm:33m works. Vector size = " + embedding.size()
                   + " | First 5 values: " + embedding.subList(0, 5);
        } catch (Exception e) {
            return "FAILED: " + e.getMessage();
        }
    }

    // STEP 2: Classify a query and see similarity scores against all intent examples
    @GetMapping("/classify")
    public List<String> classify(@RequestParam String q) {

        List<Double> queryEmbedding = getEmbedding(q);

        List<String> results = new ArrayList<>();
        results.add("Query: " + q);
        results.add("---");

        for (Map.Entry<String, List<String>> entry : EXAMPLES.entrySet()) {

            String intentName = entry.getKey();
            List<String> examples = entry.getValue();

            // find best matching example for this intent
            double bestScore = 0.0;
            String bestExample = "";

            for (String example : examples) {
                List<Double> exampleEmbedding = getEmbedding(example);
                double score = cosineSimilarity(queryEmbedding, exampleEmbedding);
                if (score > bestScore) {
                    bestScore = score;
                    bestExample = example;
                }
            }

            results.add(intentName + " → score: "
                + String.format("%.4f", bestScore)
                + " (best match: \"" + bestExample + "\")");
        }

        return results;
    }

    // STEP 3: Get the winner intent only
    @GetMapping("/intent")
    public String intent(@RequestParam String q) {

        List<Double> queryEmbedding = getEmbedding(q);

        String bestIntent = "RAG_CHAT";
        double bestScore = 0.0;

        for (Map.Entry<String, List<String>> entry : EXAMPLES.entrySet()) {

            String intentName = entry.getKey();

            for (String example : entry.getValue()) {
                List<Double> exampleEmbedding = getEmbedding(example);
                double score = cosineSimilarity(queryEmbedding, exampleEmbedding);
                if (score > bestScore) {
                    bestScore = score;
                    bestIntent = intentName;
                }
            }
        }

        return "Intent: " + bestIntent + " | Confidence: " + String.format("%.4f", bestScore);
    }

    // --- HELPERS ---

    @SuppressWarnings("unchecked")
    private List<Double> getEmbedding(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
            "model", "all-minilm:33m",
            "prompt", text
        );

        Map response = restTemplate.postForObject(
            "http://localhost:11434/api/embeddings",
            new HttpEntity<>(body, headers),
            Map.class
        );

        return (List<Double>) response.get("embedding");
    }

    private double cosineSimilarity(List<Double> a, List<Double> b) {
        double dot = 0, normA = 0, normB = 0;
        for (int i = 0; i < a.size(); i++) {
            dot   += a.get(i) * b.get(i);
            normA += a.get(i) * a.get(i);
            normB += b.get(i) * b.get(i);
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}