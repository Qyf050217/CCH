package com.zust.cch.controller;

import com.zust.cch.dto.CreatePostDTO;
import com.zust.cch.dto.CreateCommentDTO;
import com.zust.cch.entity.Post;
import com.zust.cch.entity.Comment;
import com.zust.cch.exception.BusinessException;
import com.zust.cch.service.ForumService;
import com.zust.cch.common.Result;
import com.zust.cch.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    private Integer getCurrentUserId() {
        Integer userId = UserHolder.getUserId();
        if (userId == null) {
            throw new BusinessException("未登录或 token 失效");
        }
        return userId;
    }

    @PostMapping("/post")
    public Result<Post> createPost(@RequestBody CreatePostDTO postDTO) {
        // 从 UserHolder 拿到 userId
        Integer userId = getCurrentUserId();
        return Result.success(forumService.createPost(userId, postDTO));
    }

    @DeleteMapping("/post/{postId}")
    public Result<Void> deletePost(@PathVariable Integer postId) {
        Integer userId = getCurrentUserId();
        forumService.deletePost(userId, postId);
        return Result.success();
    }

    @PostMapping("/post/{postId}/like")
    public Result<Void> likePost(@PathVariable Integer postId) {
        Integer userId = getCurrentUserId();
        forumService.likePost(userId, postId);
        return Result.success();
    }

    @GetMapping("/post/{postId}")
    public Result<Post> getPost(@PathVariable Integer postId) {
        return Result.success(forumService.getPostById(postId));
    }

    @PostMapping("/comment/{postId}")
    public Result<Comment> addComment(@PathVariable Integer postId, @RequestBody CreateCommentDTO commentDTO) {
        Integer userId = UserHolder.getUserId();
        return Result.success(forumService.addComment(userId, postId, commentDTO));
    }

    @PostMapping("/post/{postId}/comments")
    public Result<List<Comment>> listComments(@PathVariable Integer postId,
                                              @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return Result.success(forumService.listComments(postId, page, size));
    }

    @DeleteMapping("/comment/{postId}/{floor}")
    public Result<Void> deleteComment(@PathVariable Integer postId,
                                      @PathVariable Integer floor) {
        Integer userId = UserHolder.getUserId();
        forumService.deleteComment(userId, postId, floor);
        return Result.success();
    }

    @PostMapping("/comment/{postId}/{floor}/like")
    public Result<Void> likeComment(@PathVariable Integer postId, @PathVariable Integer floor) {
        Integer userId = UserHolder.getUserId();
        forumService.likeComment(userId, postId, floor);
        return Result.success();
    }
}