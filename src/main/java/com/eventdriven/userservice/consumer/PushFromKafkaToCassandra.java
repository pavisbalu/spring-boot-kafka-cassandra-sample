package com.eventdriven.userservice.consumer;

import com.eventdriven.userservice.config.KafkaConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PushFromKafkaToCassandra {

    @KafkaListener(groupId = "test", topics = "#{'${spring.kafka.template.default-topic}'.split(',')}",
            properties = "${spring.kafka.consumer.props}")
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord);
    }

}
