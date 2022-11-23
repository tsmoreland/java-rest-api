package com.tsmoreland.issuetracker.issues.domain.models.issuesaggregate;

import org.apache.logging.log4j.util.Strings;

public record IssueIdentifier(String project, int issueNumber) {
    public IssueIdentifier(String project, int issueNumber) {
        if (Strings.isBlank(project) || project.length() > 3) {
            throw new IllegalArgumentException("invalid project id");
        }
        if (issueNumber <= 0) {
            throw new IllegalArgumentException("invalid issue number, must be positive");
        }

        this.project = project;
        this.issueNumber = issueNumber;
    }

    @Override
    public String toString() {
        return "%s-%d".formatted(project, issueNumber);
    }
}
