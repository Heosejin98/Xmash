package com.tmp.xmash.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "유저 정보 수정 요청")
public record UserProfileRequest(
        @Schema(description = "유저 id", example = "sejin")
        String userId,

        @Schema(description = "유저 명", example = "허세진")
        String userName,

        @Schema(description = "유저 Email", example = "seheo@ex-em.com")
        String userEmail,

        @Schema(description = "유저 프로필 사진 (이거 그냥 신경쓰지마세요 나중에 구현함 일단 사람들 이미지 내가 직접 서버에 저장해두고 화면서 뿌릴꺼임)")
        MultipartFile userImage
) {

}
