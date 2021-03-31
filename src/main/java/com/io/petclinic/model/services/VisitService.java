package com.io.petclinic.model.services;

import com.io.petclinic.model.repositories.VisitRepository;

public class VisitService {
    private final VisitRepository repository;

    public VisitService(VisitRepository repository) {
        this.repository = repository;
    }
}
