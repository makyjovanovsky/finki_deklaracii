package com.example.deklaraciifinki.entity;

import com.example.deklaraciifinki.entity.semester.SemesterEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subjects")
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_name")
    private String name;

    @ManyToMany(mappedBy = "subjects")
    private List<SemesterEntity> semesters = new ArrayList<>();

    public SubjectEntity() {
    }

    public SubjectEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SemesterEntity> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<SemesterEntity> semesters) {
        this.semesters = semesters;
    }
}
