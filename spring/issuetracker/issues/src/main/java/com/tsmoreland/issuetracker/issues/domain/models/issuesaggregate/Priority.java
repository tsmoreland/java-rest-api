package com.tsmoreland.issuetracker.issues.domain.models.issuesaggregate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Priority {
    LOW(0),
    MEDIUM(1),
    HIGH(2);

    private final int value;
    private static final Map<Integer, Priority> priorityByValue = new HashMap<>();
    private Priority(int value) {
        this.value = value;
    }

    static {
       for (Priority value : Priority.values()) {
           priorityByValue.put(value.getValue(), value);
       }
    }

    public int getValue() {
        return value;
    }
    public static Optional<Priority> valueOf(int value) {
        if (priorityByValue.containsKey(value)) {
            return Optional.of(priorityByValue.get(value));
        } else {
            return Optional.empty();
        }
    }

}
