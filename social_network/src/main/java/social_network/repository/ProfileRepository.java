package social_network.repository;

import social_network.entity.Post;
import social_network.entity.Profile;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class ProfileRepository extends AbstractRepository<Profile, Integer> {

    private Session session;

    public ProfileRepository(Session session) {
        super(Profile.class, session);
    }

    public List<Post> getAllPostById(Integer id) {

        Profile profile = findById(id);

        return profile.getPosts();
    }

    public Profile setStatusById(Profile profile, String status) {

        profile.setStatus(status);

        super.update(profile);

        return profile;
    }

    public Profile setBirthdayById(Profile profile, LocalDate birthday) {

        profile.setBirthday(birthday);

        super.update(profile);

        return profile;
    }
}
