package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;

public class MemberDTO {
    private String password;
    private String email;

    public MemberDTO(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public Member toEntity() {
    return new Member(null ,password, email);
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
