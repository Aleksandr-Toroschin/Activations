package ru.toroschin.ds.activations.error_handling;

import lombok.Data;

@Data
public class ErrorMessage {
    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
