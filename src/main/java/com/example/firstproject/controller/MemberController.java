package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberDTO;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

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
        return "redirect:/members/" + saved.getId();
    }

    //목록보기
    @GetMapping("/members")
    public String index(Model model){
        //1. db에서 조회받아
        List<Member> members = (List<Member>)memberRepository.findAll();
        //2. 모델로 변환
        model.addAttribute("members", members);
        return "members/index";
    }

    //상세보기
    @GetMapping("/members/{id}")
    public String index(@PathVariable Long id, Model model){
        //db에서 조회한 건 받아
        Member member = memberRepository.findById(id).orElse(null);
        //모델로 변환
        model.addAttribute("member", member);
        return "members/detail";
    }
}
