package br.com.josenaldo.kfb.producer;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {
        MessageProducer messageProducer = new MessageProducer(MessageProducer.propsMap(), "test-topic");
        try {
            messageProducer.publishMessageSync(null, "Hello World");
        } catch (ExecutionException e) {
            System.out.println("erro ao publicar mensagem: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Thread interrompida " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
