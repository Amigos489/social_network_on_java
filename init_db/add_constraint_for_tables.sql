ALTER TABLE IF EXISTS public.community
    ADD CONSTRAINT fk_community_user_creator_id FOREIGN KEY (creator_id)
    REFERENCES "user" (id);


ALTER TABLE IF EXISTS friend_invitation
    ADD CONSTRAINT fk_friend_invitation_user_sender_id FOREIGN KEY (sender_id)
    REFERENCES "user" (id);


ALTER TABLE IF EXISTS friend_invitation
    ADD CONSTRAINT fk_friend_invitation_user_recipient_id FOREIGN KEY (recipient_id)
    REFERENCES "user" (id);


ALTER TABLE IF EXISTS group_chat
    ADD CONSTRAINT fk_group_chat_user_creator_id FOREIGN KEY (creator_id)
    REFERENCES "user" (id);

-- CREATE INDEX IF NOT EXISTS group_chat_creator_id_key ON public.group_chat(creator_id);


ALTER TABLE IF EXISTS message
    ADD CONSTRAINT message_user_sender_id FOREIGN KEY (sender_id)
    REFERENCES "user" (id);

ALTER TABLE IF EXISTS post
    ADD CONSTRAINT post_profile_profile_id FOREIGN KEY (profile_id)
    REFERENCES profile (user_id);


ALTER TABLE IF EXISTS post_in_community
    ADD CONSTRAINT fk_post_in_community_community_community_id FOREIGN KEY (community_id)
    REFERENCES community (id);


ALTER TABLE IF EXISTS post_in_community
    ADD CONSTRAINT fk_post_in_community_user_author_id FOREIGN KEY (author_id)
    REFERENCES "user" (id);


ALTER TABLE IF EXISTS profile
    ADD CONSTRAINT fk_profile_user_user_id FOREIGN KEY (user_id)
    REFERENCES "user" (id);

-- CREATE INDEX IF NOT EXISTS profile_pkey ON public.profile(user_id);


ALTER TABLE IF EXISTS user_chat
    ADD CONSTRAINT fk_user_chat_user_users_id FOREIGN KEY (users_id)
    REFERENCES "user" (id);


ALTER TABLE IF EXISTS user_community
    ADD CONSTRAINT fk_user_community_user_users_id FOREIGN KEY (users_id)
    REFERENCES "user" (id);


ALTER TABLE IF EXISTS user_community
    ADD CONSTRAINT fk_user_community_community_communities_id FOREIGN KEY (communities_id)
    REFERENCES community (id);

ALTER TABLE IF EXISTS personal_chat
    ADD CONSTRAINT fk_personal_chat_user_second_user_id FOREIGN KEY (second_user_id)
    REFERENCES "user" (id);

ALTER TABLE IF EXISTS personal_chat
    ADD CONSTRAINT fk_personal_chat_user_first_user_id FOREIGN KEY (first_user_id)
    REFERENCES "user" (id);
