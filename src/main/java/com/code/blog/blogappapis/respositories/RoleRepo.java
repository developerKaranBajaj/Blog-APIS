package com.code.blog.blogappapis.respositories;

import com.code.blog.blogappapis.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
}
