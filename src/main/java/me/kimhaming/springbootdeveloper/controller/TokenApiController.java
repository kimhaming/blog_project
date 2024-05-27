package me.kimhaming.springbootdeveloper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.kimhaming.springbootdeveloper.dto.CreateAccessTokenRequest;
import me.kimhaming.springbootdeveloper.dto.CreateAccessTokenResponse;
import me.kimhaming.springbootdeveloper.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "token", description = "토큰 API")
@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    @Operation(summary = "토큰 발급", description = "새로운 액세스 토큰을 발급받습니다. 요청으로부터 리프레시 토큰을 받아와, 유효한 리프레시 토큰일 경우 새로운 액세스 토큰을 생성하고 반환합니다.")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken
            (@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.
                getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
