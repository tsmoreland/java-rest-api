package com.tsmoreland.issuetracker.issues.domain.models.issuesaggregate;

import org.apache.logging.log4j.util.Strings;

import java.util.Optional;

public record IssueIdentifier(String project, int issueNumber) {
    public IssueIdentifier {
        Optional<String> validationError = GetValidationErrorOrEmpty(project, issueNumber);
        if (validationError.isPresent()) {
            throw new IllegalArgumentException(validationError.get());
        }
    }

    public static Optional<String> GetValidationErrorOrEmpty(String project, int issueNumber) {
        if (Strings.isBlank(project) || project.length() > 3) {
            return Optional.of("invalid project id");
        }
        if (issueNumber <= 0) {
            return Optional.of("invalid issue number, must be positive");
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "%s-%d".formatted(project, issueNumber);
    }
}
