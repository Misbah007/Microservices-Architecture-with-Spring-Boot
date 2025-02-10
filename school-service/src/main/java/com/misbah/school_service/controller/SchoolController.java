package com.misbah.school_service.controller;

import com.misbah.school_service.model.School;
import com.misbah.school_service.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*")
@RequestMapping(value = "/school")
@RestController

public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping
    public ResponseEntity<?> addSchool(@RequestBody School school) {
        return schoolService.addSchool(school);
    }

    @GetMapping
    public List<School> fetchSchools() {
        return schoolService.fetchSchools();
    }

    @GetMapping("/{id}")
    public School fetchSchoolById(@PathVariable String id) {
        return schoolService.fetchSchoolById(id);
    }
}
