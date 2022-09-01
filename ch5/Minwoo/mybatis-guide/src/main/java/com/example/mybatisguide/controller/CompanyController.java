package com.example.mybatisguide.controller;

import com.example.mybatisguide.dto.CreateCompanyDTO;
import com.example.mybatisguide.model.Company;
import com.example.mybatisguide.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("")
    public int create(@RequestBody CreateCompanyDTO createCompanyDTO) {
        return companyService.create(createCompanyDTO);
    }

    @GetMapping("/{name}")
    public Company read(@PathVariable String name) {
        return companyService.findByName(name);
    }
}
