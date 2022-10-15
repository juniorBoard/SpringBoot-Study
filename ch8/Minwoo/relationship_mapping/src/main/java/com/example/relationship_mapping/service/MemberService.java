package com.example.relationship_mapping.service;

import com.example.relationship_mapping.domain.Member;
import com.example.relationship_mapping.domain.Profile;
import com.example.relationship_mapping.domain.Team;
import com.example.relationship_mapping.dto.MemberReqDTO;
import com.example.relationship_mapping.repository.MemberRepository;
import com.example.relationship_mapping.repository.ProfileRepository;
import com.example.relationship_mapping.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final TeamRepository teamRepository;

    public MemberService(MemberRepository memberRepository, ProfileRepository profileRepository, TeamRepository teamRepository) {
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
        this.teamRepository = teamRepository;
    }

    public Member create(MemberReqDTO memberReqDTO) {
        Profile savedProfile = profileRepository.save(new Profile(memberReqDTO.getName(), memberReqDTO.getAge()));
        Member member = new Member();
        member.setEmail(memberReqDTO.getEmail());
        member.setProfile(savedProfile);

        return memberRepository.save(member);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }
    @Transactional
    public Member joinTeam(Long memberId, Long teamId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException());
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException());

        team.add(member);

        return member;
    }
}
