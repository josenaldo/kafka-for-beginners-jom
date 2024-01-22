package br.com.josenaldo.kfb.launcher;


import br.com.josenaldo.kfb.producer.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import static br.com.josenaldo.kfb.producer.MessageProducer.propsMap;

public class CommandLineLauncher {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineLauncher.class);


    public static void launchCommandLine(){
        boolean cliUp = true;
        while (cliUp){
            Scanner scanner = new Scanner(System.in);
            userOptions();
            String option = scanner.next();
            logger.info("Selected Option is : {} ", option);
            switch (option) {
                case "1":
                    acceptMessageFromUser(option);
                    break;
                case "2":
                    cliUp = false;
                    break;
                default:
                    break;

            }
        }
    }

    public static void userOptions(){
        List<String> userInputList = new ArrayList<>();
        userInputList.add("1: Kafka Producer");
        userInputList.add("2: Exit");
        System.out.println("Please select one of the below options:");
        for(String userInput: userInputList ){
            System.out.println(userInput);
        }
    }
    public static MessageProducer init(){

        Map<String, Object> producerProps = propsMap();
        return new MessageProducer(producerProps, "test-topic-replicated");
    }

    public static void publishMessage(MessageProducer messageProducer, String input){
        StringTokenizer stringTokenizer = new StringTokenizer(input, "-");
        int noOfTokens = stringTokenizer.countTokens();
        switch (noOfTokens){
            case 1:
                messageProducer.publishMessageSync(null,stringTokenizer.nextToken());
                break;
            case 2:
                messageProducer.publishMessageSync(stringTokenizer.nextToken(),stringTokenizer.nextToken());
                break;
            default:
                break;
        }
    }

    public static void acceptMessageFromUser(String option){
        Scanner scanner = new Scanner(System.in);
        boolean flag= true;
        while (flag){
            System.out.println("Please Enter a Message to produce to Kafka:");
            String input = scanner.nextLine();
            logger.info("Entered message is {}", input);
            if(input.equals("00")) {
                flag = false;
            }else {
                MessageProducer messageProducer = init();
                publishMessage(messageProducer, input);
                messageProducer.close();
            }
        }
        logger.info("Exiting from Option : {} ", option);
    }

    public static void main(String[] args) {

        System.out.println(CommandLineMessages.HELLO);
        launchCommandLine();
        System.out.println(CommandLineMessages.BYE);
    }
}
