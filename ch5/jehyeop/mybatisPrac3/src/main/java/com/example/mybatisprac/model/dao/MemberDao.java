package com.example.mybatisprac.model.dao;

import com.example.mybatisprac.model.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MemberDao {
    // 메소드 명이 mapper.xml 에서 설정한 id 값과 같아야한다.
    List<MemberDto> getAllDataList();
    MemberDto postData();
}
