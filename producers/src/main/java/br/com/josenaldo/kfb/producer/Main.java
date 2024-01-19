package br.com.josenaldo.kfb.producer;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MessageProducer messageProducer = new MessageProducer(MessageProducer.propsMap(), "test-topic");

        for (int i = 0; i < 10; i++) {
            messageProducer.publishMessageSync("key2", "Sending message " + i);
        }
        Thread.sleep(3000);
    }
}
