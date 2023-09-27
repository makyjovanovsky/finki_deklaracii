package com.example.deklaraciifinki.data;

import com.example.deklaraciifinki.entity.SubjectEntity;
import com.example.deklaraciifinki.entity.UserEntity;
import com.example.deklaraciifinki.entity.semester.SemesterEntity;
import com.example.deklaraciifinki.entity.semester.SemesterType;
import com.example.deklaraciifinki.repository.SemesterRepository;
import com.example.deklaraciifinki.repository.SubjectRepository;
import com.example.deklaraciifinki.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class DataInitializr {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;
    private final PasswordEncoder passwordEncoder;


    public DataInitializr(UserRepository userRepository, SubjectRepository subjectRepository, SemesterRepository semesterRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.semesterRepository = semesterRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeData() {
        UserEntity user1 = new UserEntity("riste.stojanov", passwordEncoder.encode("riste"));
        UserEntity user2 = new UserEntity("sasho.gramatikov", passwordEncoder.encode("sasho"));
        userRepository.save(user1);
        userRepository.save(user2);


        SubjectEntity subject1 = new SubjectEntity("Operating Systems");
        SubjectEntity subject2 = new SubjectEntity("Web Programming");
        SubjectEntity subject3 = new SubjectEntity("Databases");

        subjectRepository.save(subject1);
        subjectRepository.save(subject2);
        subjectRepository.save(subject3);

        SemesterEntity semester1 = new SemesterEntity(2022, 2023, SemesterType.Winter);
        semester1.getSubjects().add(subject3);
        semester1.getSubjects().add(subject2);
        SemesterEntity semester2 = new SemesterEntity(2022, 2023, SemesterType.Summer);
        semester2.getSubjects().add(subject1);
        SemesterEntity semester3 = new SemesterEntity(2023,2024,SemesterType.Winter);
        semester3.getSubjects().add(subject1);

        semesterRepository.save(semester1);
        semesterRepository.save(semester2);
        semesterRepository.save(semester3);
    }
}
