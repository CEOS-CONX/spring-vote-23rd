package com.ceos23.spring_boot.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 요청")
public class SignupRequest {

    @Schema(description = "아이디", example = "user123")
    private String username;

    @Schema(description = "비밀번호", example = "Password1234!")
    private String password;

    @Schema(description = "이메일", example = "user@example.com")
    private String email;

    @Schema(description = "파트", example = "BACKEND")
    private String part;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "팀", example = "DIGG_INDIE")
    private String team;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPart() {
        return part;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }
}