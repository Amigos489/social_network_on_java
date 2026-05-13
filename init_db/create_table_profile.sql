CREATE TABLE IF NOT EXISTS profile
(
    age INTEGER,
    birthday DATE,
    user_id INTEGER NOT NULL,
    status CHARACTER VARYING(100),
    gender CHARACTER VARYING(6),
    CONSTRAINT profile_pkey PRIMARY KEY (user_id)
);