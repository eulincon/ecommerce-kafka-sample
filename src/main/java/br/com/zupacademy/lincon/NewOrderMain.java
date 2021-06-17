package br.com.zupacademy.lincon;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException,
            InterruptedException {

        try(var dispatcher =
                new KafkaDispatcher()){
            for (int i = 0; i < 10; i++) {
                String key = UUID.randomUUID().toString();
                var value = key + ",21333,89424234";
                dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);
                var email = "Thanks! We are processing your order...";
                dispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);
            }
        }
    }
}
