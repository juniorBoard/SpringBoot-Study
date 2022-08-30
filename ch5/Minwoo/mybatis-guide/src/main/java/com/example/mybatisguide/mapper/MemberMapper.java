package com.example.mybatisguide.mapper;

import com.example.mybatisguide.model.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapper {
    @Insert("INSERT INTO member(name, age) VALUES(#{member.name}, #{member.age})")
    int insert(@Param("member") Member member);

    @Select("SELECT * FROM member")
    List<Member> findAll();

    @Select("SELECT * FROM member WHERE id = #{id}")
    Member findOneById(Long id);
}
