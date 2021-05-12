package com.io.petclinic.unitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.io.petclinic.model.services.OwnerService;
import com.io.petclinic.model.repositories.*;
import org.springframework.util.Assert;

class OwnerServiceTest {
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private VisitRepository visitRepository;
    @InjectMocks
    private OwnerService ownerService;

    @Test()
    public void creatingOwner(){
//        ownerService.createOwner("Bruce", "Wayne");

    }
}
