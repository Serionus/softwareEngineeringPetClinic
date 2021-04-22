package com.io.petclinic.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Vet extends Human {

    private @Id @GeneratedValue Long vetId;

    @OneToMany
    private List<Visit> visits = new ArrayList<>();

    public Vet(String firstname, String surname) {
        super(firstname, surname);
    }

    public Vet() { super();}

    public Long getVetId() {
        return vetId;
    }

    public void setVetId(Long vetId) {
        this.vetId = vetId;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public List<Visit> getVisits() { return visits; }



    private int officeHours;  // wiem że nie int tylko coś czasowego ale jako placeholder

    public void addNewVisit(Visit visit) {
        getVisits().add(visit);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Vet vet = (Vet) o;
        return vetId.equals(vet.vetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), vetId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vet{");
        sb.append("vetId=").append(vetId);
        sb.append('}');
        return sb.toString();
    }
}
