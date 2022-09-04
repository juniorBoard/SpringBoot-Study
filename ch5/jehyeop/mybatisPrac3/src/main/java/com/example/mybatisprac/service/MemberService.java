package com.example.mybatisprac.service;

import com.example.mybatisprac.model.dto.MemberDto;

import java.util.List;

public interface MemberService {
    List<MemberDto> getAllDataList();
    MemberDto postData();
}
