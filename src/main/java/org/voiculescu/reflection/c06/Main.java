package org.voiculescu.reflection.c06;

import org.voiculescu.reflection.c06.auction.Auction;
import org.voiculescu.reflection.c06.auction.Bid;

import java.io.Serializable;
import java.lang.reflect.Modifier;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        printClassModifiers(Auction.class);
        System.out.println("----------------------------");
        printClassModifiers(Bid.class);
        System.out.println("----------------------------");
        printClassModifiers(Bid.Builder.class);
        System.out.println("----------------------------");
        printClassModifiers(Class.forName("org.voiculescu.reflection.c06.auction.Bid$Builder$BidImpl"));
        System.out.println("----------------------------");
        printClassModifiers(Serializable.class);
    }

    public static void printClassModifiers(Class<?> clazz) {
        int modifier = clazz.getModifiers();
        System.out.printf("Class %s access modifier is %s%n", clazz.getSimpleName(), getAccessModifier(modifier));
        if (Modifier.isAbstract(modifier)) {
            System.out.println("The class is abstract");
        }
        if (Modifier.isInterface(modifier)) {
            System.out.println("The class is an interface");
        }
        if (Modifier.isStatic(modifier)) {
            System.out.println("The class is static");
        }
    }

    private static String getAccessModifier(int modifier) {
        if (Modifier.isPublic(modifier)) {
            return "public";
        } else if (Modifier.isPrivate(modifier)) {
            return "private";
        } else if (Modifier.isProtected(modifier)) {
            return "protected";
        } else return "package-private";
    }

    public static void runAuctions() {
        Auction auction = new Auction();
        auction.startAuction();

        Bid bid1 = Bid.builder().setBidderName("Company1").setPrice(10).build();
        auction.addBid(bid1);
        Bid bid2 = Bid.builder().setBidderName("Company2").setPrice(12).build();
        auction.addBid(bid2);
        auction.stopAuction();
        System.out.println(auction.getAllBids());
        System.out.println("Highest bid: " + auction.getHighestBid().get());
    }
}
