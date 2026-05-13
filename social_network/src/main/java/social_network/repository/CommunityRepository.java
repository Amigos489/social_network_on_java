package social_network.repository;

import org.springframework.stereotype.Repository;
import social_network.entity.Community;
import org.hibernate.Session;
import social_network.entity.User;

import java.util.List;
import java.util.Set;

@Repository
public class CommunityRepository extends AbstractRepository<Community, Integer> {

    public CommunityRepository(Session session) {
        super(Community.class, session);
    }

    public boolean isUserInCommunity(Integer userId, Integer communityId) {

        final String sql = "SELECT COUNT(*) FROM user_community uc WHERE uc.communities_id = :communityId AND uc.users_id = :userId";

        long countRow = session.createNativeQuery(sql, Long.class)
                .setParameter("communityId", communityId)
                .setParameter("userId", userId)
                .getSingleResultOrNull();

        return countRow == 1;
    }

    public Set<User> addUserById(User user, Integer communityId) {

        Community community = findById(communityId);

        Set<User> users = community.getUsers();
        users.add(user);

        Set<Community> communities = user.getCommunities();
        communities.add(community);

        return users;
    }

    public Set<User> deleteUserById(User user, Community community) {

        Set<User> users = community.getUsers();
        users.remove(user);

        Set<Community> communities = user.getCommunities();
        communities.remove(community);

        return users;
    }

    public User findCreatorByCommunityId(Integer communityId) {

        Community community = findById(communityId);

        return community.getCreator();
    }

    public boolean isUserCreatorCommunityById(Integer userId, Integer communityId) {

        User user = findCreatorByCommunityId(communityId);

        return user.getId().equals(userId);
    }

    public List<Community> findUserJoined(User user) {
        final String hql = "FROM Community c JOIN c.users u WHERE u = :user";

        return session.createQuery(hql, Community.class).setParameter("user", user).getResultList();
    }

    public Community findFetchUsersById(Integer id) {

        final String hql = "FROM Community c LEFT JOIN FETCH c.users WHERE c.id = :id";

        return session.createQuery(hql, Community.class)
                .setParameter("id", id)
                .getSingleResultOrNull();
    }

    public Community findAllFetchById(Integer id) {

        final String hql = "FROM Community c LEFT JOIN FETCH c.users LEFT JOIN FETCH c.posts WHERE c.id = :id";

        return session.createQuery(hql, Community.class)
                .setParameter("id", id)
                .getSingleResultOrNull();
    }
}
