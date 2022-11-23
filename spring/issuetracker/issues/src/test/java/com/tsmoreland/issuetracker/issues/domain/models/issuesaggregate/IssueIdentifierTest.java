package com.tsmoreland.issuetracker.issues.domain.models.issuesaggregate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class IssueIdentifierTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "\t"})
    void ConstructorShouldThrowIllegalArgumentExceptionWhenProjectIsNullOrBlank(String project) {
        assertThrows(IllegalArgumentException.class, () -> {
            new IssueIdentifier(project, 1234);
        });
    }

    @Test
    void ConstructorShouldThrowIllegalArgumentExceptionWhenProjectIsLongerThan3() {
        assertThrows(IllegalArgumentException.class, () -> {
            new IssueIdentifier("project", 1234);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -1 })
    void ConstructorShouldThrowIllegalArgumentExceptionWhenIssueNumberIsLessThanOrEqualToZero(int issueNumber) {
        assertThrows(IllegalArgumentException.class, () -> {
            new IssueIdentifier("APP", issueNumber);
        });
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "\t", "PROJ" })
    void GetValidationErrorOrEmptyShouldReturnValueWhenProjectIsInvalid(String project) {
        var actual = IssueIdentifier.GetValidationErrorOrEmpty(project, 1234);
        assertTrue(actual.isPresent());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -1 })
    void GetValidationErrorOrEmptyShouldReturnValueWhenIssueNumberIsLessThanOrEqualToZero(int issueNumber) {
        var actual = IssueIdentifier.GetValidationErrorOrEmpty("ABC", issueNumber);
        assertTrue(actual.isPresent());
    }

    @Test
    void ToStringShouldReturnProjectDashIssueNumber() {

        final String expected = "APP-1234";
        var id = new IssueIdentifier("APP", 1234);

        String actual = id.toString();

        assertEquals(expected, actual);
    }
}