package com.tsmoreland.issuetracker.issues.domain.models.issuesaggregate;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.tsmoreland.issuetracker.shared.Guard;
import jakarta.annotation.Nullable;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

public final class Issue {

    @Getter
    private final IssueIdentifier id;
    @Getter
    private String title;
    @Getter
    private String description;
    @Nullable
    private IssueIdentifier epicId;
    @Getter
    private IssueType type;
    @Getter
    private Priority priority;
    @Nullable
    private User assignee;
    @Nullable
    private User reporter;
    @Nullable
    private Instant startTime;
    @Nullable
    private Instant endTime;

    private final Set<IssueLink> children = new HashSet<>();
    private final Set<IssueLink> parents = new HashSet<>();

    public Issue(IssueIdentifier id, String title, String description) {
        Guard.againstNull(id);
        Guard.againstNullOrBlank(title);
        Guard.againstNullOrBlank(description);

        this.id = id;
        this.title = title;
        this.description = description;
        this.type = IssueType.DEFECT;
        this.priority = Priority.LOW;
    }

    public Issue(IssueIdentifier id, String title, String description, Priority priority, IssueType type) {
        this(id, title, description);
        this.priority = priority;
        this.type = type;
    }

    public Issue(IssueIdentifier id, String title, String description, Priority priority, IssueType type,
                 @Nullable User assignee, @Nullable User reporter,
                 @Nullable Instant startTime, @Nullable Instant endTime) {
        this(id, title, description, priority, type);

        this.assignee = assignee;
        this.reporter = reporter;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public Issue(IssueIdentifier id, String title, String description, Priority priority, IssueType type,
                 @Nullable User assignee, @Nullable User reporter,
                 @Nullable Instant startTime, @Nullable Instant endTime,
                 Iterable<IssueLink> children, Iterable<IssueLink> parents) {
        this(id, title, description, priority, type, assignee, reporter, startTime, endTime);

        Guard.againstNull(children, "children");
        Guard.againstNull(parents, "parents");

        for (IssueLink child : children) {
            this.children.add(child);
        }
        for (IssueLink parent : parents) {
            this.parents.add(parent);
        }
    }

    public Stream<IssueLink> getChildren() {
        return children.stream();
    }
    public Stream<IssueLink> getParents() {
        return parents.stream();
    }

    /**
     * sets the title if valid
     * @param title the new title to use
     * @throws IllegalArgumentException when title is blank or greater than 200 characters
     */
    public void setTitle(String title) {
        if (Strings.isBlank(title) || title.length() > 200) {
            throw new IllegalArgumentException("invalid title, cannot be empty or longer than 200 characters");
        }
        this.title = title;
    }

    /**
     * sets the description if valid
     * @param description the new description to use
     * @throws IllegalArgumentException when description is blank or greater than 500 characters
     */
    public void setDescription(String description) {
        if (Strings.isBlank(description) || description.length() > 500) {
            throw new IllegalArgumentException("invalid description, cannot be empty or longer than 500 characters");
        }
        this.description = description;
    }

    public Optional<IssueIdentifier> getEpicId() {
        return epicId != null
            ? Optional.of(epicId)
            : Optional.empty();
    }
    public void setEpicId(@Nullable IssueIdentifier epicId) {
        if (epicId != null) {
            if (epicId.equals(id)) {
                throw new IllegalArgumentException("an issue cannot be its own epic");
            }
            if (this.type == IssueType.EPIC) {
                throw new IllegalArgumentException("Cannot assign epic to an epic");
            }
        }
        this.epicId = epicId;
    }

    public Optional<User> getAssignee() {
        return assignee != null
            ? Optional.of(assignee)
            : Optional.empty();
    }
    public void setAssignee(@Nullable User assignee) {
        this.assignee = assignee;
    }
    public Optional<User> getReporter() {
        return reporter != null
            ? Optional.of(reporter)
            : Optional.empty();
    }
    public void setReporter(@Nullable User reporter) {
        this.reporter = reporter;
    }
    public Optional<Instant> getStartTime() {
        return startTime != null
            ? Optional.of(startTime)
            : Optional.empty();
    }
    public void setStartTime(Instant startTime) {
        Guard.againstNull(startTime, "startTime");

        if (this.endTime != null && startTime.isAfter(this.endTime)) {
            this.endTime = null;
        }

        this.startTime = startTime;
    }
    public Optional<Instant> getEndTime() {
        return endTime != null
            ? Optional.of(endTime)
            : Optional.empty();
    }
    public void setEndTime(Instant endTime) {
        Guard.againstNull(endTime, "endTime");

        if (this.startTime != null && endTime.isBefore(this.startTime)) {
            throw new IllegalArgumentException("end time cannot be before start time");
        }
        this.endTime = endTime;
    }

    public void addLinkToParent(LinkType link, IssueIdentifier issueId)
    {
        Guard.againstNull(issueId, "issueId");
        var issueLink = new IssueLink(link, issueId,  this);
        parents.add(issueLink);
    }

    public void addLinkToChild(LinkType link, Issue issue)
    {
        Guard.againstNull(issue, "issue");
        var issueLink = new IssueLink(link, id,  issue);
        children.add(issueLink);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Issue other && id.equals(other.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
