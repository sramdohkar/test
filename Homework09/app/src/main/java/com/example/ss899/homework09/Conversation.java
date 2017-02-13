package com.example.ss899.homework09;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by shash on 27/04/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Conversation {
    String archived_by_participant1, archived_by_participant2, deletedBy, participant1, participant2;

    public Conversation(String archived_by_participant1, String archived_by_participant2, String deletedBy, String participant1, String participant2) {
        this.archived_by_participant1 = archived_by_participant1;
        this.archived_by_participant2 = archived_by_participant2;
        this.deletedBy = deletedBy;
        this.participant1 = participant1;
        this.participant2 = participant2;
    }

    public Conversation() {
    }

    public String getArchived_by_participant1() {
        return archived_by_participant1;
    }

    public void setArchived_by_participant1(String archived_by_participant1) {
        this.archived_by_participant1 = archived_by_participant1;
    }

    public String getArchived_by_participant2() {
        return archived_by_participant2;
    }

    public void setArchived_by_participant2(String archived_by_participant2) {
        this.archived_by_participant2 = archived_by_participant2;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getParticipant1() {
        return participant1;
    }

    public void setParticipant1(String participant1) {
        this.participant1 = participant1;
    }

    public String getParticipant2() {
        return participant2;
    }

    public void setParticipant2(String participant2) {
        this.participant2 = participant2;
    }
}
