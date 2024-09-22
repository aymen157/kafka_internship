package com.example.saasapplication.Controllers;

import com.example.saasapplication.Entities.MouseEventData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController("main")
public class MainController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper; // Inject the ObjectMapper

    @Value(value = "${spring.kafka.mouse-analytics-topic}")
    private String topicName;

    @PostMapping("analytics")
    public void ReceiveData(@RequestBody MouseEventData data)
            throws JsonProcessingException
    {
        String jsonData = objectMapper.writeValueAsString(data);
        var future = kafkaTemplate.send(topicName, jsonData);
        future.whenComplete((result, ex) -> {;
            if (ex == null) {
                System.out.println("Sent message=[" + jsonData +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        jsonData + "] due to : " + ex.getMessage());
            }
        });
    }

}
