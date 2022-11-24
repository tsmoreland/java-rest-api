package com.tsmoreland.issuetracker.issues.domain.models.issuesaggregate;

import com.tsmoreland.issuetracker.shared.Guard;

public record IssueLink(LinkType link, IssueIdentifier parentId, Issue child) {
    public IssueLink {
        Guard.againstNull(child, "child");
    }

    public IssueIdentifier getId() {
        return child.getId();
    }

    public String getTitle() {
        return child.getTitle();
    }

    public String getDescription() {
        return child.getDescription();
    }

    public Priority getPriority() {
        return child.getPriority();
    }

    public IssueType getType() {
        return child.getType();
    }
}
