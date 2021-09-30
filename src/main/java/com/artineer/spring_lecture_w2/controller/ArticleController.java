package com.artineer.spring_lecture_w2.controller;

import com.artineer.spring_lecture_w2.domain.Article;
import com.artineer.spring_lecture_w2.dto.ArticleDto;
import com.artineer.spring_lecture_w2.dto.Response;
import com.artineer.spring_lecture_w2.service.ArticleService;
import com.artineer.spring_lecture_w2.vo.ApiCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/vi/article")
@RestController
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    public Response<Long> post(@RequestBody ArticleDto.Request request) {
        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getTitle())
                .build();

        Long id = articleService.save(article);

        return Response.<Long>builder()
                .code(ApiCode.SUCESS)
                .data(id)
                .build();
    }

    @GetMapping("/{id}")
    public Response<ArticleDto.Res> get(@PathVariable Long id) {
        Article article = articleService.findById(id);

        ArticleDto.Res response = ArticleDto.Res.builder()
                .id(String.valueOf(article.getId()))
                .title(article.getTitle())
                .content(article.getContent())
                .build();

        return Response.<ArticleDto.Res>builder()
                .code(ApiCode.SUCESS)
                .data(response)
                .build();
    }
//    public ArticleController(ArticleService articleService) {
//        this.articleService = articleService;
//    }
}
