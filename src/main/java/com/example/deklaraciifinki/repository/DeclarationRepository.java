package com.example.deklaraciifinki.repository;

import com.example.deklaraciifinki.entity.declaration.DeclarationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeclarationRepository extends JpaRepository<DeclarationEntity, Long> {
}
