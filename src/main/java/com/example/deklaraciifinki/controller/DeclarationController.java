package com.example.deklaraciifinki.controller;

import com.example.deklaraciifinki.entity.SubjectEntity;
import com.example.deklaraciifinki.entity.declaration.ClassType;
import com.example.deklaraciifinki.entity.declaration.Language;
import com.example.deklaraciifinki.service.DeclarationService;
import com.example.deklaraciifinki.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeclarationController {

    private final SubjectService subjectService;
    private final DeclarationService declarationService;

    public DeclarationController(SubjectService subjectService, DeclarationService declarationService) {
        this.subjectService = subjectService;
        this.declarationService = declarationService;
    }

    @GetMapping("/add/declaration")
    public String getAddDeclarationPage(@RequestParam(name = "semester") Long id, Model model) throws Exception {
        model.addAttribute("subjects", subjectService.findAllBySemester(id));
        model.addAttribute("classes", ClassType.values());
        model.addAttribute("languages", Language.values());
        model.addAttribute("semester", id);
        return "form";
    }

    @GetMapping("/edit/declaration/{id}")
    public String getEditDeclarationPage(@PathVariable(name = "id") Long id, Model model) throws Exception {
        model.addAttribute("declaration", declarationService.findById(id));
        model.addAttribute("subjects", declarationService.findAll(id));
        model.addAttribute("classes", ClassType.values());
        model.addAttribute("languages", Language.values());
        model.addAttribute("semester", id);
        return "form";
    }


    @PostMapping("/add/declaration")
    public String addDeclaration(@RequestParam(name = "subject") Long subjectId,
                                 @RequestParam(name = "classType") String classType,
                                 @RequestParam(name = "numberOfClasses") int numberOfClasses,
                                 @RequestParam(name = "otherTeacher") boolean otherTeacher,
                                 @RequestParam(name = "language") String language,
                                 @RequestParam(name = "numberOfStudents") int numberOfStudents,
                                 @RequestParam(name = "consultative", required = false) boolean consultative,
                                 @RequestParam(name = "note") String note,
                                 @RequestParam(name = "semester") Long semesterId) throws Exception {
        declarationService.addNewDeclaration(subjectId, classType, numberOfClasses, otherTeacher, language, numberOfStudents, consultative, note, semesterId);
        return "redirect:/";
    }

    @PostMapping("/edit/declaration/{id}")
    public String addDeclaration(@PathVariable(name = "id") Long declarationId,
                                 @RequestParam(name = "subject") Long subjectId,
                                 @RequestParam(name = "classType") String classType,
                                 @RequestParam(name = "numberOfClasses") int numberOfClasses,
                                 @RequestParam(name = "otherTeacher") boolean otherTeacher,
                                 @RequestParam(name = "language") String language,
                                 @RequestParam(name = "numberOfStudents") int numberOfStudents,
                                 @RequestParam(name = "consultative", required = false) boolean consultative,
                                 @RequestParam(name = "note") String note,
                                 @RequestParam(name = "semester") Long semesterId) throws Exception {
        declarationService.editDeclaration(declarationId, subjectId, classType, numberOfClasses, otherTeacher, language, numberOfStudents, consultative, note, semesterId);
        return "redirect:/";
    }


    @PostMapping("/delete/declaration/{id}")
    public String deleteDeclaration(@PathVariable(name = "id") Long id) throws Exception {
        declarationService.deleteDeclaration(id);
        return "redirect:/list";
    }

}
