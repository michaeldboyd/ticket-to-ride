package Communication;

import java.io.*;

import Model.ServerModel;
import Persistence.GameRestorer;
import Persistence.PersistenceManager;
import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;


import java.util.logging.*;

public class ServerRunner {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println(args.length + " incorrect parameters!! Please specify db name and game state command count. #makerodhamproud");
            return;
        }

        try {
            PersistenceManager.getInstance().loadPlugin(args[0]);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(String.format("parameters incorrect: [%s %s]. \n CORRECT USE: $: server.jar <sqlite | json> <commandCount>", args[0], args[1]));
            return;
        }
        int portNumber = 8080;
        ServerRunner server = new ServerRunner();
        server.run(portNumber);

        //TODO: Is this where the game restoration should be called?
        //GameRestorer.getInstance().run();
    }
}

