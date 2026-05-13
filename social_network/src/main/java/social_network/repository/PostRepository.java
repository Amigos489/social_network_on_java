package social_network.repository;

import org.springframework.stereotype.Repository;
import social_network.entity.Community;
import social_network.entity.Post;
import org.hibernate.Session;

import java.util.List;

@Repository
public class PostRepository extends AbstractRepository<Post, Integer> {

    public PostRepository(Session session) {
        super(Post.class, session);
    }

    public void deletePosts(List<Post> posts) {

        for (Post post : posts) {
            delete(post);
        }
    }
}
