package social_network.repository;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import social_network.entity.Post;
import social_network.entity.AuthorPostInCommunity;
import social_network.primarykey.UserPostPrimaryKey;

import java.util.List;

@Repository
public class UserPostRepository extends AbstractRepository<AuthorPostInCommunity, UserPostPrimaryKey> {

    public UserPostRepository(Session session) {
        super(AuthorPostInCommunity.class, session);
    }

    public List<Post> findAllPostByCommunityId(Integer communityId) {

        final String hql = "FROM Post p JOIN UserPost u_p ON p.id = u_p.primaryKey.post.id WHERE u_p.primaryKey.community.id = :communityId";

        return session.createQuery(hql, Post.class).setParameter("communityId", communityId).list();
    }

    public void deleteAllByCommunityId(Integer communityId) {

        final String hql = "DELETE UserPost u_p WHERE u_p.primaryKey.community.id = :communityId";

        session.createQuery(hql).setParameter("communityId", communityId).executeUpdate();
    }
}
