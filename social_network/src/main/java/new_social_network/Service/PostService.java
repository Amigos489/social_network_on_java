package new_social_network.Service;

import new_social_network.entity.Post;
import new_social_network.repository.PostRepository;

public class PostService {

    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createNewPost(String content) {
        return postRepository.create(content);
    }
}
