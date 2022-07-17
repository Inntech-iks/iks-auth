package com.inn.iks.auth.entitiy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageDTO {
    private String statusCode;
    private String message;
}
