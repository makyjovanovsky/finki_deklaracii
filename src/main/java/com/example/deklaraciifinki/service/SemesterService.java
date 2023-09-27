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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final DeclarationRepository declarationRepository;

    public SemesterService(SemesterRepository semesterRepository, UserRepository userRepository, SubjectRepository subjectRepository, DeclarationRepository declarationRepository) {
        this.semesterRepository = semesterRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.declarationRepository = declarationRepository;
    }

    public List<SemesterEntity> findAll() {
        return semesterRepository.findAll();
    }

    public Page<SemesterEntity> findAllPagination(int page, int pageSize) throws Exception {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<SemesterEntity> semesters = semesterRepository.findAll(pageable);
        for (SemesterEntity s : semesters) {
            List<Long> indicesToRemove = new ArrayList<>();

            for (int i = 0; i < s.getDeclarations().size(); i++) {
                DeclarationEntity d = s.getDeclarations().get(i);
                if (!Objects.equals(d.getUser().getId(), getLoggedInUser().getId())) {
                    indicesToRemove.add(d.getId());
                }
            }
            for (long index : indicesToRemove) {
                s.getDeclarations().remove(declarationRepository.findById(index).orElseThrow(Exception::new));
            }
        }
        return semesters;
    }

    public SemesterEntity downloadReport(Long id) throws Exception {
        SemesterEntity semester = semesterRepository.findById(id).orElseThrow(Exception::new);
        List<Integer> indicesToRemove = new ArrayList<>();
        for (int i = 0; i < semester.getDeclarations().size(); i++) {
            DeclarationEntity d = semester.getDeclarations().get(i);
            if (!Objects.equals(d.getUser().getId(), getLoggedInUser().getId())) {
                indicesToRemove.add(i);
            }
        }
        for (int index : indicesToRemove) {
            semester.getDeclarations().remove(index);
        }

        return semester;
    }

    public void importData(Long id, MultipartFile file) throws Exception {
        SemesterEntity semester = semesterRepository.findById(id).orElseThrow(Exception::new);

        try {
            List<String[]> rows = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            String line;

            while ((line = br.readLine()) != null) {
                String[] columns = line.split("\t");
                rows.add(columns);
            }

            boolean flag = true;

            for (String[] row : rows) {
                if (flag) {
                    flag = false;
                } else {
                    SubjectEntity subject = subjectRepository.findByName(row[0]).get();
                    DeclarationEntity declaration = new DeclarationEntity(subject, ClassType.valueOf(row[1]), Integer.parseInt(row[2]), Boolean.parseBoolean(row[3]), Language.valueOf(row[4]), Integer.parseInt(row[5]), Boolean.parseBoolean(row[6]), row[7]);
                    declaration.setUser(getLoggedInUser());

                    declarationRepository.save(declaration);
                    semester.getDeclarations().add(declaration);
                }
            }

            semesterRepository.save(semester);

            br.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

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
