package com.example.cms.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostDTO {

    private Long id;
    private String title;
    private String subtitle;
    private String content;
    private String imageUrl;

    public PostDTO() {
    }

    public PostDTO(Long id, String title, String subtitle, String content, String imageUrl) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.imageUrl = imageUrl;
    }

}