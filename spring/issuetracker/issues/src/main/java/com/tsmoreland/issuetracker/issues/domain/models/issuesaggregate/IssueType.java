package com.tsmoreland.issuetracker.issues.domain.models.issuesaggregate;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum IssueType {
    EPIC(0),
    STORY(1),
    TASK(2),
    SUBTASK(3),
    DEFECT(4);

    @Getter
    private final int value;
    private static final Map<Integer, IssueType> issueTypeByValue = new HashMap<>();
    IssueType(int value) {
        this.value = value;
    }
    static {
        for (IssueType type : IssueType.values()) {
            issueTypeByValue.put(type.getValue(), type);
        }
    }

    public static Optional<IssueType> valueOf(int value) {
        if (!issueTypeByValue.containsKey(value)) {
            return Optional.empty();
        } else {
            return Optional.of(issueTypeByValue.get(value));
        }
    }
}
