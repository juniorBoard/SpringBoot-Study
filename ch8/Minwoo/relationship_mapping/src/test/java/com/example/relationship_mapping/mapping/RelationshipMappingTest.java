package com.example.relationship_mapping.mapping;

import com.example.relationship_mapping.domain.Member;
import com.example.relationship_mapping.domain.Profile;
import com.example.relationship_mapping.domain.Team;
import com.example.relationship_mapping.repository.MemberRepository;
import com.example.relationship_mapping.repository.ProfileRepository;
import com.example.relationship_mapping.repository.TeamRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RelationshipMappingTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    @DisplayName("멤버 및 프로필 저장 테스트")
    void memberSaveTest01() {
        // 멤버 객체 생성
        Member member = new Member();
        member.setId(1L);
        member.setEmail("jjanggu@gmail.com");

        // 프로필 객체 생성
        Profile profile = new Profile();
        profile.setId(1L);
        profile.setName("신짱구");
        profile.setAge(5);

        // 멤버 프로필 설정
        member.setProfile(profile);

        // 프로필 저장.
        profileRepository.save(profile);

        // 멤버 저장.
        memberRepository.save(member);

        assertEquals(1, memberRepository.countById(1L));
        assertEquals(1, profileRepository.countById(1L));
    }

    @Test
    @DisplayName("멤버 및 프로필 저장 필드 설정 테스트")
    void memberSaveTest02() {

        // 멤버 객체 생성
        Member member = new Member();
        member.setEmail("jjanggu@gmail.com");

        // 프로필 객체 생성
        Profile profile = new Profile();
        profile.setName("신짱구");
        profile.setAge(5);
        profile.setMember(member);


        // 프로필 저장.
        profileRepository.save(profile);

        // 멤버 저장.
        memberRepository.save(member);

        Member savedMember = memberRepository.findById(1L).get();

        System.out.println(savedMember);
        System.out.println(savedMember.getProfile());
    }
    @Test
    @DisplayName("팀 멤버 다대일 일대다 양방향 매핑 테스트")
    void getMembersInTeamTest() {
        Member member1 = new Member();
        member1.setEmail("member1@gmail.com");
        Member member2 = new Member();
        member2.setEmail("member2@gmail.com");

        Team team = new Team();
        team.setName("주니어 보드");

        team.add(member1);
        team.add(member2);

        teamRepository.save(team);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = team.getMembers();

        System.out.println(members);
    }
}
