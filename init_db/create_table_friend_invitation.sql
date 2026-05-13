CREATE TABLE IF NOT EXISTS friend_invitation
(
    recipient_id INTEGER NOT NULL,
    sender_id INTEGER NOT NULL,
    friend_invitation_status CHARACTER VARYING(10) NOT NULL,
    CONSTRAINT friend_invitation_pkey PRIMARY KEY (recipient_id, sender_id)
);