CREATE TABLE IF NOT EXISTS message
(
    chat_id INTEGER,
    id SERIAL,
    is_read BOOLEAN NOT NULL,
    sender_id INTEGER,
    date_time_sending TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    text CHARACTER VARYING(150) NOT NULL,
    CONSTRAINT message_pkey PRIMARY KEY (id)
);