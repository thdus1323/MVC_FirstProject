package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long id;
    private String password;
    private String email;

    public Member toEntity() {
    return new Member(id ,password, email);
    }
}
