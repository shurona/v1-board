package shurona.board.community.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import shurona.board.common.dto.ApiResponse;
import shurona.board.community.dto.CreatePostDto;
import shurona.board.community.service.PostService;
import shurona.board.config.auth.CustomUserDetails;

@RestController
@RequestMapping("post")
public class PostApiController {

    private final PostService postService;

    @Autowired
    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("test")
    public ApiResponse<String> testing() {
        return ApiResponse.success("success", 200);
    }

    @PostMapping
    public ApiResponse<Long> createPost(
            Authentication authentication,
            @RequestBody CreatePostDto createPostDto) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        Long postId = this.postService.createPost(createPostDto.getTitle(), createPostDto.getContent(), userId);
        return ApiResponse.success(postId, 201);
    }

    @PutMapping("update/{id}")
    public ApiResponse<Long> updatePost() {

        return ApiResponse.success(1L, 200);
    }

}
