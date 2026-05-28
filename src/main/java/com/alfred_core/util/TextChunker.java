package com.alfred_core.util;

import java.util.ArrayList;
import java.util.List;

public class TextChunker {

    public static List<String> chunkText(String text, int maxWords) {

        String[] words = text.split("\\s+");
        List<String> chunks = new ArrayList<>();

        StringBuilder chunk = new StringBuilder();
        int count = 0;

        for (String word : words) {
            chunk.append(word).append(" ");
            count++;

            if (count >= maxWords) {
                chunks.add(chunk.toString().trim());
                chunk = new StringBuilder();
                count = 0;
            }
        }

        if (chunk.length() > 0) {
            chunks.add(chunk.toString().trim());
        }

        return chunks;
    }
}