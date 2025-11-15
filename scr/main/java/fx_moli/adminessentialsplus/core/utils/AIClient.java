package fx_moli.adminessentialsplus.core.utils;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AIClient {
    private final String apiKey;
    private final String model;
    private final String provider;
    private final AdminEssentialsPlus plugin;

    public AIClient(String apiKey, String model, String provider, AdminEssentialsPlus plugin) {
        this.apiKey = apiKey;
        this.model = model;
        this.provider = provider;
        this.plugin = plugin;
    }

    public String sendRequest(String prompt) {
        try {
            if ("groq".equalsIgnoreCase(provider)) {
                return sendGroqRequest(prompt);
            } else if ("huggingface".equalsIgnoreCase(provider)) {
                return sendHuggingFaceRequest(prompt);
            } else {
                return "Провайдер не поддерживается: " + provider;
            }
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    private String sendGroqRequest(String prompt) throws IOException {
        String url = "https://api.groq.com/openai/v1/chat/completions";
        String requestBody = String.format(
                "{\"model\":\"%s\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}],\"max_tokens\":1000,\"temperature\":0.7}",
                model, prompt.replace("\"", "\\\"")
        );

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            return "Ошибка Groq API: " + responseCode;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return parseGroqResponse(response.toString());
        }
    }

    private String sendHuggingFaceRequest(String prompt) throws IOException {
        String url = "https://api-inference.huggingface.co/models/" + model;
        String requestBody = String.format(
                "{\"inputs\":\"%s\",\"parameters\":{\"max_length\":500,\"temperature\":0.7,\"do_sample\":true}}",
                prompt.replace("\"", "\\\"")
        );

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            if (responseCode == 503) return "Модель загружается, попробуйте через 10-20 секунд";
            return "Ошибка HuggingFace API: " + responseCode;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    private String parseGroqResponse(String response) {
        if (response.contains("\"content\"")) {
            int start = response.indexOf("\"content\":\"") + 11;
            int end = response.indexOf("\"", start);
            if (start > 10 && end > start) {
                return response.substring(start, end).replace("\\n", "\n").replace("\\\"", "\"");
            }
        }
        return "Не удалось распарсить ответ: " + response;
    }

    public String moderateText(String text) {
        try {
            String url = "https://api-inference.huggingface.co/models/unitary/toxic-bert";
            String requestBody = String.format("{\"inputs\":\"%s\"}", text.replace("\"", "\\\""));

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                return "Ошибка модерации: " + responseCode;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                return "Модерация завершена: " + response.toString().substring(0, Math.min(100, response.length()));
            }

        } catch (Exception e) {
            return "Ошибка модерации: " + e.getMessage();
        }
    }
}