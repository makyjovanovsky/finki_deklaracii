package com.example.deklaraciifinki.repository;

import com.example.deklaraciifinki.entity.SubjectEntity;
import com.example.deklaraciifinki.entity.semester.SemesterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {

    List<SubjectEntity> findAllBySemestersContaining(SemesterEntity semester);

    Optional<SubjectEntity> findByName(String name);
}
