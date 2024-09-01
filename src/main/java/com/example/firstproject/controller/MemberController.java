package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberDTO;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/signup")
    public String signUp(){
        return "/members/new";
    }

    @PostMapping("/members/create")
    public String createMember(MemberDTO memberDTO){
        log.info(memberDTO.toString());
//        System.out.println("memberDTO = " + memberDTO);
        //1. dto를 엔티티로 변환
        Member member = memberDTO.toEntity();
        log.info(member.toString());
//        System.out.println("member = " + member);


        //2.엔티티를 db에 저장.
        Member saved = memberRepository.save(member);
        log.info(saved.toString());
//        System.out.println("saved = " + saved);
        return "";
    }
}
