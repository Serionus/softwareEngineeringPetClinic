package com.io.petclinic.model.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Visit {

    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    @ManyToOne
    private Vet vet;
    @ManyToOne(cascade = CascadeType.ALL)
    private Pet pet = null;

    private @Id @GeneratedValue Long visitId;

    //  :) <3 ;( ;o

    public Visit(Vet vet, LocalDateTime beginTime, LocalDateTime endTime) {
        this.vet = vet;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Visit() {

    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Vet getVet() {
        return vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
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
        if (!(o instanceof Visit)) return false;
        Visit visit = (Visit) o;
        return beginTime.equals(visit.beginTime) && endTime.equals(visit.endTime) && vet.equals(visit.vet) && Objects.equals(pet, visit.pet) && visitId.equals(visit.visitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beginTime, endTime, vet, pet, visitId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Visit{");
        sb.append("beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", Vet{firstname='").append(vet.getFirstname()).append("', surname='").append(vet.getSurname()).append("'}");
        if(pet == null){
            sb.append(", No pet assigned to this visit");
        } else {
            sb.append(", Pet{name='").append(pet.getName()).append("', species='").append(pet.getSpecies()).append("'");
        }
        sb.append("}, visitId=").append(visitId);
        sb.append('}');
        return sb.toString();
    }
}
