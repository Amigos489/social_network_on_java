CREATE TABLE IF NOT EXISTS community
(
    creator_id INTEGER,
    id SERIAL NOT NULL,
    name CHARACTER VARYING(100) NOT NULL,
    description CHARACTER VARYING(200),
    CONSTRAINT community_pkey PRIMARY KEY (id)
);