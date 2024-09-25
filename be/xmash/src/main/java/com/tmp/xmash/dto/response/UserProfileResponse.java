package com.tmp.xmash.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserProfileResponse(
        @Schema(example = "test", description = "사용자 ID")
        String userId,

        @Schema(example = "허세진", description = "사용자 이름")
        String userName,

        @Schema(example = "seheo@ex-em.com", description = "사용자 이메일")
        String userEmail,

        @Schema(example = "/images/profiles/test.jpg", description = "프로필 사진 경로 (아직 미구현, 항상 null)")
        String profileUrl
) {

}
