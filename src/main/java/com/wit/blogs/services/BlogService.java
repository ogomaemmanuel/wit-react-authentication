package com.wit.blogs.services;

import com.wit.blogs.dto.BlogCreateRequestDto;
import com.wit.blogs.dto.BlogUpdateRequestDto;
import com.wit.blogs.entities.Blog;
import com.wit.blogs.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public Blog createBlog(BlogCreateRequestDto createRequestDto) {
        Blog blog = new Blog();
        blog.setTitle(createRequestDto.getTitle());
        blog.setContent(createRequestDto.getContent());
        return this.blogRepository.save(blog);
    }

    public Page<Blog> getBlogs(Pageable pageable) {
        return this.blogRepository.findAll(pageable);
    }

    public Optional<Blog> getById(Long id) {
        return this.blogRepository.findById(id);
    }

    public Optional<Blog> updateBlogById(Long id, BlogUpdateRequestDto updateRequestDto) {
        return this.blogRepository.findById(id).map(blog -> {
            blog.setTitle(updateRequestDto.getTitle());
            blog.setContent(updateRequestDto.getContent());
            return this.blogRepository.save(blog);
        });
    }

    public void deleteBlogById(Long id) {
        this.blogRepository.deleteById(id);
    }
}
