package com.example.deklaraciifinki.entity.declaration;

import com.example.deklaraciifinki.entity.SubjectEntity;
import com.example.deklaraciifinki.entity.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "declarations")
public class DeclarationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private SubjectEntity subject;

    @Enumerated(EnumType.STRING)
    private ClassType classType;

    @Column(name = "number_of_classes")
    private int numberOfClasses;

    @Column(name = "other_teacher_flag")
    private boolean otherTeacher;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "number_of_students")
    private int numberOfStudents;

    @Column(name = "consultative")
    private boolean consultative;

    @Column(name = "note")
    private String note;

    @ManyToOne
    private UserEntity user;

    public DeclarationEntity() {
    }

    public DeclarationEntity(SubjectEntity subject, ClassType classType, int numberOfClasses, boolean otherTeacher, Language language, int numberOfStudents, boolean consultative, String note) {
        this.subject = subject;
        this.classType = classType;
        this.numberOfClasses = numberOfClasses;
        this.otherTeacher = otherTeacher;
        this.language = language;
        this.numberOfStudents = numberOfStudents;
        this.consultative = consultative;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }

    public int getNumberOfClasses() {
        return numberOfClasses;
    }

    public void setNumberOfClasses(int numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }

    public boolean isOtherTeacher() {
        return otherTeacher;
    }

    public void setOtherTeacher(boolean otherTeacher) {
        this.otherTeacher = otherTeacher;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public boolean isConsultative() {
        return consultative;
    }

    public void setConsultative(boolean consultative) {
        this.consultative = consultative;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


}
