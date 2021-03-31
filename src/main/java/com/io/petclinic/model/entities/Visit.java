package com.io.petclinic.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Visit {

    private int beginningTime;

    private @Id @GeneratedValue Long visitId;

    public Visit(int beginningTime, Long visitId) {
        this.beginningTime = beginningTime;
        this.visitId = visitId;
    }

    public Visit() {

    }

    public int getBeginningTime() {
        return beginningTime;
    }

    public void setBeginningTime(int beginningTime) {
        this.beginningTime = beginningTime;
    }

    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return beginningTime == visit.beginningTime &&
                visitId.equals(visit.visitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beginningTime, visitId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Visit{");
        sb.append("beginningTime=").append(beginningTime);
        sb.append(", visitId=").append(visitId);
        sb.append('}');
        return sb.toString();
    }
}
