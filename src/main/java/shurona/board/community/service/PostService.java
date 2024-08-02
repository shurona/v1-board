package shurona.board.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shurona.board.community.domain.Post;
import shurona.board.community.repository.PostRepository;
import shurona.board.user.domain.User;
import shurona.board.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long createPost(String title, String content, Long userId) {
        User writer = this.userRepository.findUserById(userId);
        Post community = Post.createCommunity(title, content, writer);
        return this.postRepository.createPost(community);
    }
}
