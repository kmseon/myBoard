package com.example.myboard.dto;

import com.example.myboard.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String content;

    public static ArticleDto fromArticleEntity(Article article){
        return new ArticleDto(
                article.getId(),
                article.getTitle(),
                article.getContent()
        );
    }

    public static ArticleDto from(Article article){
        return new ArticleDto(
                article.getId(),
                article.getTitle(),
                article.getContent()
        );
    }

    public Article fromArticleDto(ArticleDto articleDto){
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        return article;
    }
}
