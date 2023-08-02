package com.doxxx.backend.member;

import com.doxxx.backend.web.exception.ApiException;
import com.doxxx.backend.web.exception.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SignUpResponse signUp(SignUpRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new ApiException(ErrorCode.ALREADY_EXIST_MEMBER, "이미 존재하는 유저입니다. email: " + request.email());
        }

        Member member = request.toMember(passwordEncoder);
        memberRepository.save(member);
        return SignUpResponse.from(member);
    }
}
