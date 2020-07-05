package com.example.kafka.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * kafka消费者
 *
 * @author wangpeil
 */
@Component
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = {KafkaProducer.TOPIC_TEST}, groupId = KafkaProducer.TOPIC_GROUP1)
    public void topicTest(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> message = Optional.ofNullable(record.value());
        if (message.isPresent())    {
            Object msg = message.get()  ;
            log.info("topic_test消费了 Topic: {}, message: {}",topic, msg);
            ack.acknowledge();
        }
    }
    @KafkaListener(topics = {KafkaProducer.TOPIC_TEST}, groupId = KafkaProducer.TOPIC_GROUP2)
    public void topicTest1(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> message = Optional.ofNullable(record.value());
        if (message.isPresent())    {
            Object msg = message.get()  ;
            log.info("topic_test1消费了 Topic: {}, message: {}",topic, msg);
            ack.acknowledge();
        }
    }
}
