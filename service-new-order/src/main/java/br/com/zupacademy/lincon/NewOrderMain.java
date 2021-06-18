package br.com.zupacademy.lincon;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
  public static void main(String[] args) throws ExecutionException,
      InterruptedException {
    try (var orderDispatcher =
             new KafkaDispatcher<Order>()) {
      try (var emailDispatcher = new KafkaDispatcher<Email>()) {
        for (int i = 0; i < 10; i++) {
          var userId = UUID.randomUUID().toString();
          var orderId = UUID.randomUUID().toString();
          var amount = new BigDecimal(Math.random() * 5000 + 1);
          var order = new Order(userId, orderId, amount);
          var emailBody = "Thanks! We are processing your order...";
          var emailSubject = "New Order";
          var email = new Email(emailSubject, emailBody);
          orderDispatcher.send("ECOMMERCE_NEW_ORDER", userId, order);
          emailDispatcher.send("ECOMMERCE_SEND_EMAIL", userId, email);
        }
      }
    }
  }
}
