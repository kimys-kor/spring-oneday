package com.oneday.api.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderNumberGenerator {

    // Atomic integer to ensure thread-safe incrementing of the sequence
    private static final AtomicInteger sequence = new AtomicInteger(0);

    // Format for the date part of the order number
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    // Method to generate the order number
    public static String generateOrderNumber() {
        // Get the current date
        String datePart = dateFormat.format(new Date());

        // Get the next sequence value and pad it with zeros to make it 4 digits
        String sequencePart = String.format("%04d", sequence.incrementAndGet());

        // Combine the date and sequence parts to form the final order number
        String orderNumber = datePart + sequencePart;

        return orderNumber;
    }

    public static void main(String[] args) {
        // Generate and print the order number
        System.out.println("Order Number: " + generateOrderNumber());
    }
}