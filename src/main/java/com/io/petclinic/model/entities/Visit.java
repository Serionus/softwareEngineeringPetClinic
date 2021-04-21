package com.io.petclinic.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Visit {

    LocalDateTime visitDate;

    private @Id @GeneratedValue Long visitId;

    //  :) <3
    public Visit() {

    }

    public Visit(int year, int month, int day,int hour, int minute) {
        this.visitDate = LocalDate.of(year, month, day).atTime(hour, minute);
    }


    public LocalDateTime getBeginTime() {
        return visitDate;
    }

    public void setBeginTime(LocalDateTime visitDate) {
        this.visitDate = visitDate;
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
        return visitDate == visit.visitDate &&
                visitId.equals(visit.visitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitDate, visitId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Visit{");
        sb.append("beginningTime=").append(visitDate);
        sb.append(", visitId=").append(visitId);
        sb.append('}');
        return sb.toString();
    }
}
