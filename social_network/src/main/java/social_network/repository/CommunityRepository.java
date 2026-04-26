package social_network.repository;

import social_network.entity.Community;
import org.hibernate.Session;
import social_network.entity.User;

import java.util.List;

public class CommunityRepository extends AbstractRepository<Community, Integer> {

    public CommunityRepository(Session session) {
        super(Community.class, session);
    }

    public boolean isUserInCommunity(Integer userId, Integer communityId) {

        final String sql = "SELECT COUNT(*) FROM users_community uc WHERE uc.communities_id = :communityId AND uc.users_id = :userId";

        long countRow = session.createNativeQuery(sql, Long.class)
                .setParameter("communityId", communityId)
                .setParameter("userId", userId)
                .getSingleResultOrNull();

        return countRow == 1;
    }

    public List<User> addUserById(User user, Integer communityId) {

        Community community = findById(communityId);

        List<User> users = community.getUsers();
        users.add(user);

        List<Community> communities = user.getCommunities();
        communities.add(community);

        return users;
    }

    public List<User> deleteUserById(User user, Community community) {

        List<User> users = community.getUsers();
        users.remove(user);

        List<Community> communities = user.getCommunities();
        communities.remove(community);

        return users;
    }

    public List<User> findAllJoinedUsers(Integer communityId) {

        Community community = findById(communityId);

        return community.getUsers();
    }
}
