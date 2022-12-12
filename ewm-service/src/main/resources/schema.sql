drop table request cascade;
drop table compilation_of_events cascade;
drop table compilation cascade;
drop table events cascade;
drop table users cascade;
drop table categories cascade;
drop table comments cascade;

CREATE TABLE IF NOT EXISTS users
(
    user_id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_name VARCHAR(256)                            NOT NULL,
    email     VARCHAR(128)                            NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories
(
    category_id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    category_name VARCHAR(128)                            NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (category_id)
);

CREATE TABLE IF NOT EXISTS compilation
(
    compilation_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned         BOOLEAN,
    title          VARCHAR(200)                            NOT NULL,
    CONSTRAINT pk_compilation PRIMARY KEY (compilation_id)
);

CREATE TABLE IF NOT EXISTS events
(
    event_id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    event_annotation   VARCHAR(512)                            NOT NULL,
    event_description  VARCHAR(7000)                           NOT NULL,
    category_id        BIGINT                                  NOT NULL,
    conformed_requests INT                                     NOT NULL,
    participant_limit  INT                                     NOT NULL,
    user_id            BIGINT                                  NOT NULL,
    lat                NUMERIC(8, 5)                           NOT NULL,
    lon                NUMERIC(8, 5)                           NOT NULL,
    created_on         TIMESTAMP                               NOT NULL,
    event_date         TIMESTAMP                               NOT NULL,
    paid               BOOLEAN                                 NOT NULL,
    published_on       TIMESTAMP,
    request_moderation BOOLEAN                                 NOT NULL,
    state              VARCHAR(64)                             NOT NULL,
    title              VARCHAR(512)                            NOT NULL,
    CONSTRAINT pk_event PRIMARY KEY (event_id),
    CONSTRAINT fk_category_id_events FOREIGN KEY (category_id) REFERENCES categories (category_id),
    CONSTRAINT fk_user_id_events FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS comments
(
    comment_id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    comment_text VARCHAR(512)                            NOT NULL,
    user_id      BIGINT                                  NOT NULL,
    event_id     BIGINT                                  NOT NULL,
    created_on TIMESTAMP DEFAULT  now(),
    CONSTRAINT pk_comments PRIMARY KEY (comment_id),
    CONSTRAINT fk_event_id_comment FOREIGN KEY (event_id) REFERENCES events (event_id),
    CONSTRAINT fk_comp_id_comment FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS request
(
    request_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created    TIMESTAMP DEFAULT now(),
    event_id   BIGINT                                  NOT NULL,
    user_id    BIGINT                                  NOT NULL,
    state      VARCHAR(64)                             NOT NULL,
    CONSTRAINT pk_request PRIMARY KEY (request_id),
    CONSTRAINT fk_event_id_request FOREIGN KEY (event_id) REFERENCES events (event_id),
    CONSTRAINT fk_user_id_request FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS compilation_of_events
(
    comp_id  BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    CONSTRAINT fk_event_id_compilation_of_events FOREIGN KEY (event_id) REFERENCES events (event_id),
    CONSTRAINT fk_comp_id_compilation_of_events FOREIGN KEY (comp_id) REFERENCES compilation (compilation_id)
);