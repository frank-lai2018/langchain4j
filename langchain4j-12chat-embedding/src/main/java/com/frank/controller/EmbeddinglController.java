package com.frank.controller;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;

/**
 * @Description: 知識出處，https://docs.langchain4j.dev/tutorials/rag#embedding-store
 */
@RestController
@Slf4j
public class EmbeddinglController
{
    @Resource
    private EmbeddingModel embeddingModel;
    @Resource
    private QdrantClient qdrantClient;
    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;






    /**
     * 文本向量化測試，看看形成向量後的文本
     * http://localhost:9012/embedding/embed
     * @return
     */
    @GetMapping(value = "/embedding/embed")
    public String embed()
    {
    	String prompt = """
    			 詠雞
    			雞鳴破曉光，
    			紅冠映朝陽。 
    			金羽披霞彩，
    			昂首步高崗。 
    			""";
        Response<Embedding> embeddingResponse = embeddingModel.embed(prompt);
        int dimension = embeddingResponse.content().vector().length;
        System.out.println("Embedding dimension: " + dimension);//取得向量維度
        System.out.println(embeddingResponse);
        return embeddingResponse.content().toString();
    }

    /*** 新向量資料庫實例和建立索引：test-qdrant
	 * 類似mysql create database test-qdrant
	 * http://localhost:9012/embedding/createCollection
	 */
    @GetMapping(value = "/embedding/createCollection")
    public void createCollection()
    {
        var vectorParams = Collections.VectorParams.newBuilder()
                .setDistance(Collections.Distance.Cosine)
                //Qdrant（或其他向量資料庫）要求你存入的向量維度必須和 collection 建立時指定的維度完全一致，不然就會報錯，無法存入。
                .setSize(1536)//openai/text-embedding-3-small的向量維度是1536
                .build();
        qdrantClient.createCollectionAsync("test-qdrant", vectorParams);
    }

    /*
     往向量資料庫新增文字記錄
     */
    @GetMapping(value = "/embedding/add")
    public String add()
    {
        String prompt = """
                	 詠雞
    			 雞鳴破曉光，
    			 紅冠映朝陽。 
    			 金羽披霞彩，
    			 昂首步高崗。 
    			""";
        TextSegment segment1 = TextSegment.from(prompt);
        segment1.metadata().put("author", "lalal");
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        String result = embeddingStore.add(embedding1, segment1);

        System.out.println(result);

        return result;
    }

    @GetMapping(value = "/embedding/query1")
    public void query1(){
        Embedding queryEmbedding = embeddingModel.embed("詠雞說的是什麼").content();
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(1)
                .build();
        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(embeddingSearchRequest);
        System.out.println(searchResult.matches().get(0).embedded().text());
    }

    @GetMapping(value = "/embedding/query2")
    public void query2(){
        Embedding queryEmbedding = embeddingModel.embed("詠雞").content();

        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .filter(metadataKey("author").isEqualTo("aaaa"))
                .maxResults(1)
                .build();

        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(embeddingSearchRequest);

        System.out.println(searchResult.matches().get(0).embedded().text());
    }
}
