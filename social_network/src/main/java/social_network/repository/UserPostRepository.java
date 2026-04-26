package social_network.repository;

import org.hibernate.Session;
import social_network.entity.Post;
import social_network.entity.UserPost;
import social_network.primarykey.UserPostPrimaryKey;

import java.util.List;

public class UserPostRepository extends AbstractRepository<UserPost, UserPostPrimaryKey> {

    public UserPostRepository(Session session) {
        super(UserPost.class, session);
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
