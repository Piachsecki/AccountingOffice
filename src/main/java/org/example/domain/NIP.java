package org.example.domain;

import java.util.Objects;
import java.util.Random;

public record NIP(String value) {
    public NIP{
        Objects.requireNonNull(value, "You passed the wrong value format for NIP");
        if (value.length() != 10){
            throw new RuntimeException("The given value for NIP id cannot be empty!");
        }
    }
    private static final int LENGTH = 10;

    public static String createNIP(){
        Random random = new Random();
        var table = new String[LENGTH];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            sb.append(random.nextInt(8));
        }
        return sb.toString();
    }
}
