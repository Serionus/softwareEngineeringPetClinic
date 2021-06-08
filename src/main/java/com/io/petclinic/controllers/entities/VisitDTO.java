package com.io.petclinic.controllers.entities;

import java.time.LocalDateTime;

public class VisitDTO {
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private String vetFirstName;
    private String vetSurname;
    private String petName;
    private String petSpecies;
    private Long visitId;

    public VisitDTO() {
    }

    public VisitDTO(LocalDateTime beginTime, LocalDateTime endTime, String vetFirstName, String vetSurname ,String petName, String petSpecies, Long visitId) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.vetFirstName = vetFirstName;
        this.vetSurname = vetSurname;
        this.petName = petName;
        this.petSpecies = petSpecies;
        this.visitId = visitId;
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

    public String getVetFirstName() {
        return vetFirstName;
    }

    public void setVetFirstName(String vetFirstName) {
        this.vetFirstName = vetFirstName;
    }

    public String getVetSurname() {
        return vetSurname;
    }

    public void setVetSurname(String vetSurname) {
        this.vetSurname = vetSurname;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetSpecies() {
        return petSpecies;
    }

    public void setPetSpecies(String petSpecies) {
        this.petSpecies = petSpecies;
    }

    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }
}
