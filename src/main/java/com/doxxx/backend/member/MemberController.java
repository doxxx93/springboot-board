package com.doxxx.backend.member;

import com.doxxx.backend.dto.member.SignInRequest;
import com.doxxx.backend.dto.member.SignUpRequest;
import com.doxxx.backend.dto.member.SignUpResponse;
import com.doxxx.backend.specification.MemberSpecification;
import com.doxxx.backend.dto.member.TokenResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController implements MemberSpecification {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody @Valid SignUpRequest request) {
        SignUpResponse signUpResponse = memberService.signUp(request);
        return ResponseEntity.created(URI.create("/members/" + signUpResponse.id())).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signIn(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(memberService.signIn(request));
    }
}
