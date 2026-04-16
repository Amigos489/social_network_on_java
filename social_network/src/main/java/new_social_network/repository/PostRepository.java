package new_social_network.repository;

import new_social_network.entity.Post;
import org.hibernate.Session;

public class PostRepository extends AbstractRepository<Post, Integer> {

    private Session session;

    public PostRepository(Session session) {
        super(Post.class, session);
    }

    public Post create(String content) {

        Post post = new Post(content);

        super.create(post);

        return post;
    }
}
