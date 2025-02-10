package com.misbah.school_service.repository;

import com.misbah.school_service.model.School;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.beans.Transient;

@Repository
public interface SchoolRepository extends JpaRepository<School, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO school(id,location,principal_name,school_name) VALUES(?1,?2,?3,?4)", nativeQuery = true)
    void addUser(String id, String location, String principalName, String schoolName);
}
