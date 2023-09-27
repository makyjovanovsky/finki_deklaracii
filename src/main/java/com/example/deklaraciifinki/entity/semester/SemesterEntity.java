package com.example.deklaraciifinki.entity.semester;

import com.example.deklaraciifinki.entity.SubjectEntity;
import com.example.deklaraciifinki.entity.declaration.DeclarationEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "semesters")
public class SemesterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year_from")
    private int yearFrom;

    @Column(name = "year_to")
    private int yearTo;

    @Enumerated(EnumType.STRING)
    private SemesterType type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "semester_subjects", joinColumns = @JoinColumn(name = "semester_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<SubjectEntity> subjects = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<DeclarationEntity> declarations = new ArrayList<>();

    public SemesterEntity() {
    }

    public SemesterEntity(int yearFrom, int yearTo, SemesterType type) {
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(int yearFrom) {
        this.yearFrom = yearFrom;
    }

    public int getYearTo() {
        return yearTo;
    }

    public void setYearTo(int yearTo) {
        this.yearTo = yearTo;
    }

    public SemesterType getType() {
        return type;
    }

    public void setType(SemesterType type) {
        this.type = type;
    }

    public List<SubjectEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectEntity> subjects) {
        this.subjects = subjects;
    }

    public List<DeclarationEntity> getDeclarations() {
        return declarations;
    }

    public void setDeclarations(List<DeclarationEntity> declarations) {
        this.declarations = declarations;
    }
}
