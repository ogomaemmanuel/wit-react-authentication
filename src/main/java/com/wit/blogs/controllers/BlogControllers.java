package com.wit.blogs.controllers;

import com.wit.blogs.dto.BlogCreateRequestDto;
import com.wit.blogs.dto.BlogUpdateRequestDto;
import com.wit.blogs.entities.Blog;
import com.wit.blogs.services.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/blogs")
@RequiredArgsConstructor
public class BlogControllers {
    private final BlogService blogService;

    @PostMapping
    public ResponseEntity createBlog(@Valid @RequestBody BlogCreateRequestDto createRequestDto) {
        Blog blog = this.blogService.createBlog(createRequestDto);
        return ResponseEntity.created(URI.create(String.format("/api/v1/blogs/$s",blog.getId()))).body(blog);
    }

    @GetMapping
    public ResponseEntity getBlogs(Pageable pageable){
      Page<Blog> blogs=  this.blogService.getBlogs(pageable);
      return ResponseEntity.ok(blogs);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity getBlogsById(@PathVariable Long id ){
      var blog=  this.blogService.getById(id);
      return ResponseEntity.of(blog);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateBlog(@PathVariable Long id, @Valid @RequestBody BlogUpdateRequestDto updateRequestDto) {
        Optional<Blog> blog = this.blogService.updateBlogById(id, updateRequestDto);
        return ResponseEntity.of(blog);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteBlog(@PathVariable Long id) {
         this.blogService.deleteBlogById(id);
        return ResponseEntity.ok("blog deleted");
    }

}
