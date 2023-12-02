package com.wit.blogs.dto;

public record LoginResponse(String accessToken, String refreshToken, Long expiresAt) {
}
