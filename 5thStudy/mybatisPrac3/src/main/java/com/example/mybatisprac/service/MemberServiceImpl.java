package com.example.mybatisprac.service;

import com.example.mybatisprac.model.dao.MemberDao;
import com.example.mybatisprac.model.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    @Autowired
    private final MemberDao memberDao;

    @Override
    public List<MemberDto> getAllDataList() {
        return memberDao.getAllDataList();
    }

    @Override
    public MemberDto postData() {
        return memberDao.postData();
    }
}
