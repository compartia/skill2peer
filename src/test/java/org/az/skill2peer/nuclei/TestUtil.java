package org.az.skill2peer.nuclei;

public class TestUtil {

    private static final String CHARACTER = "a";

    public static String makeStringOfLen(final int length) {
        final StringBuilder builder = new StringBuilder();

        for (int index = 0; index < length; index++) {
            builder.append(CHARACTER);
        }

        return builder.toString();
    }
}
