package org.voiculescu.reflection.c02.web_private_constructor;

import java.net.InetSocketAddress;

public class ServerConfiguration {

    private static ServerConfiguration serverConfiguration;

    private final InetSocketAddress serverAddress;
    private final String greetingMessage;

    private ServerConfiguration(int port, String greetingMessage) {
        this.greetingMessage = greetingMessage;
        this.serverAddress = new InetSocketAddress("localhost", port);
        if (serverConfiguration == null) {
            serverConfiguration = this;
        }
    }

    public InetSocketAddress getServerAddress() {
        return serverAddress;
    }

    public String getGreetingMessage() {
        return greetingMessage;
    }

    public static ServerConfiguration getServerConfiguration() {
        return serverConfiguration;
    }
}
