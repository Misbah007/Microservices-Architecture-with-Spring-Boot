package com.misbah.student_service.services;

import com.misbah.student_service.dto.School;
import com.misbah.student_service.dto.StudentResponse;
import com.misbah.student_service.model.Student;
import com.misbah.student_service.repository.StudentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @CacheEvict(value = "students", allEntries = true)
    public ResponseEntity<?> createStudent(Student student) {
        try {
            return new ResponseEntity<Student>(studentRepository.save(student), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CircuitBreaker(name = "student-service", fallbackMethod = "getDefaultMessage")
    @Cacheable(value = "students", key = "#id")
    public ResponseEntity<?> fetchStudentById(String id, HttpServletRequest httpServletRequest) {
        Optional<Student> student = studentRepository.getStudentById(id);
        log.info("School Id:{}", student.get().getSchoolId());
        if (student.isPresent()) {
            String bearerToken = httpServletRequest.getHeader("Authorization");
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", bearerToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<School> responseEntity = restTemplate.exchange(
                    "http://SCHOOL-SERVICE/school/" + student.get().getSchoolId(),
                    HttpMethod.GET,
                    entity,
                    School.class
            );

            School school = responseEntity.getBody();

            StudentResponse studentResponse = new StudentResponse(
                    student.get().getId(),
                    student.get().getName(),
                    student.get().getAge(),
                    student.get().getGender(),
                    school
            );
            return new ResponseEntity<>(studentResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Student Found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getDefaultMessage(String id, HttpServletRequest request, Throwable throwable) {
        log.error("Fallback triggered for Student ID: {}", id, throwable);
        return new ResponseEntity<>("Fallback response: Service unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Cacheable(value = "students")
    public ResponseEntity<?> fetchStudents() {
        List<Student> students = studentRepository.getAllStudents();
        if (students.size() > 0) {
            return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Students", HttpStatus.NOT_FOUND);
        }
    }

}
