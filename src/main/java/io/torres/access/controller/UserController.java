package io.torres.access.controller;

import io.torres.common.vo.response.Response;
import io.torres.common.vo.response.ResponseBuilder;
import io.torres.entity.User;
import io.torres.access.jwt.JwtTokenProvider;
import io.torres.access.repository.UserRepository;
import io.torres.access.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping("/access")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    //회원가입
    @PostMapping("/join")
    public Response join(@RequestBody UserVo userVo) {

        User user = User.builder()
                .email(userVo.getEmail())
                .password(passwordEncoder.encode(userVo.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
        .build();

        userRepository.save(user);

        return new ResponseBuilder()
                .add("id", user.getId())
                .build();
    }

    // 로그인
    @PostMapping("/login")
    public Response login(@RequestBody UserVo userVo) {

        User member = userRepository.findByEmail(userVo.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(userVo.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return new ResponseBuilder()
                .add("token", jwtTokenProvider.createToken(member.getUsername(), member.getRoles()))
                .build();
    }
}
