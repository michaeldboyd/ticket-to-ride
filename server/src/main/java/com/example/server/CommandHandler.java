package com.example.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

/**
 * Created by mboyd6 on 1/31/2018.
 */

class CommandHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Hello world");
  }
}
