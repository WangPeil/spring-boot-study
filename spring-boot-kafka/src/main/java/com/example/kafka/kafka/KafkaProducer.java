package com.example.kafka.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * kafka生产者
 *
 * @author wangpeil
 */
@Component
@Slf4j
public class KafkaProducer {
    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    static final String TOPIC_TEST = "topic.test";
    static final String TOPIC_GROUP1 = "topic.group1";
    static final String TOPIC_GROUP2 = "topic.group2";

    public void send(Object message) throws JsonProcessingException {
        String msg = OBJECTMAPPER.writeValueAsString(message);
        log.info("producer发送消息为: {}", msg);
        ListenableFuture<SendResult<String, Object>> feature = kafkaTemplate.send(TOPIC_TEST, msg);
        feature.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info("{}发送失败 {}", TOPIC_TEST, throwable.toString());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                log.info("{}发送成功 {}", TOPIC_TEST, stringObjectSendResult.toString());
            }
        });
    }
}
