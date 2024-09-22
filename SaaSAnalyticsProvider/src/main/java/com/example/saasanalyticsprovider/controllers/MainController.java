package com.example.saasanalyticsprovider.controllers;

import com.example.saasanalyticsprovider.AnalyticsDataProcessors.MouseEventDataProcessor;
import com.example.saasanalyticsprovider.Entities.MouseEventData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller("main")
public class MainController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    public MouseEventDataProcessor mouseEventDataProcessor;
    @Autowired
    private ObjectMapper objectMapper; // Inject the ObjectMapper

    @KafkaListener(topics = "SaaSMouseEvents")
    public void listenTest(String message) throws JsonProcessingException {
        System.out.println("Received Message in topic: " + message);

        MouseEventData data = objectMapper.readValue(message, MouseEventData.class);
        mouseEventDataProcessor.Process(data);
    }
}
