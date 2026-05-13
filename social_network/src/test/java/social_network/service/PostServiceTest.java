package social_network.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import social_network.entity.Post;
import social_network.exception.NotFoundException;
import social_network.repository.PostRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    PostRepository postRepository;
    @InjectMocks
    PostService postService;

    @Test
    void createPost_shouldSavePostWithContent() {
        when(postRepository.create(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Post result = postService.createPost("hello wall");

        assertEquals("hello wall", result.getContent());
        verify(postRepository).create(any(Post.class));
    }

    @Test
    void findPostById_shouldThrow_whenPostNotFound() {
        when(postRepository.findById(404)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> postService.findPostById(404));
    }
}
