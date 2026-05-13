package social_network.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import social_network.dto.FindProfileCriteriaDto;
import social_network.dto.ProfileInfoDto;
import social_network.entity.Post;
import social_network.entity.Profile;
import social_network.entity.User;
import social_network.enums.Gender;
import social_network.exception.NotFoundException;
import social_network.mapper.ProfileMapper;
import social_network.repository.ProfileRepository;

import java.util.List;

@Service
public class ProfileService {

    private Logger logger = LogManager.getLogger(ProfileService.class);

    private ProfileMapper profileMapper;

    private ProfileRepository profileRepository;

    public ProfileService(ProfileMapper profileMapper, ProfileRepository profileRepository) {
        this.profileMapper = profileMapper;
        this.profileRepository = profileRepository;
    }

    public Profile createProfile(User user) {

        logger.info((String.format("create profile for user with id = %d.",user.getId())));

        Profile profile = new Profile(user);

        return profileRepository.create(profile);
    }

    public Profile findProfileById(Integer id) {

        logger.info((String.format("find profile with id = %d.", id)));

        Profile profile = profileRepository.findById(id);

        if (profile == null) {
            logger.error((String.format("profile with id = %d not found.",id)));
            throw new NotFoundException(String.format("Profile with id = %d not found.",id));
        }

        logger.info((String.format("profile with id = %d found.", id)));
        return profile;
    }

    public List<Post> addPostInProfile(Integer profileId, Post post) {

        logger.info((String.format("add post in profile with id = %d", profileId)));

        Profile profile = findProfileById(profileId);

        profile.getPosts().add(post);

        logger.info((String.format("post added in profile with id = %d", profileId)));
        return profile.getPosts();
    }

    public List<Post> deletePostFromProfileById(Integer profileId, Post post) {

        logger.info((String.format("delete post from profile with id = %d", profileId)));
        Profile profile = findProfileById(profileId);

        profile.getPosts().remove(post);

        logger.info((String.format("post deleted from profile with id = %d", profileId)));
        return profile.getPosts();
    }

    public void setStatusProfile(Profile profile,
                                        String status) {

        logger.info((String.format("set status in profile with id = %d", profile.getUser().getId())));

        profileRepository.setStatusById(profile, status);
    }

    public void setAgeProfile(Profile profile, Integer age) {

        logger.info((String.format("set age in profile with id = %d", profile.getUser().getId())));

        profileRepository.setAgeById(profile, age);
    }

    public void setGenderProfile(Profile profile, Gender gender) {

        logger.info((String.format("set gender in profile with id = %d", profile.getUser().getId())));

        profileRepository.setGenderById(profile, gender);
    }

    public List<Post> findAllPostsByProfileId(Integer profileId) {

        logger.info((String.format("find all posts from profile with id = %d", profileId)));
        return profileRepository.getAllPostById(profileId);
    }

    public ProfileInfoDto findProfile(Integer id) {
        Profile profile = profileRepository.findById(id);
        if (profile == null) {
            logger.error((String.format("profile with id = %d not found.",id)));
            throw new NotFoundException(String.format("Profile with id = %d not found.",id));
        }
        return profileMapper.toProfileInfoDto(profile);
    }

    public List<ProfileInfoDto> findProfilesByCriteria(FindProfileCriteriaDto findProfileCriteriaDto) {

        List<Profile> profiles = profileRepository.findByCriteria(findProfileCriteriaDto);

        return profileMapper.toProfileInfoDtoList(profiles);
    }
}
