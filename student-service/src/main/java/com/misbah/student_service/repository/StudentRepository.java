package com.misbah.student_service.repository;

import com.misbah.student_service.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    @Query(value = "{id:?0}")
    Optional<Student> getStudentById(String id);

    @Query(value = "{}")
    List<Student> getAllStudents();
}
