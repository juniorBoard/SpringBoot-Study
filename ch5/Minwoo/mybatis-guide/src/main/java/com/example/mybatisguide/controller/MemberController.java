package com.example.mybatisguide.controller;

import com.example.mybatisguide.dto.CreateMemberDTO;
import com.example.mybatisguide.model.Member;
import com.example.mybatisguide.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MemberController {
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public int create(@RequestBody CreateMemberDTO dto) {
        return memberService.save(dto);
    }

    @GetMapping("/members")
    public List<Member> readAll() {
        return memberService.getAll();
    }

    @GetMapping("/members/{id}")
    public Member read(@PathVariable Long id) {
        return memberService.getOne(id);
    }
}
