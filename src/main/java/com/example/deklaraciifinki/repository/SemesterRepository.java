package com.example.deklaraciifinki.repository;

import com.example.deklaraciifinki.entity.declaration.DeclarationEntity;
import com.example.deklaraciifinki.entity.semester.SemesterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<SemesterEntity, Long> {

    List<SemesterEntity> findAllByDeclarationsContaining(DeclarationEntity declaration);

    Optional<SemesterEntity> findByDeclarationsContaining(DeclarationEntity declaration);
}
