package com.alfred_core.intent;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class IntentRouterService {

    private final MiniLMEmbeddingService embeddingService;
    private final IntentDatasetService datasetService;

    public IntentRouterService(
            MiniLMEmbeddingService embeddingService,
            IntentDatasetService datasetService) {

        this.embeddingService = embeddingService;
        this.datasetService = datasetService;
    }

    public IntentResult route(String message) {

        List<Double> queryEmbedding =
                embeddingService.embed(message);

        IntentType bestIntent = IntentType.UNKNOWN;
        double bestScore = 0.0;

        Map<String, List<String>> intents =
                datasetService.getIntents();

        for (Map.Entry<String, List<String>> entry : intents.entrySet()) {

            String intentName = entry.getKey();
            List<String> examples = entry.getValue();

            IntentType currentIntent;

            try {
                currentIntent = IntentType.valueOf(intentName);
            } catch (IllegalArgumentException e) {
                continue;
            }

            for (String example : examples) {

                List<Double> exampleEmbedding =
                        embeddingService.embed(example);

                double score =
                        CosineSimilarityUtil.calculate(
                                queryEmbedding,
                                exampleEmbedding
                        );

                if (score > bestScore) {
                    bestScore = score;
                    bestIntent = currentIntent;
                }
            }
        }

        System.out.println(
                "Query: " + message +
                " | Intent: " + bestIntent +
                " | Confidence: " +
                String.format("%.4f", bestScore)
        );

        return new IntentResult(
                bestIntent,
                message
        );
    }
}