package com.poc;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import lombok.val;

public class Server extends AbstractVerticle {

  @Override
  public void start() {
    val netServer = getVertx().createNetServer();
    netServer.connectHandler(socket -> socket.handler(buffer -> {
      System.out.println("I received some bytes: " + buffer);
    })).listen(8080, asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println("Server is listening " + asyncResult.result().actualPort());
      } else {
        System.out.println("Failed to bind!");
      }
    });
  }

  public static void main(String... arguments) {
    val vertx = Vertx.vertx();
    vertx.deployVerticle(Server.class.getName());
  }

}