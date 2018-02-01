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
    log = Logger.getLogger("fms");
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String respData = null;
    String msg = "Invalid http request";
    boolean success = false;
    Gson gson = new Gson();

    try {
      if (exchange.getRequestMethod().toLowerCase().equals("post")) {
        //get request object
        Reader reader = new InputStreamReader(exchange.getRequestBody());
        Command req = gson.fromJson(reader, Command.class);
        //check if request is valid:
        CommandResult res = req.execute();
        //send response to client
        //TODO check Command Result for errors
        respData = gson.toJson(res);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        success = true;
      } else
        msg = "this api is a post request only. no get requests allowed in the club.";
      if (!success) {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        //TODO: how do we best sent back commands?
        respData = gson.toJson(new CommandResult("error", null));
      }
    } catch (IOException e) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
      msg = "an error has occurred. Internal Server Error: " + e.getMessage();
      // Display/log the stack trace
      respData = gson.toJson(new CommandResult("error", null));
      e.printStackTrace();
    } finally {
      OutputStream respBody = exchange.getResponseBody();
      writeString(respData, respBody);
      respBody.close();
    }
  }

  private void writeString(String str, OutputStream os) throws IOException {
    OutputStreamWriter sw = new OutputStreamWriter(os);
    sw.write(str);
    sw.flush();
  }

}