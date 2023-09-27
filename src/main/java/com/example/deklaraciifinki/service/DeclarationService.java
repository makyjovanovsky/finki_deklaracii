package com.example.deklaraciifinki.service;

import com.example.deklaraciifinki.entity.SubjectEntity;
import com.example.deklaraciifinki.entity.UserEntity;
import com.example.deklaraciifinki.entity.declaration.ClassType;
import com.example.deklaraciifinki.entity.declaration.DeclarationEntity;
import com.example.deklaraciifinki.entity.declaration.Language;
import com.example.deklaraciifinki.entity.semester.SemesterEntity;
import com.example.deklaraciifinki.repository.DeclarationRepository;
import com.example.deklaraciifinki.repository.SemesterRepository;
import com.example.deklaraciifinki.repository.SubjectRepository;
import com.example.deklaraciifinki.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class DeclarationService {

    private final DeclarationRepository declarationRepository;
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public DeclarationService(DeclarationRepository declarationRepository, SemesterRepository semesterRepository, SubjectRepository subjectRepository, UserRepository userRepository) {
        this.declarationRepository = declarationRepository;
        this.semesterRepository = semesterRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    public DeclarationEntity findById(Long id) throws Exception {
        return declarationRepository.findById(id).orElseThrow(Exception::new);
    }

    public void addNewDeclaration(Long subjectId,
                                  String classType,
                                  int numberOfClasses,
                                  boolean otherTeacher,
                                  String language,
                                  int numberOfStudents,
                                  boolean consultative,
                                  String note,
                                  Long semesterId) throws Exception {
        SubjectEntity subject = subjectRepository.findById(subjectId).orElseThrow(Exception::new);
        SemesterEntity semester = semesterRepository.findById(semesterId).orElseThrow(Exception::new);

        DeclarationEntity declaration = new DeclarationEntity(subject, ClassType.valueOf(classType), numberOfClasses, otherTeacher, Language.valueOf(language), numberOfStudents, consultative, note);
        declaration.setUser(getLoggedInUser());
        declarationRepository.save(declaration);

        semester.getDeclarations().add(declaration);
        semesterRepository.save(semester);
    }

    public void editDeclaration(Long declarationId,
                                Long subjectId,
                                String classType,
                                int numberOfClasses,
                                boolean otherTeacher,
                                String language,
                                int numberOfStudents,
                                boolean consultative,
                                String note,
                                Long semesterId) throws Exception {

        DeclarationEntity declaration = declarationRepository.findById(declarationId).orElseThrow(Exception::new);

        declaration.setClassType(ClassType.valueOf(classType));
        declaration.setNumberOfClasses(numberOfClasses);
        declaration.setOtherTeacher(otherTeacher);
        declaration.setLanguage(Language.valueOf(language));
        declaration.setNumberOfStudents(numberOfStudents);
        declaration.setConsultative(consultative);
        declaration.setNote(note);

        if (declaration.getSubject().getId() != subjectId) {
            declaration.setSubject(subjectRepository.findById(subjectId).orElseThrow(Exception::new));
        }

        declarationRepository.save(declaration);
    }

    public void deleteDeclaration(Long id) throws Exception {
        DeclarationEntity declaration = declarationRepository.findById(id).orElseThrow(Exception::new);
        List<SemesterEntity> semesters = semesterRepository.findAllByDeclarationsContaining(declaration);
        for (SemesterEntity s : semesters) {
            s.getDeclarations().remove(declaration);
            semesterRepository.save(s);
        }
        declarationRepository.delete(declaration);
    }

    public List<SubjectEntity> findAll(Long declarationId) throws Exception {
        return semesterRepository.findByDeclarationsContaining(declarationRepository.findById(declarationId).orElseThrow(Exception::new)).get().getSubjects();
    }

    public UserEntity getLoggedInUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {

            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                return userRepository.findByUsername(username).orElseThrow(Exception::new);
            }
        }
        return null;
    }


}
