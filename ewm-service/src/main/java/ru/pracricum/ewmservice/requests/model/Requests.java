package ru.pracricum.ewmservice.requests.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import ru.pracricum.ewmservice.event.model.Event;
import ru.pracricum.ewmservice.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "request", schema = "public")
public class Requests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    Long id;

    //@ManyToOne
    //@JoinColumn(name = "event_id")
    @Column(name = "event_id")
    Long event;

    //@ManyToOne
    //@JoinColumn(name = "user_id")
    @Column(name = "user_id")
    Long requester;

    @Column(name = "created")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    ParticipationStatus status;
}