package com.doxxx.backend.member;

import com.doxxx.backend.jwt.TokenProvider;
import com.doxxx.backend.token.TokenResponse;
import com.doxxx.backend.web.exception.ApiException;
import com.doxxx.backend.web.exception.ErrorCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider, MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
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

    public TokenResponse signIn(SignInRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();

        Authentication authentication;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            throw new ApiException(ErrorCode.BAD_CREDENTIALS, "이메일 또는 비밀번호를 잘못 입력했습니다.");
        }

        TokenResponse tokenResponse = tokenProvider.generateTokenDto(authentication.getName(), authentication.getAuthorities());

        return tokenResponse;
    }

    public Optional<Member> findOptionalByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND, "사용자 정보가 없습니다. id: " + memberId));
    }
}
