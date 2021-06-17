package br.com.zupacademy.lincon;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class FraudDetectorService {
    public static void main(String[] args) throws InterruptedException {
        var consumer = new KafkaConsumer<>(properties());
        consumer.subscribe(Collections.singletonList("ECOMMERCE_NEW_ORDER"));
        while (true) {
            var records = consumer.poll(Duration.ofMillis(1000));
            if (!records.isEmpty()) {
                System.out.println("encontrei" + records.count() + "registros");

                for (var record : records) {
                    System.out.println("-----------------------------");
                    System.out.println("Processing new order, checking for " +
                            "fraud");
                    System.out.println(record.key());
                    System.out.println(record.partition());
                    System.out.println(record.offset());
                    Thread.sleep(5000);
                }

                System.out.println("Order processed");
            }
        }
    }

    private static Properties properties() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127" +
                ".0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG
                , StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,
                FraudDetectorService.class.getSimpleName());
//        properties.setProperty(ConsumerConfig);
//        properties.setProperty(ConsumerConfig);
//        properties.setProperty(ConsumerConfig);
        return properties;
    }
}
