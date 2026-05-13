package social_network.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import social_network.entity.Post;
import social_network.exception.NotFoundException;
import social_network.repository.PostRepository;

@Service
public class PostService {

    private static Logger logger = LogManager.getLogger(PostService.class);

    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(String content) {

        logger.info("create post");

        Post post = new Post(content);

        return postRepository.create(post);
    }

    public void deletePostById(Integer id) {

        logger.info(String.format("delete post with id = %d", id));

        Post post = postRepository.findById(id);

        postRepository.delete(post);
    }

    public Post findPostById(Integer id) {

        logger.info("find post with id = %d");

        Post post = postRepository.findById(id);

        if (post == null) {
            logger.error(String.format("post with id = %d not found.", id));
            throw new NotFoundException(String.format("post with id = %d not found.", id));
        }

        logger.info((String.format("post with id = %d found.", id)));
        return post;
    }
}
