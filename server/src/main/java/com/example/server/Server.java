package com.example.server;
import com.google.gson.Gson;
import java.io.*;
import java.net.*;

import com.sun.net.httpserver.*;
import java.util.logging.*;

public class Server {

  // STATIC MEMBERS

  private static Logger log;

  static {
    try {
      initLog();
    }
    catch (IOException e) {
      System.out.println("Could not initialize log: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private static void initLog() throws IOException {

    Level logLevel = Level.FINEST;

    log = Logger.getLogger("fms");
    log.setLevel(logLevel);
    log.setUseParentHandlers(false);

    Handler consoleHandler = new ConsoleHandler();
    consoleHandler.setLevel(logLevel);
    consoleHandler.setFormatter(new SimpleFormatter());
    log.addHandler(consoleHandler);

    FileHandler fileHandler = new FileHandler("log.txt", false);
    fileHandler.setLevel(logLevel);
    fileHandler.setFormatter(new SimpleFormatter());
    log.addHandler(fileHandler);
  }
  private static final int MAX_WAITING_CONNECTIONS = 12;


  private HttpServer server;


  private void run(String portNumber) {

    log.fine("Initializing HTTP Server");

    try {
      server = HttpServer.create(
          new InetSocketAddress(Integer.parseInt(portNumber)),
          MAX_WAITING_CONNECTIONS);
    }
    catch (IOException e) {
      e.printStackTrace();
      return;
    }

    // Indicate that we are using the default "executor".
    // This line is necessary, but its function is unimportant for our purposes.
    server.setExecutor(null);

    server.createContext("/execute", new CommandHandler());

    server.start();

    // Log message indicating that the server has successfully started.
    log.finest("Server started...");
  }

  public static void main(String[] args) {
    String portNumber = "8080";
    Server server = new Server();
    server.run(portNumber);
  }
}

