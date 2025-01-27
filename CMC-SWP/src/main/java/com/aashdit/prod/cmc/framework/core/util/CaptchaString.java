package com.aashdit.prod.cmc.framework.core.util;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class CaptchaString {

    /**
     * Generate a random string.
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    public static final String upper = "ABCDFGHJKMNPRSTUVWXYZ"; // No I , L, O

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "23456789";

    public static final String alphanum = upper + digits + lower ;

    private final Random random;

    private final char[] symbols;

    private final char[] buf;

    public CaptchaString(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Create an alphanumeric string generator.
     */
    public CaptchaString(int length, Random random) {
        this(length, random, alphanum);
    }

    /**
     * Create an alphanumeric strings from a secure generator.
     */
    public CaptchaString(int length) {
        this(length, new SecureRandom());
    }

    /**
     * Create session identifiers.
     */
    public CaptchaString() {
        this(21);
    }

}