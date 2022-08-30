package com.example.mybatisguide.service;

import com.example.mybatisguide.dao.CompanyDAO;
import com.example.mybatisguide.dto.CreateCompanyDTO;
import com.example.mybatisguide.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private CompanyDAO companyDAO;

    @Autowired
    public CompanyService(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public int create(CreateCompanyDTO dto) {
        Company company = new Company(dto.getName(), dto.getCeo());
        return companyDAO.insertCompany(company);
    }

    public Company findByName(String name) {
        return companyDAO.selectCompanyByName(name);
    }
}
