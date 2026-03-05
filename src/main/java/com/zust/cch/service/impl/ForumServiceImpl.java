package com.zust.cch.service.impl;

import com.zust.cch.dto.CreatePostDTO;
import com.zust.cch.dto.CreateCommentDTO;
import com.zust.cch.entity.Post;
import com.zust.cch.entity.Comment;
import com.zust.cch.exception.BusinessException;
import com.zust.cch.mapper.ForumMapper;
import com.zust.cch.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForumServiceImpl implements ForumService {

    @Autowired
    private ForumMapper forumMapper;

    @Override
    public int getPostRowCount() {
        return forumMapper.countPostRows();
    }

    @Override
    public int getCommentRowCount() {
        return forumMapper.countCommentRows();
    }

    @Override
    public int getUserRowCount() {
        return forumMapper.countUserRows();
    }

    @Override
    public Post createPost(Integer userId, CreatePostDTO postDTO) {
        Post post = new Post();
        post.setUserId(userId);
        post.setTitle(postDTO.title());
        post.setContent(postDTO.content());

        forumMapper.insertPost(post); // 插入数据库，create_time 自动生成
        return post;
    }

//    @Override
//    public void deletePost(Integer userId, Integer postId) {
//        Post post = forumMapper.selectPostById(postId);
//        if (post == null) throw new BusinessException("帖子不存在");
//        if (!post.getUserId().equals(userId)) throw new BusinessException("无权限删除帖子");
//        forumMapper.deletePost(postId);
//    }
    @Override
    @Transactional
    public void deletePost(Integer userId, Integer postId) {
        Post post = forumMapper.selectPostById(postId);
        if (post == null) throw new BusinessException("帖子不存在");
        if (!post.getUserId().equals(userId)) throw new BusinessException("无权限删除帖子");

        // 1. 获取该帖子的所有评论
        List<Comment> comments = forumMapper.selectComments(postId, 0, 1000);

        // 2. 遍历评论并删除
        for (Comment comment : comments) {
            // 通过评论ID删除评论
            forumMapper.deleteComment(comment.getId()); // 删除评论
            // 帖子评论数 -1
            forumMapper.decreasePostCommentCount(postId);
        }

        // 3. 删除帖子
        forumMapper.deletePost(postId);
    }

    @Override
    public void likePost(Integer userId, Integer postId) {

        int count = forumMapper.countPostLike(userId, postId);

        if (count == 0) {
            // 没点赞 → 点赞
            forumMapper.insertPostLike(userId, postId);
            forumMapper.increasePostLikeCount(postId);
        } else {
            // 已点赞 → 取消
            forumMapper.deletePostLike(userId, postId);
            forumMapper.decreasePostLikeCount(postId);
        }
    }

    @Override
    public List<Post> listPost() {
        return forumMapper.selectPostList();
    }

    @Override
    public Comment addComment(Integer userId, Integer postId, CreateCommentDTO commentDTO) {
        Post post = forumMapper.selectPostById(postId);
        if (post == null) throw new BusinessException("帖子不存在");

        int floor = post.getFloor() + 1;

        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setPostId(postId);
        comment.setContent(commentDTO.content());
        comment.setFloor(floor);

        forumMapper.insertComment(comment);
        forumMapper.increasePostCommentCount(postId);
        forumMapper.increasePostFloor(postId);

        return comment;
    }

    @Override
    @Transactional
    public void deleteComment(Integer userId, Integer postId, Integer floor) {
        // 查询帖子，确保存在
        Post post = forumMapper.selectPostById(postId);
        if (post == null) throw new BusinessException("帖子不存在");

        // 查询评论，确保存在且属于当前用户
        Integer commentId = forumMapper.selectCommentIdByPostIdAndFloor(postId, floor);
        if (commentId == null) throw new BusinessException("评论不存在");

        Comment comment = forumMapper.selectCommentById(commentId);
        if (!comment.getUserId().equals(userId)) {
            throw new BusinessException("无权限删除该评论");
        }

        // 删除评论
        forumMapper.deleteComment(commentId);
//        forumMapper.deleteCommentByPostIdAndFloor(postId, floor);

        // 帖子评论数 -1
        forumMapper.decreasePostCommentCount(postId);
    }

    @Override
    @Transactional
    public void likeComment(Integer userId, Integer postId, Integer floor) {
        // 先获取评论ID
        Integer commentId = forumMapper.selectCommentIdByPostIdAndFloor(postId, floor);
        if (commentId == null) throw new BusinessException("评论不存在");

        // 查询是否已点赞
        int count = forumMapper.countCommentLike(userId, commentId);

        if (count == 0) {
            // 没点赞 → 点赞s
            forumMapper.insertCommentLike(userId, commentId);
            forumMapper.increaseCommentLikeCount(commentId);
        } else {
            // 已点赞 → 取消
            forumMapper.deleteCommentLike(userId, commentId);
            forumMapper.decreaseCommentLikeCount(commentId);
        }
    }

    @Override
    public Post getPostById(Integer postId) {
        Post post = forumMapper.selectPostById(postId);
        if (post == null) throw new BusinessException("帖子不存在");
        System.out.println(post.getLikeCount());
        return post;
    }

    @Override
    public List<Comment> listComments(Integer postId, int page, int size) {
        int offset = (page - 1) * size;
        return forumMapper.selectComments(postId, offset, size);
    }
}