package ru.pracricum.ewmservice.requests.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.pracricum.ewmservice.event.model.Event.EVENTS_ID;
import static ru.pracricum.ewmservice.requests.model.Requests.SCHEMA_TABLE;
import static ru.pracricum.ewmservice.requests.model.Requests.TABLE_REQUESTS;
import static ru.pracricum.ewmservice.user.model.User.USERS_ID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = TABLE_REQUESTS, schema = SCHEMA_TABLE)
public class Requests {

    public static final String TABLE_REQUESTS = "request";
    public static final String SCHEMA_TABLE = "public";
    public static final String REQUESTS_ID = "request_id";
    public static final String REQUESTS_CREATED = "created";
    public static final String REQUESTS_STATE = "state";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = REQUESTS_ID)
    Long id;

    @Column(name = EVENTS_ID)
    Long event;

    @Column(name = USERS_ID)
    Long requester;

    @Column(name = REQUESTS_CREATED)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(name = REQUESTS_STATE)
    ParticipationStatus status;
}