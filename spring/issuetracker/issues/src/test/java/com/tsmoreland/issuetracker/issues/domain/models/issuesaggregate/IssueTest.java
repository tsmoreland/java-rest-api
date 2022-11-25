package com.tsmoreland.issuetracker.issues.domain.models.issuesaggregate;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

final class IssueTest {

    private final static IssueIdentifier ID = new IssueIdentifier("APP", 1234);
    private final static User USER = new User(UUID.randomUUID(), "John Smith");
    private final static Instant START = Instant.parse("2022-11-25T00:09:24.00Z");
    private final static Instant END = Instant.parse("2022-11-25T00:10:15.00Z");

    @Test
    void constructorShouldNotThrowExceptionWhenAssigneeIsNull() {
        assertDoesNotThrow(() -> {
            new Issue(ID, "title", "description", Priority.MEDIUM, IssueType.DEFECT, null, USER, START, END);
        });
    }
    @Test
    void constructorShouldNotThrowExceptionWhenReporterIsNull() {
        assertDoesNotThrow(() -> {
            new Issue(ID, "title", "description", Priority.MEDIUM, IssueType.DEFECT, USER, null, START, END);
        });
    }
    @Test
    void constructorShouldNotThrowExceptionWhenStartTimeIsNull() {
        assertDoesNotThrow(() -> {
            new Issue(ID, "title", "description", Priority.MEDIUM, IssueType.DEFECT, USER, USER, null, END);
        });
    }
    @Test
    void constructorShouldNotThrowExceptionWhenEndTImeIsNull() {
        assertDoesNotThrow(() -> {
            new Issue(ID, "title", "description", Priority.MEDIUM, IssueType.DEFECT, USER, USER, START, null);
        });
    }
}