package org.money.wiki.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDTO {
    private final String message;

    public ErrorDTO(String message) {
        this.message = message;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }
}
