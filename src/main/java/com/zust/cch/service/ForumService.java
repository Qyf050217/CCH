package com.zust.cch.service;

import com.zust.cch.dto.CreatePostDTO;
import com.zust.cch.dto.CreateCommentDTO;
import com.zust.cch.entity.Post;
import com.zust.cch.entity.Comment;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.util.List;

@Service
public interface ForumService {

    // 发帖
    Post createPost(Integer userId, CreatePostDTO postDTO);

    // 删除帖子
    void deletePost(Integer userId, Integer postId);

    // 帖子点赞
    void likePost(Integer userId, Integer postId);

    // 发评论
    Comment addComment(Integer userId, Integer postId, CreateCommentDTO commentDTO);

    // 删除指定楼层的评论
    void deleteComment(Integer userId, Integer postId, Integer floor);

    // 点赞评论（帖子ID + 楼层）
    void likeComment(Integer userId, Integer postId, Integer floor);


    // 查看帖子详情
    Post getPostById(Integer postId);

    // 查看帖子下的评论列表
    List<Comment> listComments(Integer postId, int page, int size);
}