package social_network.Service;

import social_network.entity.*;
import social_network.exception.community.*;
import social_network.primarykey.CreatorCommunityPrimaryKey;
import social_network.primarykey.UserPostPrimaryKey;
import social_network.repository.CommunityRepository;
import social_network.repository.CreatorCommunityRepository;
import social_network.repository.PostRepository;
import social_network.repository.UserPostRepository;

import java.util.List;

public class CommunityService {

    private final CommunityRepository communityRepository;

    private final CreatorCommunityRepository creatorCommunityRepository;

    private final UserPostRepository userPostRepository;

    private final PostRepository postRepository;

    public CommunityService(CommunityRepository communityRepository, CreatorCommunityRepository creatorCommunityRepository,
                            UserPostRepository userPostRepository, PostRepository postRepository) {
        this.communityRepository = communityRepository;
        this.creatorCommunityRepository = creatorCommunityRepository;
        this.userPostRepository = userPostRepository;
        this.postRepository = postRepository;
    }

    public Community createCommunity(String name, String description, User creator) {

        Community community = new Community(name, description);

        communityRepository.create(community);

        CreatorCommunityPrimaryKey primaryKey = new CreatorCommunityPrimaryKey(creator, community);

        CreatorCommunity creatorCommunity = new CreatorCommunity(primaryKey);

        creatorCommunityRepository.create(creatorCommunity);

        return community;
    }

    public void deleteCommunityById(Community community, User user) {

        if (creatorCommunityRepository.isUserCreatorCommunityById(user.getId(), community.getId())) {

            CreatorCommunity creatorCommunity = creatorCommunityRepository.findById(new CreatorCommunityPrimaryKey(user, community));

            creatorCommunityRepository.delete(creatorCommunity);

            List<Post> posts = userPostRepository.findAllPostByCommunityId(community.getId());

            userPostRepository.deleteAllByCommunityId(community.getId());

            postRepository.deletePosts(posts);

            for (User userFromCommunity : community.getUsers()) {
                userFromCommunity.getCommunities().remove(community);
            }

            communityRepository.delete(community);
        }
    }

    public List<User> joinCommunityById(User user, Community community) {

        if (creatorCommunityRepository.isUserCreatorCommunityById(user.getId(), community.getId())) {

            throw new CommunityException("User creator this community");
        }

        if (!communityRepository.isUserInCommunity(user.getId(), community.getId())) {

            return communityRepository.addUserById(user, community.getId());
        }

        throw new UserAlreadyJoinedCommunityException(user.getId(), community.getId());
    }

    public List<User> leaveCommunityById(User user, Community community) {

        if (communityRepository.isUserInCommunity(user.getId(), community.getId())) {

            return communityRepository.deleteUserById(user, community);
        }

        throw new ErrorDeletingException(String.format("The user with id = %d is not in the community with id = %d", user.getId(), community.getId()));
    }

    public List<User> kickUserFromCommunityById(User userForKick, Community community, User userWhoKick) {

        if (creatorCommunityRepository.isUserCreatorCommunityById(userWhoKick.getId(), community.getId())) {

            communityRepository.deleteUserById(userForKick, community);
        }

        throw new ErrorDeletingException(String.format("User with id = %d cannot kick a user with id = %d", userWhoKick.getId(), userForKick.getId()));
    }

    public UserPost publishPostInCommunityById(Post post, Community community, User authorPost) {

        if (!communityRepository.isUserInCommunity(authorPost.getId(), community.getId()) &&
        !creatorCommunityRepository.isUserCreatorCommunityById(authorPost.getId(), community.getId())) {

            throw new ErrorAddingException(String.format("The user with id = %d is not in the community with id = %d", authorPost.getId(), community.getId()));
        }

        UserPostPrimaryKey primaryKey = new UserPostPrimaryKey(authorPost, post, community);

        UserPost userPost = new UserPost(primaryKey);

        return userPostRepository.create(userPost);
    }

    public void deletePostFromCommunity(Post post, Community community, User user) {

        if (creatorCommunityRepository.isUserCreatorCommunityById(user.getId(), community.getId())) {

            UserPost userPost = new UserPost(new UserPostPrimaryKey(user, post, community));

            userPostRepository.delete(userPost);
        }

        throw new ErrorDeletingException(String.format("User with id = %d cannot delete a post in community with id = %d", user.getId(), community.getId()));
    }

    public Community findCommunityById(Integer id) {

        Community community = communityRepository.findById(id);

        if(community == null) {
            throw new CommunityNotFoundException(String.format("Community with id = %d not found.", id));
        }

        return community;
    }
}
