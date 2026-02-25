
package com.example.urlshortener.util;

import java.util.concurrent.atomic.AtomicLong;

public class Base62Encoder {

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final AtomicLong counter = new AtomicLong(1000);

    public static String generateShortCode() {
        long value = counter.incrementAndGet();
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            sb.append(BASE62.charAt((int) (value % 62)));
            value /= 62;
        }
        return sb.reverse().toString();
    }
}
