package com.frank.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfig
{
		//deepseek模型不支援embedding
    @Bean
    public EmbeddingModel embeddingModel()
    {
        return OpenAiEmbeddingModel.builder()
                    .apiKey(System.getenv("openrouter"))
                    .modelName("openai/text-embedding-3-small")
                    .logRequests(true)
                    .logResponses(true)
                    .baseUrl("https://openrouter.ai/api/v1")
                .build();
    }




    /**
     *創建Qdrant客戶端
     * @return
     */
    @Bean
    public QdrantClient qdrantClient() {
        QdrantGrpcClient.Builder grpcClientBuilder =
                QdrantGrpcClient.newBuilder("192.168.32.128", 6334, false);
        return new QdrantClient(grpcClientBuilder.build());
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return QdrantEmbeddingStore.builder()
                .host("192.168.32.128")
                .port(6334)
                .collectionName("test-qdrant")
                .build();
    }
}
