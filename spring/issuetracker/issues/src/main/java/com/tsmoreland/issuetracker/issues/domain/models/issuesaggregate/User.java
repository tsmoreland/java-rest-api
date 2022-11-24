package com.tsmoreland.issuetracker.issues.domain.models.issuesaggregate;

import java.util.UUID;

public record User(UUID userId, String fullName) {

    public static final User Unassigned = new User(UUID.fromString(""), "Unassigned");
}
