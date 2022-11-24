package com.tsmoreland.issuetracker.issues.domain.models.issuesaggregate;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum LinkType {

    /**
     * Issue is related to
     */
    Related(0),

    /**
     * Duplicates or duplicated by depending on which side of the join
      */

    Duplicate(1),

    /**
     * Blocks or blocked by depending on which side of the join
     */
    Blocking(2),

    /**
     * Clones or cloned from depending which on side of the join
     */
    Clone(3);

    @Getter
    private int value;
    private static final Map<Integer, LinkType> linkTypeByValue = new HashMap<>();

    LinkType(int value) {
        this.value = value;
    }
    static {
        for (LinkType linkType : LinkType.values()) {
            linkTypeByValue.put(linkType.getValue(), linkType);
        }
    }

    public static Optional<LinkType> valueOf(int value) {
        if (linkTypeByValue.containsKey(value)) {
            return Optional.of(linkTypeByValue.get(value));
        } else {
            return Optional.empty();
        }
    }

}
