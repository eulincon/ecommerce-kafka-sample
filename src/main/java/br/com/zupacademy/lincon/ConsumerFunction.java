package br.com.zupacademy.lincon;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerFunction {
    void consume(ConsumerRecord<String, String> record) throws InterruptedException;
}
