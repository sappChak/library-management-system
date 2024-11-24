package org.example.bookstore.dto.response;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {
    private String timestamp;
    private String message;
    private String path;

    public InvalidLoginResponse(String timestamp, String message, String path) {
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
    }
}
