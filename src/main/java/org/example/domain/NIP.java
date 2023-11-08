package org.example.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Random;

@Slf4j
public record NIP(String value) {
    private static final int LENGTH = 10;

    public NIP {
        Objects.requireNonNull(value, "You passed the wrong value format for NIP");
        if (value.length() != 10) {
            log.error("The given value {} for NIP id cannot be empty!", value);
            throw new RuntimeException("The given value for NIP id cannot be empty!");
        }
    }

    public static String createNIP() {
        Random random = new Random();
        var table = new String[LENGTH];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            sb.append(random.nextInt(8));
        }
        return sb.toString();
    }
}
