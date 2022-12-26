package ru.pracricum.ewmservice.user.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static ru.pracricum.ewmservice.user.model.User.TABLE_USERS;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = TABLE_USERS, schema = User.SCHEMA_TABLE)
public class User {

    public static final String TABLE_USERS = "users";
    public static final String SCHEMA_TABLE = "public";
    public static final String USERS_ID = "user_id";
    public static final String USERS_NAME = "user_name";
    public static final String USERS_EMAIL = "email";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = USERS_ID)
    Long id;

    @Column(name = USERS_NAME)
    String name;

    @Column(name = USERS_EMAIL)
    String email;
}
