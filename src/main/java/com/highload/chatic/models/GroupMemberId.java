package com.highload.chatic.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberId implements Serializable {
    @Column(name = "pgroup")
    private UUID pgroupId;
    @Column(name = "person")
    private UUID personId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupMemberId that = (GroupMemberId) o;

        if (!Objects.equals(pgroupId, that.pgroupId)) return false;
        return Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        int result = pgroupId != null ? pgroupId.hashCode() : 0;
        result = 31 * result + (personId != null ? personId.hashCode() : 0);
        return result;
    }
}
