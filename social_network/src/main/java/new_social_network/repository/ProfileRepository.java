package new_social_network.repository;

import new_social_network.entity.Post;
import new_social_network.entity.Profile;
import new_social_network.entity.User;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class ProfileRepository extends AbstractRepository<Profile, Integer> {

    private Session session;

    public ProfileRepository(Session session) {
        super(Profile.class, session);
    }

    public Profile createProfile(User user) {

        Profile profile = new Profile(user);

        super.create(profile);

        return profile;
    }

    public Profile profileFindById(Integer id) {
        return super.findById(id);
    }

    public List<Post> getAllPostByProfileId(Integer id) {

        Profile profile = session.find(Profile.class, id);

        return profile.getPosts();
    }

    public Profile setStatusById(Integer id, String status) {

        Profile profile = super.findById(id);

        profile.setStatus(status);

        super.update(profile);

        return profile;
    }

    public Profile setBirthdayById(Integer id, LocalDate birthday) {

        Profile profile = super.findById(id);

        profile.setBirthday(birthday);

        super.update(profile);

        return profile;
    }
}
