package com.example.deklaraciifinki.controller;

import com.example.deklaraciifinki.entity.declaration.DeclarationEntity;
import com.example.deklaraciifinki.entity.semester.SemesterEntity;
import com.example.deklaraciifinki.service.SemesterService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SemesterController {

    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @GetMapping("/download/report/{id}")
    public void downloadReport(@PathVariable(name = "id") Long id, HttpServletResponse response) throws Exception {
        SemesterEntity semester = semesterService.downloadReport(id);
        StringBuilder tsvContent = new StringBuilder();
        tsvContent.append("Subject\tType of Class\tNumber of classes\tOther teacher\tLanguage\tNumber of students\tConsultative\tNote\n");
        for (DeclarationEntity d : semester.getDeclarations()) {
            tsvContent.append(new String(d.getSubject().getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)).append("\t")
                    .append(d.getClassType()).append("\t")
                    .append(d.getNumberOfClasses()).append("\t")
                    .append(d.isOtherTeacher()).append("\t")
                    .append(d.getLanguage().toString()).append("\t")
                    .append(d.getNumberOfStudents()).append("\t")
                    .append(d.isConsultative()).append("\t")
                    .append(new String(d.getNote().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)).append("\n");
        }

        response.setContentType("text/tab-separated-values; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=report.tsv");


        try (OutputStream outputStream = response.getOutputStream();
             Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            writer.write(tsvContent.toString());
        }
    }

    @PostMapping("/import/report/{id}")
    public String importData(@PathVariable(name = "id") Long id, @RequestParam(name = "file") MultipartFile file) throws Exception {
        System.out.println(id);
        semesterService.importData(id, file);
        return "redirect:/";
    }
}
