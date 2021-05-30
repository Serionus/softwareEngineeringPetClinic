package com.io.petclinic.controllers.entities;

import java.time.LocalDateTime;

public class VisitDTO {
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private String vetName;
    private String petName;

    public VisitDTO() {
    }

    public VisitDTO(LocalDateTime beginTime, LocalDateTime endTime, String vetName, String petName) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.vetName = vetName;
        this.petName = petName;
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

    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }
}
