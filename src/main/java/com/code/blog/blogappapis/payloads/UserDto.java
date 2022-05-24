package com.code.blog.blogappapis.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotEmpty
    @Size(min = 4, message = "Username must be greater than 4 characters")
    private String name;

    @NotEmpty
    @Size(min = 4, max = 10, message = "Password must be min of 3 chars and max of 10 chars !!")
    private String password;

    @Email(message = "Email address is not valid")
    private String email;

    @NotEmpty
    private String about;

    private Set<RoleDto> roles = new HashSet<>();
}
