package com.zust.cch.mapper;

import com.zust.cch.entity.Post;
import com.zust.cch.entity.Post_like;
import com.zust.cch.entity.Comment;
import com.zust.cch.entity.Comment_like;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ForumMapper {

    // 查询 post 表的行数
    @Select("SELECT COUNT(*) FROM post")
    int countPostRows();

    // 查询 comment 表的行数
    @Select("SELECT COUNT(*) FROM comment")
    int countCommentRows();

    // 查询 user 表的行数
    @Select("SELECT COUNT(*) FROM user")  // 假设表名是 user，如果不是根据实际修改
    int countUserRows();

    // 帖子
    @Insert("INSERT INTO post(user_id,title,content) VALUES(#{userId},#{title},#{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertPost(Post post);

    @Select("SELECT * FROM post WHERE id=#{postId}")
    Post selectPostById(Integer postId);

    @Select("SELECT * FROM comment WHERE id=#{commentId}")
    Comment selectCommentById(Integer commentId);

    @Delete("DELETE FROM post WHERE id=#{postId}")
    void deletePost(Integer postId);

    @Delete("DELETE FROM comment WHERE id=#{commentId}")
    void deleteComment(Integer commentId);

    // 查询所有帖子（按创建时间倒序）
    @Select("SELECT * FROM post ORDER BY created_at DESC")
    List<Post> selectPostList();

    // 判断是否已点赞
    @Select("""
        SELECT COUNT(*) 
        FROM post_like 
        WHERE user_id = #{userId} 
        AND post_id = #{postId}
    """)
    int countPostLike(Integer userId, Integer postId);

    // 插入点赞
    @Insert("""
        INSERT INTO post_like(user_id, post_id)
        VALUES(#{userId}, #{postId})
    """)
    int insertPostLike(Integer userId, Integer postId);

    // 删除点赞
    @Delete("""
        DELETE FROM post_like
        WHERE user_id = #{userId}
        AND post_id = #{postId}
    """)
    int deletePostLike(Integer userId, Integer postId);

    // 帖子点赞数 +1
    @Update("""
        UPDATE post
        SET like_count = like_count + 1
        WHERE id = #{postId}
    """)
    int increasePostLikeCount(Integer postId);

    // 帖子点赞数 -1
    @Update("""
        UPDATE post
        SET like_count = like_count - 1
        WHERE id = #{postId}
        AND like_count > 0
    """)
    int decreasePostLikeCount(Integer postId);


    @Update("UPDATE post SET comment_count=comment_count+1 WHERE id=#{postId}")
    void increasePostCommentCount(Integer postId);

    @Update("UPDATE post SET floor=floor+1 WHERE id=#{postId}")
    void increasePostFloor(Integer postId);

    // 帖子评论数 -1
    @Update("""
        UPDATE post
        SET comment_count = comment_count - 1
        WHERE id = #{postId}
        AND comment_count > 0
    """)
    int decreasePostCommentCount(Integer postId);


    // 评论
    @Insert("INSERT INTO comment(post_id,user_id,content,floor) VALUES(#{postId},#{userId},#{content},#{floor})")
    void insertComment(Comment comment);

    // 分页查询指定帖子的评论
    @Select("SELECT * FROM comment WHERE post_id = #{postId} ORDER BY floor ASC LIMIT #{offset}, #{size}")
    List<Comment> selectComments(
            @Param("postId") Integer postId,
            @Param("offset") int offset,
            @Param("size") int size
    );

    // 查询指定帖子楼层的评论ID
    @Select("SELECT id FROM comment WHERE post_id=#{postId} AND floor=#{floor}")
    Integer selectCommentIdByPostIdAndFloor(@Param("postId") Integer postId, @Param("floor") Integer floor);

    // 删除指定楼层评论
    @Delete("DELETE FROM comment WHERE post_id=#{postId} AND floor=#{floor}")
    int deleteCommentByPostIdAndFloor(@Param("postId") Integer postId, @Param("floor") Integer floor);

    // 查询评论是否被用户点赞
    @Select("SELECT COUNT(*) FROM comment_like WHERE user_id=#{userId} AND comment_id=#{commentId}")
    int countCommentLike(@Param("userId") Integer userId, @Param("commentId") Integer commentId);

    // 插入评论点赞
    @Insert("INSERT INTO comment_like(user_id, comment_id) VALUES(#{userId}, #{commentId})")
    int insertCommentLike(@Param("userId") Integer userId, @Param("commentId") Integer commentId);

    // 删除评论点赞
    @Delete("DELETE FROM comment_like WHERE user_id=#{userId} AND comment_id=#{commentId}")
    int deleteCommentLike(@Param("userId") Integer userId, @Param("commentId") Integer commentId);

    // 评论点赞数 +1
    @Update("UPDATE comment SET like_count = like_count + 1 WHERE id=#{commentId}")
    int increaseCommentLikeCount(@Param("commentId") Integer commentId);

    // 评论点赞数 -1
    @Update("UPDATE comment SET like_count = like_count - 1 WHERE id=#{commentId} AND like_count > 0")
    int decreaseCommentLikeCount(@Param("commentId") Integer commentId);

}