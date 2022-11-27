package ru.pracricum.ewmservice.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {

    String error;
    String message;
    ErrorStatusEwm status;

}