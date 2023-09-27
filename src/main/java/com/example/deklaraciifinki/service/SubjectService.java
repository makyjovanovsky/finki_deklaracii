package com.example.deklaraciifinki.service;

import com.example.deklaraciifinki.entity.SubjectEntity;
import com.example.deklaraciifinki.repository.SemesterRepository;
import com.example.deklaraciifinki.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;

    public SubjectService(SubjectRepository subjectRepository, SemesterRepository semesterRepository) {
        this.subjectRepository = subjectRepository;
        this.semesterRepository = semesterRepository;
    }

    public List<SubjectEntity> findAllBySemester(Long id) throws Exception {
        return subjectRepository.findAllBySemestersContaining(semesterRepository.findById(id).orElseThrow(Exception::new));
    }


}
