package com.code.blog.blogappapis.respositories;

import com.code.blog.blogappapis.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {


}
