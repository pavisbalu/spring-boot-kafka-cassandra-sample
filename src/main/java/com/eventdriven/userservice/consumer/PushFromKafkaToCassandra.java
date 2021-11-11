package com.eventdriven.userservice.consumer;

import com.eventdriven.userservice.dto.UserDto;
import com.eventdriven.userservice.service.UserService;
import com.eventdriven.userservice.util.JsonSerDe;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PushFromKafkaToCassandra {

    @Autowired
    private UserService userService;

    @KafkaListener(groupId = "test", topics = "#{'${spring.kafka.template.default-topic}'.split(',')}",
            properties = "${spring.kafka.consumer.props}")
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord) {
        String recordAsJson = consumerRecord.value();
        UserDto userDto = JsonSerDe.fromJson(recordAsJson, UserDto.class);
        userService.createUserInCassandra(userDto);
        log.info("Saved %s to Cassandra".formatted(userDto.getEmail()));
    }

}
