package com.example.springlangchain4j.config;

import com.example.springlangchain4j.aiservice.ConsultantService;
import dev.langchain4j.community.store.embedding.redis.RedisEmbeddingStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CommonConfig {
    @Autowired
    private OpenAiChatModel model;
    @Autowired
    private ChatMemoryStore redisChatMemoryStore;
    @Autowired
    private EmbeddingModel embeddingModel;
//    redis向量数据库
    @Autowired
    private RedisEmbeddingStore embeddingStore;

//    @Bean
//    public ConsultantService consultantService(){
//        ConsultantService consultantService = AiServices.builder(ConsultantService.class)
//                .chatModel(model)
//                .build();
//        return consultantService;
//    }
    //会话记忆功能，构建会话记忆对象
    @Bean
    public ChatMemory chatMemoryaaa(){
        return MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();

    }
    //构建chatmemoeyprovide对象
    @Bean
    public ChatMemoryProvider chatMemoryProvider(){
         ChatMemoryProvider chatMemoryProvider=new ChatMemoryProvider(){

             @Override
             public ChatMemory get(Object memoryId) {
                 return MessageWindowChatMemory.builder()
                         .id(memoryId)
                         .maxMessages(20)
                         .chatMemoryStore(redisChatMemoryStore)
                         .build();
             }
         };
         return chatMemoryProvider;
    }

    //构建向量数据库操作对象
    //TODO 这里注解可以省东西是不了解的 19视频
    //@Bean
    public EmbeddingStore store(){
        //1.加载文档进内存,同时选择解析模型
        //List<Document> documents = ClassPathDocumentLoader.loadDocuments("content");
        //List<Document> documents = FileSystemDocumentLoader.loadDocuments("E:\\CodeAI\\springlangchain4j\\src\\main\\resources\\content",new ApachePdfBoxDocumentParser());
        List<Document> documents = ClassPathDocumentLoader.loadDocuments("content",new ApachePdfBoxDocumentParser());

        //2.构建向量数据库操作对象
        //TODO 这里省钱是不了解的
        //内存操作 每次是内存，关机了就没了 同时使用的是百炼
//        InMemoryEmbeddingStore store=new InMemoryEmbeddingStore();
        //store 要操作内存版本数据库，然后东西存到store

        //redis版本的数据库
        //redisEmbeddingStore

        //3.构造文档分割器对象
        DocumentSplitter ds= DocumentSplitters.recursive(500,100);
        //完成文本切割和向量化
        EmbeddingStoreIngestor ingestor=EmbeddingStoreIngestor.builder()
                .embeddingStore(embeddingStore)
                .documentSplitter(ds)
                //向量分割模型
                .embeddingModel(embeddingModel)
                .build();
        ingestor.ingest(documents);
        return embeddingStore;
    }
    //构建向量数据库检索对象
    @Bean
    public ContentRetriever contentRetrieveraa(/*EmbeddingStore store*/){
        return  EmbeddingStoreContentRetriever.builder()
                .embeddingModel(embeddingModel)
                //.embeddingStore(store)
                //这里是redis的向量数据库
                .embeddingStore(embeddingStore)
                .minScore(0.5)
                .maxResults(3)
                .build();
    }

}
