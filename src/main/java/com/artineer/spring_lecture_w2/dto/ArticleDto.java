package com.artineer.spring_lecture_w2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ArticleDto {
    @Getter
    public static class Request {
        String title;
        String content;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Res {
        private String id;
        private String title;
        private String content;
    }
}
