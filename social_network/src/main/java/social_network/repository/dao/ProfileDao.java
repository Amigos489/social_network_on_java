package social_network.repository.dao;

import org.hibernate.Session;
import social_network.repository.entity.Post;
import social_network.repository.entity.Profile;
import social_network.repository.entity.User;

import java.util.List;

public class ProfileDao {

    private Session session;

    public ProfileDao() {
    }

    public ProfileDao(Session session) {
        this.session = session;
    }

    public Profile createNewProfile(User user) {

        Profile profile = new Profile(user);
        session.persist(profile);

        return profile;
    }

    public Profile findProfileByUser(User user) {

        String hql = "FROM Profile p WHERE p.user = :user";

        return session.createQuery(hql, Profile.class)
                .setParameter("user", user)
                .getSingleResultOrNull();
    }

    public List<Post> addNewPostInProfile(Post post, Profile profile) {

        profile.getPosts().add(post);

        return profile.getPosts();
    }
}
