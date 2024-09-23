package com.tmp.xmash.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 플레이어 정보를 반환하는 DTO.
 */
@Schema(name = "PlayerResponse", description = "플레이어 정보 응답 모델")
public record PlayerResponse(
        @Schema(name = "userId", example = "test", description = "사용자 ID")
        String userId,

        @Schema(name = "userName", example = "허세진", description = "사용자 이름")
        String userName,

        @Schema(name = "profileUrl", example = "/images/profiles/test.jpg", description = "프로필 사진 경로 (아직 미구현, 항상 null)")
        String profileUrl
) {
}