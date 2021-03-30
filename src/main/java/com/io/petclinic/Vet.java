package com.io.petclinic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Vet extends Human {

    private @Id @GeneratedValue Long vetId;

    public Vet(String firstname, String surname) {
        super(firstname, surname);
    }

    public Vet() {
    }

    public Long getVetId() {
        return vetId;
    }

    public void setVetId(Long vetId) {
        this.vetId = vetId;
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
