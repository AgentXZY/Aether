package com.alfred_core;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class AlfredCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlfredCoreApplication.class, args);
    }

    @Bean
    CommandLineRunner runLLMOnStartup() {
        return args -> {

            try {
                // Example: launching Qwen via Ollama
                ProcessBuilder pb = new ProcessBuilder("ollama", "run", "qwen3:4b-instruct");
                pb.inheritIO(); // show logs in console
                Process process = pb.start();

                // Optional: keep track of the process
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    process.destroy();
                }));

            } catch (IOException e) {
                System.err.println("Failed to start Qwen LLM: " + e.getMessage());
            }
        };
    }
}
