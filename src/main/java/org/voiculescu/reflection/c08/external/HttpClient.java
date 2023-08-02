package org.voiculescu.reflection.c08.external;

public interface HttpClient {

    void initialize();

    String sendRequest(String request);
}
