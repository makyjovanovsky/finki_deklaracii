package com.example.deklaraciifinki.controller;

import com.example.deklaraciifinki.entity.declaration.DeclarationEntity;
import com.example.deklaraciifinki.entity.semester.SemesterEntity;
import com.example.deklaraciifinki.entity.semester.SemesterType;
import com.example.deklaraciifinki.service.SemesterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    private final SemesterService semesterService;

    public PageController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @GetMapping("/")
    public String getIndexPage(Model model) {
        model.addAttribute("semesters", semesterService.findAll());
        return "index";

    }

    @GetMapping("/list")
    public String getDeclarationList(@RequestParam(defaultValue = "0") int page, Model model) throws Exception {
        model.addAttribute("semesters", semesterService.findAllPagination(page, 2));
        model.addAttribute("type", SemesterType.Winter);
        return "list";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }


}
