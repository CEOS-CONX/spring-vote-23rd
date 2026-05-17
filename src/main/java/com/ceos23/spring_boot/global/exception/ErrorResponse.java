package com.ceos23.spring_boot.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "에러 응답 형식")
public class ErrorResponse {

    @Schema(description = "HTTP 상태 코드", example = "400")
    private final int status;

    @Schema(description = "에러 코드", example = "INVALID_PART_LEADER_VOTE")
    private final String code;

    @Schema(description = "에러 메시지", example = "본인의 파트에 해당하는 파트장 후보에게만 투표할 수 있습니다.")
    private final String message;

    private ErrorResponse(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getHttpStatus().value(),
                errorCode.name(),
                errorCode.getMessage()
        );
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}