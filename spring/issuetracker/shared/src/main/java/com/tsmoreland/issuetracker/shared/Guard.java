package com.tsmoreland.issuetracker.shared;

import org.apache.logging.log4j.util.Strings;

public final class Guard {

    /**
     * throws IllegalArgumentException if parameter is null or whitespace
     * @param parameter value to check
     */
    public static void againstNullOrBlank(String parameter) {
        againstNullOrBlank(parameter, "");
    }

    /**
     * throws IllegalArgumentException if parameter is null or whitespace
     * @param parameter value to check
     * @param parameterName name of value to check, used in exception message
     */
    public static void againstNullOrBlank(String parameter, String parameterName) {
        if (!Strings.isBlank(parameter)) {
            return;
        }

        final String name = Strings.isBlank(parameterName)
            ? "(Unknown)"
            : parameterName;

        throw new IllegalArgumentException("%s cannot be null or blank".formatted(name));
    }

}
