package com.nlo.repository.dbdto;

public interface UserReactionSummary {
    String getUserId();         // User ID
    Long getPositiveCount();    // Count of positive reactions
    Long getNegativeCount();    // Count of negative reactions
}
