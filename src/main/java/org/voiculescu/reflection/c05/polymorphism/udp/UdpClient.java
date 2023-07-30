package org.voiculescu.reflection.c05.polymorphism.udp;

public class UdpClient {
    public void sendAndForget(String requestPayload) {
        System.out.printf("Request : %s was sent through UDP%n", requestPayload);
    }
}
