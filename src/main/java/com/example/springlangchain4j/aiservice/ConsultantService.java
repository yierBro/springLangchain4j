package com.example.springlangchain4j.aiservice;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,//手动装配
chatModel = "openAiChatModel",
streamingChatModel = "openAiStreamingChatModel",//指定模型
chatMemory = "chatMemoryaaa",
chatMemoryProvider = "chatMemoryProvider",
contentRetriever = "contentRetrieveraa",
tools = "ReservationToolaa")//ai调用的方法所在类的名字
//@AiService
public interface ConsultantService {
    //聊天的方法
    //public String chat(String message);
    @SystemMessage(fromResource = "system.txt")
    //有两个参数需要加上注解 一个参数默认用户信息
    public Flux<String> chat(@MemoryId String memoryId,@UserMessage String message);
}
