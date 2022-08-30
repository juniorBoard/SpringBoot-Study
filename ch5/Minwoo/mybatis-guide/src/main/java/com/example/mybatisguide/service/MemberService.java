package com.example.mybatisguide.service;

import com.example.mybatisguide.dto.CreateMemberDTO;
import com.example.mybatisguide.mapper.MemberMapper;
import com.example.mybatisguide.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private MemberMapper memberMapper;

    @Autowired
    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public int save(CreateMemberDTO dto) {
        Member member = new Member(dto.getName(), dto.getAge());
        return memberMapper.insert(member);
    }

    public List<Member> getAll() {
        return memberMapper.findAll();
    }

    public Member getOne(Long id) {
        return memberMapper.findOneById(id);
    }
}
