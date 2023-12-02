package com.wit.blogs.controllers;

import com.wit.blogs.annotations.CurrentUserName;
import com.wit.blogs.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    private UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity getLoggedUser(@CurrentUserName String username){
      var userEntity=  this.userRepository.findUserEntityByEmailIgnoreCase(username);
      record UserDetails(String firstName, String lastName){}
      return ResponseEntity.of(  userEntity.map(user->new UserDetails(user.getFirstName(), user.getLastName())));
    }
}
