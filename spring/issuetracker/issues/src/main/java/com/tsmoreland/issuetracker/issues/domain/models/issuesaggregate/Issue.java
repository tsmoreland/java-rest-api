package com.tsmoreland.issuetracker.issues.domain.models.issuesaggregate;

import java.time.Instant;
import java.util.Optional;

import com.tsmoreland.issuetracker.shared.Guard;

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
        Guard.againstNull(id);
        Guard.againstNullOrBlank(title);
        Guard.againstNullOrBlank(description);

        this.id = id;
        this.title = title;
        this.description = description;
    }
}
