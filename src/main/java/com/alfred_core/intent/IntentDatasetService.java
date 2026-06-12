package com.alfred_core.intent;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;

@Service
public class IntentDatasetService {

    private Map<String, List<String>> intents;

    @PostConstruct
    public void load() {

        try {

            ObjectMapper mapper = new ObjectMapper(); // (Jackson): The library that parses the JSON data.

            InputStream inputStream =
            	    new ClassPathResource("Intent/Intents.json")
//					Locates and opens the Intents.json file from your project's src/main/resources/Intent/ 
//            	    directory.
            	        .getInputStream();

            intents = mapper.readValue(
                    inputStream,
                    new TypeReference<Map<String, List<String>>>() {}
            );

            System.out.println(
                    "Loaded " + intents.size() +
                    " intent categories."
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load intents.json",
                    e
            );
        }
    }

    public Map<String, List<String>> getIntents() {
        return intents;
    }
}