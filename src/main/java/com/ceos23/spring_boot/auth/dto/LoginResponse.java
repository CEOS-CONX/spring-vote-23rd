package com.ceos23.spring_boot.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 응답")
public record LoginResponse(
        @Schema(description = "Access Token", example = "sample-access-token")
        String accessToken,

        @Schema(description = "Refresh Token", example = "sample-refresh-token")
        String refreshToken,

        @Schema(description = "사용자 이름", example = "홍길동")
        String username,

        @Schema(description = "파트", example = "BACKEND")
        String part,

        @Schema(description = "팀", example = "CONX")
        String team
) {
    public static LoginResponse of(String accessToken, String refreshToken, String username, String part, String team) {
        return new LoginResponse(accessToken, refreshToken, username, part, team);
    }
}