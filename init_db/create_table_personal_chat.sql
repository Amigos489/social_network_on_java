CREATE TABLE IF NOT EXISTS personal_chat
(
    first_user_id INTEGER,
    id INTEGER NOT NULL,
    second_user_id INTEGER,
    CONSTRAINT personal_chat_pkey PRIMARY KEY (id)
);