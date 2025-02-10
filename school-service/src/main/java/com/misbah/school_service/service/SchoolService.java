package com.misbah.school_service.service;

import com.fasterxml.uuid.Generators;
import com.misbah.school_service.model.School;
import com.misbah.school_service.repository.SchoolRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SchoolService {


    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @CacheEvict(value = "schools", allEntries = true)
    public ResponseEntity<?> addSchool(School school) {
        String id = Generators.timeBasedGenerator().generate().toString();
        schoolRepository.addUser(id, school.getLocation(), school.getPrincipalName(), school.getSchoolName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Cacheable(value = "schools")
    public List<School> fetchSchools() {
        return schoolRepository.findAll();
    }

    @Cacheable(value = "school", key = "#id")
    public School fetchSchoolById(String id) {
        return schoolRepository.findById(id).orElse(null);
    }
}
