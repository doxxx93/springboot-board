package com.doxxx.backend.member;

import jakarta.persistence.*;
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

    public enum Authority {
        ROLE_MEMBER
    }
}
