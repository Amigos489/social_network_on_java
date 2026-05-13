package social_network.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import social_network.dto.*;
import social_network.entity.*;
import social_network.exception.*;
import social_network.mapper.CommunityMapper;
import social_network.mapper.PostInCommunityMapper;
import social_network.repository.CommunityRepository;
import social_network.repository.PostInCommunityRepository;

import java.util.List;
import java.util.Set;

@Service
public class CommunityService {

    private final String MESSAGE_COMMUNITY_NOT_FOUND = "community with id = %d not found.";

    private final String MESSAGE_USER_NOT_IN_COMMUNITY = "user with id = %d not in community with id = %d.";

    private final String MESSAGE_USER_ALREADY_IN_COMMUNITY = "user with id = %d already in community with id = %d.";

    private final String MESSAGE_USER_NOT_CREATOR_COMMUNITY = "user with id = %d not creator in community with id = %d.";

    private final String MESSAGE_USER_CREATOR_COMMUNITY = "user with id = %d creator in community with id = %d.";

    private final Logger logger = LogManager.getLogger(CommunityService.class);

    private final PostInCommunityMapper postInCommunityMapper;

    private final CommunityMapper communityMapper;

    private final CommunityRepository communityRepository;

    private final PostInCommunityRepository postInCommunityRepository;

    public CommunityService(PostInCommunityMapper postInCommunityMapper,
                            CommunityMapper communityMapper,
                            CommunityRepository communityRepository,
                            PostInCommunityRepository postInCommunityRepository) {

        this.postInCommunityMapper = postInCommunityMapper;

        this.communityMapper = communityMapper;

        this.communityRepository = communityRepository;

        this.postInCommunityRepository = postInCommunityRepository;
    }

    public CommunityInfoDto createCommunity(String name, String description, User creator) {

        logger.info("create community: name = {}, description = {}, creator id = {}.", name, description, creator.getId());

        Community community = new Community(name, description, creator);

        community = communityRepository.create(community);

        logger.info("community created: id = {}, name = {}, description = {}, creator id = {}.",
                community.getId(),
                name,
                description,
                creator.getId());

        return communityMapper.doCommunityInfoDto(community);
    }

    public void deleteCommunity(Community community, User user) {

        logger.info("deleting a community with an id = {} by a user with id = {}.", community.getId(), user.getId());

        if (!communityRepository.isUserCreatorCommunityById(user.getId(), community.getId())) {

            logger.error(String.format(MESSAGE_USER_NOT_CREATOR_COMMUNITY, user.getId(), community.getId()));

            throw new CommunityException(String.format(MESSAGE_USER_NOT_CREATOR_COMMUNITY, user.getId(), community.getId()));
        }

        logger.info("deleting posts from the community with an id = {}.", community.getId());
        postInCommunityRepository.deleteAllByCommunityId(community.getId());

        for (User userFromCommunity : community.getUsers()) {
            userFromCommunity.getCommunities().remove(community);
        }

        communityRepository.delete(community);
        logger.info("community with id = {} deleted by user with id = {}.", community.getId(), user.getId());
    }

    public void userJoiningCommunity(User user, Community community) {

        logger.info("user with id = {} join community with id = {}.", user.getId(), community.getId());

        if (communityRepository.isUserCreatorCommunityById(user.getId(), community.getId())) {

            logger.error(String.format(MESSAGE_USER_CREATOR_COMMUNITY, user.getId(), community.getId()));

            throw new CommunityException(String.format(MESSAGE_USER_CREATOR_COMMUNITY, user.getId(), community.getId()));
        }

        if (communityRepository.isUserInCommunity(user.getId(), community.getId())) {

            logger.error(String.format(MESSAGE_USER_ALREADY_IN_COMMUNITY, user.getId(), community.getId()));

            throw new CommunityException(String.format(MESSAGE_USER_ALREADY_IN_COMMUNITY, user.getId(), community.getId()));
        }

        communityRepository.addUserById(user, community.getId());
        logger.info("user with id = {} joined community with id = {}.", user.getId(), community.getId());
    }

    public void userLeavingCommunity(User user, Community community) {

        logger.info("user with id = {} leave community with id = {}.", user.getId(), community.getId());

        if (!communityRepository.isUserInCommunity(user.getId(), community.getId())) {

            logger.error(String.format(MESSAGE_USER_NOT_IN_COMMUNITY, user.getId(), community.getId()));

            throw new CommunityException(String.format(MESSAGE_USER_NOT_IN_COMMUNITY, user.getId(), community.getId()));
        }

        communityRepository.deleteUserById(user, community);

        logger.info("user with id = {} leaving community with id = {}.", user.getId(), community.getId());
    }

    public void kickUserFromCommunityById(User userForKick, Community community, User userWhoKick) {

        logger.info("kick a user with id = {} from a community with id = {}. The user id who deletes = {}.",
                userForKick.getId(),
                community.getId(),
                userWhoKick.getId());

        if (!communityRepository.isUserCreatorCommunityById(userWhoKick.getId(), community.getId())) {

            logger.error(String.format(MESSAGE_USER_NOT_CREATOR_COMMUNITY, userWhoKick.getId(), userForKick.getId()));

            throw new CommunityException(String.format(MESSAGE_USER_NOT_CREATOR_COMMUNITY, userWhoKick.getId(), userForKick.getId()));
        }

        communityRepository.deleteUserById(userForKick, community);
        logger.info("user with id = {} kicked out of community with id = {}. user id who kicked = {}.",
                userForKick.getId(),
                community.getId(),
                userWhoKick.getId());
    }

    public PostInCommunityDto publishPostInCommunityById(String content, Community community, User author) {

        logger.info("publish post in community with id = {} by user with id = {}.", community.getId(), author.getId());

        if (!communityRepository.isUserInCommunity(author.getId(), community.getId()) &&
        !communityRepository.isUserCreatorCommunityById(author.getId(), community.getId())) {

            logger.error(String.format(MESSAGE_USER_NOT_IN_COMMUNITY, author.getId(), community.getId()));
            throw new CommunityException(String.format(MESSAGE_USER_NOT_IN_COMMUNITY, author.getId(), community.getId()));
        }

        PostInCommunity postInCommunity = new PostInCommunity(content, author, community);

        postInCommunity = postInCommunityRepository.create(postInCommunity);

        logger.info("post with id {} in community with id = {} by user with id = {} published.", postInCommunity.getId(), community.getId(), author.getId());
        return postInCommunityMapper.doPostInCommunityDto(postInCommunity);
    }

    public void deletePostFromCommunity(Integer postId, Community community, User user) {

        logger.info("delete post with id = {} from community with id = {}. the user id who deletes = {}", postId, community.getId(), user.getId());

        if (!communityRepository.isUserCreatorCommunityById(user.getId(), community.getId())) {

            logger.error(String.format(MESSAGE_USER_NOT_CREATOR_COMMUNITY, user.getId(), community.getId()));

            throw new CommunityException(String.format(MESSAGE_USER_NOT_CREATOR_COMMUNITY, user.getId(), community.getId()));
        }

        PostInCommunity postInCommunity = postInCommunityRepository.findById(postId);

        logger.info("post with id = {} from community with id = {} deleted. the user id who deletes = {}", postId, community.getId(), user.getId());
        postInCommunityRepository.delete(postInCommunity);
    }

    public Community findCommunityById(Integer id) {

        logger.info("find community by id = {}", id);

        Community community = communityRepository.findById(id);

        if (community == null) {
            logger.error(String.format(MESSAGE_COMMUNITY_NOT_FOUND, id));
            throw new NotFoundException(String.format(MESSAGE_COMMUNITY_NOT_FOUND, id));
        }

        return community;
    }

    public List<CommunityInfoDto> findCommunitiesUserJoined(User user) {

        logger.info("find communities user with id = {} joined.", user.getId());

        List<Community> communities = communityRepository.findUserJoined(user);

        return communityMapper.doCommunityInfoDtoList(communities);
    }

    public Set<UserDto> findUsersJoinedCommunity(Integer id) {

        logger.info("find users joined community with id = {}.", id);

        Community community = communityRepository.findFetchUsersById(id);

        CommunityDto communityDto = communityMapper.doCommunityDto(community);

        return communityDto.users();
    }

    public CommunityDto getInfoCommunityById(Integer id) {

        logger.info("get info community with id = {}.", id);

        Community community = communityRepository.findAllFetchById(id);

        if (community == null) {

            logger.error(String.format(MESSAGE_COMMUNITY_NOT_FOUND, id));

            throw new NotFoundException(String.format(MESSAGE_COMMUNITY_NOT_FOUND, id));
        }

        return communityMapper.doCommunityDto(community);
    }
}
