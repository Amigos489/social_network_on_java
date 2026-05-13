CREATE TABLE IF NOT EXISTS public.user_chat
(
    chats_id INTEGER NOT NULL,
    users_id INTEGER NOT NULL,
    CONSTRAINT user_chat_pkey PRIMARY KEY (chats_id, users_id)
);