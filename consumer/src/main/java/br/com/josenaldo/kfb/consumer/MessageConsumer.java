package br.com.josenaldo.kfb.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    private final KafkaConsumer<String, String> consumer;

    private final String topicName;

    public MessageConsumer(Map<String, Object> propsMap, String topicName) {
        this.consumer = new KafkaConsumer<>(propsMap);
        this.topicName = topicName;
    }

    public static Map<String, Object> buildConsumerProperties() {
//        return Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094",
        return Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094",
                      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName(),
                      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName(),
                      ConsumerConfig.GROUP_ID_CONFIG, "kafka-test"
                      );
    }

    public void pollKafka() {
        consumer.subscribe(List.of(topicName));
        Duration timeoutDuration = Duration.ofMillis(100);
        try {
            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(timeoutDuration);
                consumerRecords.forEach(consumerRecord -> {
                    logger.info("Consumer Record Key is {} and the value is {}", consumerRecord.key(),
                                consumerRecord.value()
                    );
                    logger.info("Partition is {} and the offset is {}", consumerRecord.partition(),
                                consumerRecord.offset()
                    );
                });
            }
        } catch (Exception e) {
            logger.error("Exception in pollKafka is {}", e.getMessage());
        } finally {
            consumer.close();
        }

    }
}
