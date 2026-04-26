package social_network.repository;

import org.hibernate.Session;

public class AdminRepository{

    private Session session;
    public AdminRepository(Session session) {
        this.session = session;
    }

    public void blockUserById(Integer id) {

        final String hql = "UPDATE User u SET u.isBlocked = true WHERE u.id = :id";

        session.createQuery(hql).executeUpdate();
    }

    public void unblockUserById(Integer id) {

        final String hql = "UPDATE User u SET u.isBlocked = false WHERE u.id = :id";

        session.createQuery(hql).executeUpdate();
    }

    public void deleteCommunityById(Integer id) {

        final String hqlDeleteCommunity = "DELETE Community c WHERE c.id = :id";

        final String hqlDeleteCreatorCommunity = "DELETE CreatorCommunity c_c WHERE c_c.primaryKey.community.id = :id";

        final String hqlDeleteUserPost = "DELETE UserPost u_p WHERE u_p.primaryKey.community.id = :id";

        final String sqlDeleteUserCommunity = "DELETE users_communities u_c WHERE u_c.communities_id = :id";

        session.createQuery(hqlDeleteCommunity).setParameter("id", id).executeUpdate();

        session.createQuery(hqlDeleteCreatorCommunity).setParameter("id", id).executeUpdate();

        session.createQuery(hqlDeleteUserPost).setParameter("id", id).executeUpdate();

        session.createNativeQuery(sqlDeleteUserCommunity).setParameter("id", id).executeUpdate();
    }

    public void deletePostInCommunityById(Integer communityId, Integer postId) {

        final String hql = "DELETE UserPost u_p WHERE u_p.primaryKey.community.id = :communityId AND u_p.primaryKey.post.id = :postId";

        session.createQuery(hql).setParameter("communityId", communityId).setParameter("postId", postId).executeUpdate();
    }
}
