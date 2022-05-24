package com.code.blog.blogappapis.respositories;

import com.code.blog.blogappapis.entities.Category;
import com.code.blog.blogappapis.entities.Post;
import com.code.blog.blogappapis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);

}
