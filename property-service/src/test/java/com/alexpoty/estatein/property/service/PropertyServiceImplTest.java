package com.alexpoty.estatein.property.service;

import com.alexpoty.estatein.property.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class PropertyServiceImplTest {

    @Autowired
    PropertyServiceImpl propertyService;

    @MockitoBean
    PropertyRepository propertyRepository;

    @BeforeEach
    void setUp() {

    }
}