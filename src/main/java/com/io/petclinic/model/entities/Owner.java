package com.io.petclinic.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class Owner extends Human {

    private @Id @GeneratedValue Long ownerId;

    @OneToMany
    private List<Pet> pets;

    public Owner() {
        super();
    }

    public Owner(String firstname, String surname) {
        super(firstname, surname);
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long id) {
        this.ownerId = id;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public Pet addNewPet(Pet pet) {
        pets.add(pet);
        return pet;
    }

    public void addNewVisit(Pet pet, Visit visit) {
        pet.getVisits().add(visit);
    }

    public void cancelVisit() {

    }

   public Pet getPetById(Long id){
        for (Pet pet: pets){
            if (pet.getPetId().equals(id)){
                return pet;
            }
        }
        return null;
   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner)) return false;
        if (!super.equals(o)) return false;
        Owner owner = (Owner) o;
        return ownerId.equals(owner.ownerId) && pets.equals(owner.pets);
    }



    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ownerId, pets);
    }

    @Override
    public String toString() {
        final StringBuilder message = new StringBuilder("Owner{");
        message.append("firstname='").append(getFirstname()).append("', surname='").append(getSurname()).append("', id=").append(ownerId).append("}");
        return message.toString();
    }


}
