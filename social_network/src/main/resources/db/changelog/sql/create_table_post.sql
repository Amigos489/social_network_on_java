CREATE TABLE IF NOT EXISTS post
(
    id SERIAL,
    profile_id INTEGER,
    date_time_publication TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    content CHARACTER VARYING(200),
    CONSTRAINT post_pkey PRIMARY KEY (id)
);