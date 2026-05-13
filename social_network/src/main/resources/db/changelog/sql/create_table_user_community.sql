CREATE TABLE IF NOT EXISTS user_community
(
    communities_id INTEGER NOT NULL,
    users_id INTEGER NOT NULL,
    CONSTRAINT user_community_pkey PRIMARY KEY (communities_id, users_id)
);