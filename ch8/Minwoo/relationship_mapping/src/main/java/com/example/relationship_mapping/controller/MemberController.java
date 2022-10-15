package com.example.relationship_mapping.controller;

import com.example.relationship_mapping.domain.Member;
import com.example.relationship_mapping.dto.MemberReqDTO;
import com.example.relationship_mapping.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("")
    public Member create(@RequestBody MemberReqDTO memberReqDTO) {
        return memberService.create(memberReqDTO);
    }

    @GetMapping("")
    public List<Member> findAll() {
        return memberService.findAll();
    }

    @GetMapping("{member_id}")
    public Member findOne(@PathVariable("member_id") Long id) {
        return memberService.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }

    @PutMapping("/{member_id}/{team_id}")
    public Member joinTeam(@PathVariable("member_id") Long memberId, @PathVariable("team_id") Long teamId) {
        return memberService.joinTeam(memberId, teamId);
    }
}
