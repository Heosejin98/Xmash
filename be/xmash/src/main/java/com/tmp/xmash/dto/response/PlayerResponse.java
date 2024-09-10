package com.tmp.xmash.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record PlayerResponse(String userId, String userName, String profileUrl) {

    /**
     * user Id
     * @return userId
     */
    @Override
    @Schema(name = "userId", example = "test", description = "userId")
    @JsonProperty("userId")
    public String userId() {
        return userId;
    }

    /**
     * userName
     * @return userName
     */
    @Override
    @Schema(name = "userName", example = "허세진", description = "user name")
    @JsonProperty("userName")
    public String userName() {
        return userName;
    }


    /**
     * 프로필 사진 경로
     * @return profileUrl
     */
    @Override
    @Schema(name = "profileUrl", example = "player123", description = "/images/profiles/test.jpg - [아직 미구현 항상 null]")
    @JsonProperty("profileUrl")
    public String profileUrl() {
        return profileUrl;
    }
}
