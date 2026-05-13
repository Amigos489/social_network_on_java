package social_network.repository;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import social_network.entity.PostInCommunity;

import java.util.List;

@Repository
public class PostInCommunityRepository extends AbstractRepository<PostInCommunity, Integer> {

    public PostInCommunityRepository(Session session) {
        super(PostInCommunity.class, session);
    }

    public List<PostInCommunity> findAllPostByCommunityId(Integer communityId) {

        final String hql = "FROM PostInCommunity p WHERE p.community.id = :communityId";

        return session.createQuery(hql, PostInCommunity.class).setParameter("communityId", communityId).list();
    }

    public void deleteAllByCommunityId(Integer communityId) {

        final String hql = "DELETE PostInCommunity p WHERE p.community.id = :communityId";

        session.createQuery(hql).setParameter("communityId", communityId).executeUpdate();
    }
}
