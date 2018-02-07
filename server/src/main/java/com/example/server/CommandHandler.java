package com.example.server;

import com.example.sharedcode.communication.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.logging.Logger;


import com.google.gson.*;

public class CommandHandler implements HttpHandler {

    private static Logger log;

    static {
        log = Logger.getLogger("ticket-to-ride");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String respData = null;
        String msg = "Invalid http request";
        Gson gson = new Gson();
        CommandResult res = null;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                //get request object
                Reader reader = new InputStreamReader(exchange.getRequestBody());
                Command req = gson.fromJson(reader, Command.class);

                //check if request is valid:

                res = req.execute();
            } else {
                msg = "This API is a POST request. No GET requests allowed in the club! :(";

                res = new CommandResult(msg, null);
                System.out.println(msg);
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            res = new CommandResult(e.getLocalizedMessage(), null);

            // Display/log the stack trace
            msg = "An Internal Server Error has occurred: " + e.getMessage();
            System.out.println(msg);
            e.printStackTrace();
        } catch (Exception e) {
            // For some reason, calling execute on the command failed, so send the message back
            res = new CommandResult(e.getLocalizedMessage(), null);

            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            respData = gson.toJson(res);

            OutputStream respBody = exchange.getResponseBody();
            writeString(respData, respBody);
            respBody.close();

            System.out.println("Response sent!");
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}