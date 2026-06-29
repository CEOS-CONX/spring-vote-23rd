package com.ceos23.spring_boot.auth.service;

import com.ceos23.spring_boot.auth.dto.*;
import com.ceos23.spring_boot.global.exception.CustomException;
import com.ceos23.spring_boot.global.exception.ErrorCode;
import com.ceos23.spring_boot.global.security.token.JWTType;
import com.ceos23.spring_boot.global.security.token.TokenProvider;
import com.ceos23.spring_boot.global.security.userDetails.CustomUserDetails;
import com.ceos23.spring_boot.user.domain.Member;
import com.ceos23.spring_boot.user.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public SignupResponse signup(SignupRequest request) {
        if (memberRepository.existsByUserLogInId(request.userId())){
            throw new CustomException(ErrorCode.DUPLICATE_USERID);
        }

        if (memberRepository.existsByUserEmail(request.email())){
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = encoder.encode(request.password());
        Member member = Member.create(request, encodedPassword);
        Member savedMember = memberRepository.save(member);

        return SignupResponse.of(savedMember.getId(), savedMember.getUsername());
    }


    public LoginServiceResponseDTO login(LoginRequest request) {
        String userLoginId = request.userLoginId();
        String password = request.password();

        Member member = memberRepository.findByUserLogInId(userLoginId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        if (!encoder.matches(password, member.getPassword())){
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        CustomUserDetails userDetails = CustomUserDetails.of(member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        String accessToken = tokenProvider.createToken(member.getId(), authentication, JWTType.ACCESS);
        String refreshToken = tokenProvider.createToken(member.getId(), authentication, JWTType.REFRESH);

        redisTemplate.opsForValue().set(
                "refreshToken:" + member.getId(),
                refreshToken,
                JWTType.REFRESH.getValidTime(),
                TimeUnit.SECONDS
        );

        LoginResponseDTO info = LoginResponseDTO.create(member);

        return LoginServiceResponseDTO.of(accessToken, refreshToken, info);
    }

    //리프레시 토큰
    @Transactional(readOnly = true)
    public LoginServiceResponseDTO reIssueToken(String refreshToken){
        long userId = tokenProvider.getIdFromToken(refreshToken);
        Member member = memberRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        String refreshTokenInRedis = redisTemplate.opsForValue().get(
                "refreshToken:" + userId
        );

        if (refreshTokenInRedis == null){
            throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        if (!refreshToken.equals(refreshTokenInRedis)){
            redisTemplate.delete("refreshToken:" + userId);
            throw new CustomException(ErrorCode.REFRESH_TOKEN_REUSED);
        }

        CustomUserDetails userDetails = CustomUserDetails.of(member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        String accessToken = tokenProvider.createToken(userDetails.getId(), authentication, JWTType.ACCESS);
        String newRefreshToken = tokenProvider.createToken(userDetails.getId(), authentication, JWTType.REFRESH);

        redisTemplate.opsForValue().set(
                "refreshToken:" + member.getId(),
                newRefreshToken,
                JWTType.REFRESH.getValidTime(),
                TimeUnit.SECONDS
        );

        return LoginServiceResponseDTO.of(accessToken, newRefreshToken, LoginResponseDTO.create(member));
    }
}