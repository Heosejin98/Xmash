package com.tmp.xmash.dto.response;

import com.tmp.xmash.type.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 요청에 대한 응답 객체")
public record LoginResponse(
        @Schema(description = "사용자의 고유 식별자", example = "user123")
        String userId,

        @Schema(description = "사용자의 이름", example = "홍길동")
        String userName,

        @Schema(description = "사용자의 성별", example = "남", allowableValues = {"남", "여"})
        Gender gender
) {

}
