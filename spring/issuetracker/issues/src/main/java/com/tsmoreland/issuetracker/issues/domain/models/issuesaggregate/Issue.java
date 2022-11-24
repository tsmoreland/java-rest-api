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
    @Getter
    private Optional<IssueIdentifier> epicId;
    @Getter
    private IssueType type = IssueType.DEFECT;
    @Getter
    private Priority priority = Priority.LOW;
    @Getter
    private Optional<User> assignee;
    @Getter
    private Optional<User> reporter;
    @Getter
    private Optional<Instant> startTime;
    @Getter
    private Optional<Instant> endTime;

    private Set<IssueLink> children = new HashSet<>();
    private Set<IssueLink> parents = new HashSet<>();

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

        this.assignee = assignee != null ? Optional.of(assignee) : Optional.empty();
        this.reporter = reporter != null ? Optional.of(reporter) : Optional.empty();
        this.startTime = startTime != null ? Optional.of(startTime) : Optional.empty();
        this.endTime = endTime != null ? Optional.of(endTime) : Optional.empty();
    }
    public Issue(IssueIdentifier id, String title, String description, Priority priority, IssueType type,
                 @Nullable User assignee, @Nullable User reporter,
                 @Nullable Instant startTime, @Nullable Instant endTime,
                 Iterable<IssueLink> children, Iterable<IssueLink> parents) {
        this(id, title, description, priority, type, assignee, reporter, startTime, endTime);

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

    public void setEpicId(IssueIdentifier epicId) {
        if (epicId.equals(id)) {
            throw new IllegalArgumentException("an issue cannot be its own epic");
        }

        if (this.type == IssueType.EPIC) {
            throw new IllegalArgumentException("Cannot assign epic to an epic");
        }
        this.epicId = Optional.of(epicId);
    }
}
