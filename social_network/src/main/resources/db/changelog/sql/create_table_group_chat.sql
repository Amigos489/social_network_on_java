CREATE TABLE IF NOT EXISTS group_chat
(
    creator_id INTEGER,
    id integer NOT NULL,
    CONSTRAINT group_chat_pkey PRIMARY KEY (id),
    CONSTRAINT group_chat_creator_id_key UNIQUE (creator_id)
);