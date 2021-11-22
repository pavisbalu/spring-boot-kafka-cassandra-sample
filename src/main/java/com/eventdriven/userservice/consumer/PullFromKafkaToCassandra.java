package com.eventdriven.userservice.consumer;

import com.eventdriven.userservice.dto.UserDto;
import com.eventdriven.userservice.events.CreateUserEvent;
import com.eventdriven.userservice.events.DeleteUserEvent;
import com.eventdriven.userservice.events.Event;
import com.eventdriven.userservice.events.UpdateUserEvent;
import com.eventdriven.userservice.service.UserService;
import com.eventdriven.userservice.util.JsonSerDe;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PullFromKafkaToCassandra {

    @Autowired
    private UserService userService;

    @KafkaListener(groupId = "test-dev", topics = "#{'${spring.kafka.template.default-topic}'.split(',')}",
            properties = "${spring.kafka.consumer.props}")
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord) {
        String eventAsJson = consumerRecord.value();
        Event event = JsonSerDe.fromJson(eventAsJson, Event.class);
        if (event instanceof CreateUserEvent createUserEvent) {
            UserDto userDto = new UserDto();
            userDto.setId(createUserEvent.getUserId());
            userDto.setEmail(createUserEvent.getEmail());
            userDto.setFirstname(createUserEvent.getFirstName());
            userDto.setLastname(createUserEvent.getLastName());
            userService.createUserInCassandra(userDto);
            log.info("Saved %s to Cassandra".formatted(userDto.getEmail()));
        } else if (event instanceof DeleteUserEvent deleteUserEvent) {
            userService.deleteUserFromCassandra(deleteUserEvent.getUserId());
            log.info("Deleted %s from Cassandra".formatted(deleteUserEvent.getUserId()));
        } else if (event instanceof UpdateUserEvent updateUserEvent) {
            UserDto userDto = new UserDto();
            userDto.setId(updateUserEvent.getUserId());
            userDto.setEmail(updateUserEvent.getEmail());
            userDto.setFirstname(updateUserEvent.getFirstName());
            userDto.setLastname(updateUserEvent.getLastName());
            userService.updateUserInCassandra(userDto);
        } else {
            log.warn("Unrecognised event: " + eventAsJson);
        }
    }

}
