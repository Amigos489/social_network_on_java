package social_network.repository;

import org.hibernate.Session;
import social_network.entity.Community;
import social_network.entity.CreatorCommunity;
import social_network.entity.User;
import social_network.primarykey.CreatorCommunityPrimaryKey;

import java.util.List;

public class CreatorCommunityRepository extends AbstractRepository<CreatorCommunity, CreatorCommunityPrimaryKey> {

    public CreatorCommunityRepository(Session session) {
        super(CreatorCommunity.class, session);
    }

    public User findCreatorByCommunityId(Integer communityId) {

        final String hql = "FROM User u JOIN CreatorCommunity c_c ON u.id = c_c.primaryKey.creator.id WHERE c_c.primaryKey.community.id = :communityId";

        return session.createQuery(hql, User.class)
                .setParameter("communityId", communityId)
                .getSingleResultOrNull();
    }

    public boolean isUserCreatorCommunityById(Integer userId, Integer communityId) {

        User user = findCreatorByCommunityId(communityId);

        return user.getId().equals(userId);
    }

    public List<Community> findCommunitiesWhereUserCreatorById(Integer creatorId) {

        final String hql = "FROM Community c JOIN CreatorCommunity c_c ON c.id = c_c.primaryKey.community.id WHERE c_c.primaryKey.creator.id = :creatorId";

        return session.createQuery(hql, Community.class)
                .setParameter("creatorId", creatorId)
                .list();
    }
}
