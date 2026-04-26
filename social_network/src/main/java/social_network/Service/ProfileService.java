package social_network.Service;

import social_network.entity.Post;
import social_network.entity.Profile;
import social_network.entity.User;
import social_network.exception.ProfileNotFoundException;
import social_network.repository.ProfileRepository;

import java.time.LocalDate;
import java.util.List;

public class ProfileService {

    private ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile createProfile(User user) {

        Profile profile = new Profile(user);

        return profileRepository.create(profile);
    }

    public Profile findProfileById(Integer id) {

        Profile profile = profileRepository.findById(id);

        if (profile == null) {
            throw new ProfileNotFoundException(id);
        }

        return profile;
    }

    public List<Post> addPostInProfile(Integer profileId, Post post) {

        Profile profile = findProfileById(profileId);

        profile.getPosts().add(post);

        return profile.getPosts();
    }

    public List<Post> deletePostFromProfileById(Integer profileId, Post post) {

        Profile profile = findProfileById(profileId);

        profile.getPosts().remove(post);

        return profile.getPosts();
    }

    public Profile setStatusProfile(Profile profile,
                                        String status) {

        return profileRepository.setStatusById(profile, status);
    }

    public Profile setBirthdayProfile(Profile profile,
                                      LocalDate birthday) {

        return profileRepository.setBirthdayById(profile, birthday);
    }

    public List<Post> findAllPostsByProfileId(Integer profileId) {
        return profileRepository.getAllPostById(profileId);
    }
}
