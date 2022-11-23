package com.tsmoreland.issuetracker.issues.domain.models.issuesaggregate;

import java.time.Instant;
import java.util.Optional;

public final class Issue {

    private IssueIdentifier id;
    private String title;
    private String description;
    private Optional<IssueIdentifier> epicId;
    // private IssueType type;
    // private User assignee
    // private User reporter
    private Optional<Instant> startTime;
    private Optional<Instant> endTime;


    public Issue(IssueIdentifier id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
