package com.io.petclinic.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Visit {

    private int beginTime;

    private @Id @GeneratedValue Long visitId;

    // co ten konstruktor, tu coś nie tak :((
    public Visit(int beginningTime, Long visitId) {
        this.beginTime = beginningTime;
        this.visitId = visitId;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
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
        return beginTime == visit.beginTime &&
                visitId.equals(visit.visitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beginTime, visitId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Visit{");
        sb.append("beginningTime=").append(beginTime);
        sb.append(", visitId=").append(visitId);
        sb.append('}');
        return sb.toString();
    }
}
