package social_network.Service;

import social_network.entity.Community;
import social_network.entity.Post;
import social_network.exception.PostNotFoundException;
import social_network.repository.PostRepository;

public class PostService {

    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(String content) {

        Post post = new Post(content);

        return postRepository.create(post);
    }

    public void deletePostById(Integer id) {

        Post post = postRepository.findById(id);

        postRepository.delete(post);
    }

    public Post findPostById(Integer id) {

        Post post = postRepository.findById(id);

        if (post == null) {
            throw new PostNotFoundException("Post not found.");
        }

        return post;
    }
}
