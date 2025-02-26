package com.challengerstory.chattingstory.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //400
    WRONG_ENTRY_POINT(40000, HttpStatus.BAD_REQUEST, "잘못된 접근입니다"),
    MISSING_REQUEST_PARAMETER(40001, HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."),
    INVALID_PARAMETER_FORMAT(40002, HttpStatus.BAD_REQUEST, "요청에 유효하지 않은 인자 형식입니다."),
    BAD_REQUEST_JSON(40003, HttpStatus.BAD_REQUEST, "잘못된 JSON 형식입니다."),
    // 데이터 무결성 위반 추가(ex: db의 NOT NULL 속성)
    DATA_INTEGRITY_VIOLATION(40005, HttpStatus.BAD_REQUEST,
            "데이터 무결성 위반입니다. 필수 값이 누락되었거나 유효하지 않습니다."),

    AUTHENTICATION_FAILED(4010001, HttpStatus.UNAUTHORIZED, "인증에 실패했습니다"),
    INVALID_TOKEN(4010002, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다"),
    EXPIRED_TOKEN(4010003, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다"),
    BAD_CREDENTIALS(4010004, HttpStatus.UNAUTHORIZED, "아이디와 비밀번호가 일치하지 않습니다."),
    TOKEN_NOT_FOUND(4010005, HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다"),
    ACCESS_TOKEN_BLACKLISTED(4010006, HttpStatus.FORBIDDEN, "블랙리스트된 토큰입니다"),
    ACCESS_TOKEN_EXPIRED(4010006, HttpStatus.BAD_REQUEST, "만료된 Access 토큰입니다"),
    // 권한 부족 (403)
    ACCESS_DENIED(4030001, HttpStatus.FORBIDDEN, "접근 권한이 없습니다"),
    INSUFFICIENT_PERMISSIONS(4030002, HttpStatus.FORBIDDEN, "필요한 권한이 부족합니다"),
    INSUFFICIENT_AUTHENTICATION(4040003, HttpStatus.UNAUTHORIZED, "인증 정보가 충분하지 않습니다"),
    // 계정 관련 (400)
    ACCOUNT_LOCKED(4000001, HttpStatus.BAD_REQUEST, "계정이 잠겼습니다"),
    ACCOUNT_DISABLED(4000002, HttpStatus.BAD_REQUEST, "비활성화된 계정입니다"),
    ACCOUNT_EXPIRED(4000003, HttpStatus.BAD_REQUEST, "만료된 계정입니다"),
    INVALID_REQUEST_FORMAT(4000004, HttpStatus.BAD_REQUEST, "잘못된 요청 형식입니다,"),
    // 토큰 관련 (400)
    TOKEN_ALREADY_USED(4000004, HttpStatus.BAD_REQUEST, "이미 사용된 토큰입니다"),
    INVALID_REFRESH_TOKEN(4000005, HttpStatus.BAD_REQUEST, "유효하지 않은 리프래시 토큰입니다"),
    ACCESS_TOKEN_NOT_FOUND(4000006, HttpStatus.BAD_REQUEST, "Access Token을 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(4000007, HttpStatus.BAD_REQUEST, "Refresh Token을 찾을 수 없습니다"),
    REFRESH_TOKEN_EXPIRED(4000007, HttpStatus.BAD_REQUEST, "Refresh Token이 만료되었습니다"),
    COOKIE_NOT_FOUND(4000008, HttpStatus.BAD_REQUEST, "Cookie를 찾을 수 없습니다"),
    // 보안 위반 (400)
    MULTIPLE_LOGIN_DETECTED(4000006, HttpStatus.BAD_REQUEST, "다중 로그인이 감지되었습니다"),
    REFRESH_AUTHENTICATION_FAILED(4000007, HttpStatus.BAD_REQUEST, "토큰 재발급 실패"),
    JWT_AUTHENTICATION_ERROR(4000008, HttpStatus.BAD_REQUEST, "JWT 검증중 에러가 발생했습니다"),


    INTERNAL_SERVER_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}
