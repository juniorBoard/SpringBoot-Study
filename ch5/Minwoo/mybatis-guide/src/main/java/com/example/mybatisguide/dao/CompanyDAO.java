package com.example.mybatisguide.dao;

import com.example.mybatisguide.model.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface CompanyDAO {
    int insertCompany(@Param("company") Company company);
    Company selectCompanyByName(String name);
}
