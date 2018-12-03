package com.poc;

import com.poc.domain.Product;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import lombok.val;

public class Client extends AbstractVerticle {

  @Override
  public void start() {
    val netClient = getVertx().createNetClient();
    netClient.connect(8080, "localhost", asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println("Connected!");
        val socket = asyncResult.result();
        vertx.setPeriodic(1000, aLong -> {
          val product = Product.builder().build();
          System.out.println("Sending " + product);
          socket.write(product.toString());
        });
      } else {
        System.out.println("Failed to connect: " + asyncResult.cause().getMessage());
      }
    });
  }

  public static void main(String... arguments) {
    val vertx = Vertx.vertx();
    vertx.deployVerticle(Client.class.getName());
  }

}