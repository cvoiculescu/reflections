package org.voiculescu.reflection.c02.web_private_constructor;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;

public class WebServer {

    public void startServer() throws IOException {
        HttpServer httpServer = HttpServer.create(
                ServerConfiguration.getServerConfiguration().getServerAddress(), 0);
        httpServer.createContext("/greeting").setHandler(exchange -> {
            String greetingMessage = ServerConfiguration.getServerConfiguration().getGreetingMessage();
            exchange.sendResponseHeaders(200, greetingMessage.length());
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(greetingMessage.getBytes());
            responseBody.flush();
            responseBody.close();
        });
        System.out.format("Starting server on address %s:%d\n",
                ServerConfiguration.getServerConfiguration().getServerAddress().getHostName(),
                ServerConfiguration.getServerConfiguration().getServerAddress().getPort());
        httpServer.start();
    }
}
