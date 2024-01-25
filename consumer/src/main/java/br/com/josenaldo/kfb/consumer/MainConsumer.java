package br.com.josenaldo.kfb.consumer;

import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainConsumer {

    public static void main(String[] args) {
        String topicName = "test-topic-replicated";
        Map<String, Object> propsMap = MessageConsumer.buildConsumerProperties();
        MessageConsumer messageConsumer = new MessageConsumer(propsMap, topicName);
        messageConsumer.pollKafka();
    }
}
