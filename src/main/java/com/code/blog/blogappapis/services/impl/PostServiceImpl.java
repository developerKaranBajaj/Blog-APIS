package com.code.blog.blogappapis.services.impl;

import com.code.blog.blogappapis.entities.Category;
import com.code.blog.blogappapis.entities.Post;
import com.code.blog.blogappapis.entities.User;
import com.code.blog.blogappapis.exceptions.ResourceNotFoundExceptions;
import com.code.blog.blogappapis.payloads.PostDto;
import com.code.blog.blogappapis.payloads.PostResponse;
import com.code.blog.blogappapis.respositories.CategoryRepo;
import com.code.blog.blogappapis.respositories.PostRepo;
import com.code.blog.blogappapis.respositories.UserRepo;
import com.code.blog.blogappapis.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundExceptions("User","User id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundExceptions("Category","Category id", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);

        Post updatedPost = this.postRepo.save(post);

        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundExceptions("Post","Post id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        Post updatePost = this.postRepo.save(post);
        return this.modelMapper.map(updatePost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundExceptions("Post","Post id", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

//        Sort sort;
//        if(sortDir.equalsIgnoreCase("asc")){
//            sort = Sort.by(sortBy).ascending();
//        }else{
//            sort = Sort.by(sortBy).descending();
//        }

        Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber,pageSize, sort);

        Page<Post> pagePosts = this.postRepo.findAll(p);
        List<Post> allPosts = pagePosts.getContent();

        List<PostDto> postsDto = allPosts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postsDto);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;

    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundExceptions("Post", "PostId", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        //TODO: Paging
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundExceptions("Category", "Category id:", categoryId));
        List<Post> posts = this.postRepo.findByCategory(cat);
        return posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        //TODO: Paging
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundExceptions("User", "User id:", userId));
        List<Post> posts = this.postRepo.findByUser(user);
        return posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        return posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }
}
