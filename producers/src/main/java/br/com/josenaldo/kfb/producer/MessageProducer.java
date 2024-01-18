package br.com.josenaldo.kfb.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MessageProducer {

    private final String topicName;

    private final KafkaProducer<String, Object> kafkaProducer;

    public MessageProducer(Map<String, Object> propsMap, String topicName) {
        this.kafkaProducer = new KafkaProducer<>(propsMap);
        this.topicName = topicName;
    }

    public static Map<String, Object> propsMap() {
        return Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094",
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName(),
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()
        );
    }

    public void publishMessageSync(String key, String value) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topicName, key, value);
        RecordMetadata recordMetadata = kafkaProducer.send(producerRecord).get();

        System.out.println("Message published successfully. \n" +
                "Topic: " + recordMetadata.topic() + "\n" +
                "Partition: " + recordMetadata.partition() + "\n" +
                "Offset: " + recordMetadata.offset() + "\n" +
                "Timestamp: " + recordMetadata.timestamp());
    }

}
