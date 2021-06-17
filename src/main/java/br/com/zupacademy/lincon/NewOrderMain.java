package br.com.zupacademy.lincon;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException,
            InterruptedException {
        KafkaProducer<String, String> producer =
                new KafkaProducer<>(properties());
        for (int i = 0; i < 100; i++) {

            String key = UUID.randomUUID().toString();
            var value = key + ",21333,89424234";
            var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER"
                    , key, value);
            Callback callback = (data, ex) -> {
                if (ex != null) {
                    ex.printStackTrace();
                    return;
                }
                System.out.println("sucesso enviando " + data.topic() +
                        ":::partition " + data.partition() +
                        "/ offset " + data.offset() + "/ timestamp " + data.timestamp());
            };
            var email = "Thanks! We are processing your order...";
            var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", key,
                    email);
            producer.send(record, callback).get();
            producer.send(emailRecord, callback).get();
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127" +
                ".0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        return properties;
    }
}
