import java.io.*;

import com.sun.net.httpserver.HttpServer;
import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;


import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;
import java.net.InetSocketAddress;
import java.util.logging.*;

public class ServerRunner {

    // STATIC MEMBERS
    private static final int MAX_WAITING_CONNECTIONS = 12;

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

    private void run(int portNumber) {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(portNumber);
        server.addConnector(connector);

        WebSocketHandler context = new WebSocketHandler() {
          @Override
          public void configure(WebSocketServletFactory factory) {
            factory.register(CommandSocket.class);
          }
        };


        ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/echo/");
        contextHandler.setHandler(context);
        server.setHandler(contextHandler);

        try {
            server.start();
            server.dump();
            server.join();


            //***** POLLING PART *****
            HttpServer pollServer = HttpServer.create(
                    new InetSocketAddress(portNumber),
                    MAX_WAITING_CONNECTIONS);

            pollServer.createContext("/poll", new CommandHandler());
            pollServer.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int portNumber = 8080;
        ServerRunner server = new ServerRunner();
        server.run(portNumber);
    }
}

