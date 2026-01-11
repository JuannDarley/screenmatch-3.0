package br.com.alura.screenmatch.service;

import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {

    public static String obterTraducao(String texto) {

        String apiKey = System.getenv("OPENAI_APIKEY");

        // ✅ GUARDA DE SEGURANÇA (ESSENCIAL)
        if (apiKey == null || apiKey.isBlank()) {
            System.err.println("⚠ OPENAI_API_KEY não configurada");
            return texto; // fallback
        }

        try {
            OpenAiService service = new OpenAiService(apiKey);

            CompletionRequest requisicao = CompletionRequest.builder()
                    .model("gpt-3.5-turbo-instruct")
                    .prompt("traduza para o português o texto: " + texto)
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            var resposta = service.createCompletion(requisicao);
            return resposta.getChoices().get(0).getText();

        } catch (OpenAiHttpException e) {
            System.err.println("⚠ Erro OpenAI: " + e.getMessage());
            return texto;

        } catch (Exception e) {
            System.err.println("❌ Erro inesperado: " + e.getMessage());
            return texto;
        }
    }
}