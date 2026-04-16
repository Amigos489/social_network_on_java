package new_social_network.Service;

import new_social_network.entity.Post;
import new_social_network.entity.Profile;
import new_social_network.entity.User;
import new_social_network.exception.ProfileNotFoundException;
import new_social_network.repository.ProfileRepository;

import java.time.LocalDate;
import java.util.List;

public class ProfileService {

    private ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile findProfileById(Integer id) {

        Profile profile = profileRepository.findById(id);

        if (profile == null) {
            throw new ProfileNotFoundException(id);
        }

        return profile;
    }

    public Profile save(User user) {
        return profileRepository.createProfile(user);
    }

    public List<Post> addPostInProfileById(Integer id,
                                           Post post) {

        Profile profile = findProfileById(id);

        profile.getPosts().add(post);

        return profile.getPosts();
    }

    public Profile setStatusInProfileById(Integer id,
                                          String status) {
        return profileRepository.setStatusById(id, status);
    }

    public Profile setBirthdayInProfileById(Integer id,
                                            LocalDate birthday) {
        return profileRepository.setBirthdayById(id, birthday);
    }

    public List<Post> getPostOnWallProfileById(Integer id) {
        return profileRepository.getAllPostByProfileId(id);
    }
}
