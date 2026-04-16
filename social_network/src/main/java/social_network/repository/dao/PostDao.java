package social_network.repository.dao;

import org.hibernate.Session;
import social_network.repository.entity.Post;

public class PostDao {

    private Session session;
    public PostDao(Session session) {
        this.session = session;
    }

    public Post createNewPost(String content) {

        Post post = new Post(content);

        session.persist(post);

        return post;
    }
}
