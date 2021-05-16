package com.io.petclinic.model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Vet")
@Table(name = "vet")
public class Vet extends Human {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "id_sequence")
    @Column(name = "vet_id",
            updatable = false)
    private Long vetId;

    @OneToMany(mappedBy = "vet", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Visit> visits = new ArrayList<>();

    public Vet(String firstname, String surname) {
        super(firstname, surname);
    }

    public Vet() {
        super();
    }

    public Long getVetId() {
        return vetId;
    }

    public void setVetId(Long vetId) {
        this.vetId = vetId;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vet)) return false;
        if (!super.equals(o)) return false;
        Vet vet = (Vet) o;
        return vetId.equals(vet.vetId) && visits.equals(vet.visits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), vetId, visits);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vet{");
        sb.append("firstname='").append(getFirstname()).append("', surname='").append(getSurname());
        sb.append("', vetId=").append(vetId);
        sb.append(", visits=").append(visits);

        sb.append('}');
        return sb.toString();
    }
}
