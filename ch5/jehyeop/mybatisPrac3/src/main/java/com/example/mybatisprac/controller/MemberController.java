package com.example.mybatisprac.controller;

import com.example.mybatisprac.model.dto.MemberDto;
import com.example.mybatisprac.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/test")
    public List<MemberDto> test() {
        return memberService.getAllDataList();
    }

    @PostMapping("/test")
    public MemberDto test2(){
        return memberService.postData();
    }
}
