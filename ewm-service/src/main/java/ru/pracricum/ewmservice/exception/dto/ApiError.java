package ru.pracricum.ewmservice.exception.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.pracricum.ewmservice.exception.ErrorStatusEwm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiError {

    List<String> errors = new ArrayList<>();

    String message;

    String reason;

    ErrorStatusEwm status;

    LocalDateTime timeStamp =  LocalDateTime.now();
}


