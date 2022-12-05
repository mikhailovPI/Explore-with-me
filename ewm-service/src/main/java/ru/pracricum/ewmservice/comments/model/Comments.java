package ru.pracricum.ewmservice.comments.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import ru.pracricum.ewmservice.user.model.User;

import javax.persistence.*;

import java.time.LocalDateTime;

import static ru.pracricum.ewmservice.categories.model.Categories.SCHEMA_TABLE;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = Comments.TABLE_COMMENTS, schema = SCHEMA_TABLE)
public class Comments {

    public final static String TABLE_COMMENTS = "comments";
    public final static String SCHEMA_TABLE = "public";
    public final static String COMMENT_ID = "comment_id";
    public final static String COMMENT_TEXT = "comment_text";
    public final static String COMMENT_USER_ID = "user_id";

    public final static String COMMENT_PUBLISHED_ON = "published_on";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COMMENT_ID)
    Long id;

    @Column(name = COMMENT_TEXT)
    String text;

    @OneToOne
    @JoinColumn(name = COMMENT_USER_ID)
    User user;

//    @Column(name = COMMENT_PUBLISHED_ON)
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    LocalDateTime publishedOn;
}