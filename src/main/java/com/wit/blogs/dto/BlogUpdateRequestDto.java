package com.wit.blogs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogUpdateRequestDto {
    private String content;
    private String title;
}
