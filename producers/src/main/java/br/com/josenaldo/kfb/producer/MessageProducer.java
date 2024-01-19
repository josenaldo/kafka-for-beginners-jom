package br.com.josenaldo.kfb.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    private final String topicName;

    private final KafkaProducer<String, Object> kafkaProducer;

    public static Map<String, Object> propsMap() {
        return Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094",
                      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName(),
                      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()
        );
    }

    Callback callback = (RecordMetadata recordMetadata, Exception exception) -> {
        if (exception != null) {
            logger.error("exception in Callback is {}", exception.getMessage());
        } else {
            logger.info("Published Message in Callback: \ntopic: {}, \npartition: {}, \noffset: {}, \ntimestamp: {}",
                        recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset(),
                        recordMetadata.timestamp()
            );
        }
    };

    public MessageProducer(Map<String, Object> propsMap, String topicName) {
        this.kafkaProducer = new KafkaProducer<>(propsMap);
        this.topicName = topicName;
    }

    public void publishMessageAsync(String key, String value) {
        logger.info("Publishing message {} for the key {}", value, key);
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topicName, key, value);
        kafkaProducer.send(producerRecord, callback);
    }

    public void publishMessageSync(String key, String value) {

        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topicName, key, value);

        try {
            RecordMetadata recordMetadata = kafkaProducer.send(producerRecord).get();
            logger.info("Message {} sent successfully for the key {}", value, key);
            logger.info("Metadata: [topic: {}, partition: {}, offset: {}, timestamp: {}]", recordMetadata.topic(),
                        recordMetadata.partition(), recordMetadata.offset(), recordMetadata.timestamp()
            );
        } catch (InterruptedException e) {
            logger.error("Interruption exception sending message {} for the key {}. Message: {}", value, key,
                         e.getMessage()
            );
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            logger.error("Error sending message {} for the key {}. Message: {}", value, key, e.getMessage());
        }


    }

}
