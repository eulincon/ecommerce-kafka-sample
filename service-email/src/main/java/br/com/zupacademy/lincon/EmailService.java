package br.com.zupacademy.lincon;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class EmailService {
  public static void main(String[] args) throws InterruptedException {
    var emailService = new EmailService();
    try (var service = new KafkaService(EmailService.class.getSimpleName(),
        "ECOMMERCE_SEND_EMAIL",
        emailService::parse, Email.class, Map.of())) {
      service.run();
    }
  }

  private void parse(ConsumerRecord<String, Email> record) throws InterruptedException {
    System.out.println("-----------------------------");
    System.out.println("Sending email");
    System.out.println("Thank you for your order! We are processing it!");
    System.out.println(record.key());
    System.out.println(record.value());
    System.out.println(record.partition());
    System.out.println(record.offset());
    Thread.sleep(1000);
    System.out.println("Email sent");
  }

}
