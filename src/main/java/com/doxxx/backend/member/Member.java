package com.doxxx.backend.member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority = Authority.ROLE_MEMBER;

    protected Member() {
    }

    @Builder
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public enum Authority {
        ROLE_MEMBER
    }
}
