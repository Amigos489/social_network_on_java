CREATE TABLE IF NOT EXISTS post_in_community
(
    author_id INTEGER,
    community_id integer,
    id SERIAL,
    date_time_publication TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    content CHARACTER VARYING(200) NOT NULL,
    CONSTRAINT post_in_community_pkey PRIMARY KEY (id)
);