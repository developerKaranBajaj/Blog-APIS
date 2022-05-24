package com.code.blog.blogappapis.services.impl;

import com.code.blog.blogappapis.entities.Comment;
import com.code.blog.blogappapis.entities.Post;
import com.code.blog.blogappapis.exceptions.ResourceNotFoundExceptions;
import com.code.blog.blogappapis.payloads.CommentDto;
import com.code.blog.blogappapis.respositories.CommentRepo;
import com.code.blog.blogappapis.respositories.PostRepo;
import com.code.blog.blogappapis.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundExceptions("Post", "Post id", postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundExceptions("Comment", "Comment id", commentId));
        this.commentRepo.delete(comment);
    }
}
