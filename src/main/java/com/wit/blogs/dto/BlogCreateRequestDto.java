package com.wit.blogs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogCreateRequestDto {
    private String content;
    private String title;
}
