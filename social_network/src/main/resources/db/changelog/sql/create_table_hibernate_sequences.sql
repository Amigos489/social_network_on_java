CREATE TABLE IF NOT EXISTS hibernate_sequences
(
    next_val BIGINT,
    sequence_name CHARACTER VARYING(255) NOT NULL,
    CONSTRAINT hibernate_sequences_pkey PRIMARY KEY (sequence_name)
);