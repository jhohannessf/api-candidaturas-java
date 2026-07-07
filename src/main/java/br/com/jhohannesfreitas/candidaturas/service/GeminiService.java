package br.com.jhohannesfreitas.candidaturas.service;

import br.com.jhohannesfreitas.candidaturas.domain.model.VagaEntity;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {
    // Atributo que representa a conexao com a IA.
    private final ChatClient chatClient;

    // Construtor com injeção de dependência. O Spring injeta automaticamente um ChatClient.Builder
    public GeminiService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String obterInformacao(VagaEntity vagaEntity, String perfil) {
        return chatClient.prompt() // Começa a montar a mensagem
                .user("Análise se o Perfil profissional e vaga para candidatura fazem sentido: " + vagaEntity + perfil) // Define o que o usuário pergunta à IA
                .call() // Envia a requisição pro Gemini
                .content(); // Pega só o texto da resposta
    }


}
