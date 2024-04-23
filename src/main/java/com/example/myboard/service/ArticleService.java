package com.example.myboard.service;

import com.example.myboard.dto.ArticleDto;
import com.example.myboard.entity.Article;
import com.example.myboard.repository.ArticleRepository;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<ArticleDto> showAll() {
        List<ArticleDto> dtoList = new ArrayList<>();
        return articleRepository.findAll()
                .stream()
                .map(x -> ArticleDto.fromArticleEntity(x))
                .toList();

    }

//    public List<ArticleDto> findAll() {
//        List<ArticleDto> dtoList = new ArrayList<>();
//        dtoList =  articleRepository.findAll()
//                .stream()
//                .map(x -> ArticleDto.fromArticleEntity(x))
//                .toList();
//        return dtoList;
//    }

    public void insertBoard(ArticleDto articleDto) {
        Article article = articleDto.fromArticleDto(articleDto);
        articleRepository.save(article);
    }

//    public void insertArticle(ArticleDto dto) {
//        Article article = Article.builder()
//                        .title(dto.getTitle())
//                        .content(dto.getContent())
//                        .build();
//
//        articleRepository.save(article);
//    }


    public ArticleDto updateId(Long id) {
        return articleRepository.findById(id)
                .map(x -> ArticleDto.fromArticleEntity(x))
                .orElse(null);
    }

    public void updateBoard(ArticleDto articleDto) {
        Article article = articleDto.fromArticleDto(articleDto);
        articleRepository.save(article);
    }


    public void deleteBoard(Long id) {
        articleRepository.deleteById(id);
    }

    public ArticleDto findById(Long id) {
     return  ArticleDto.from(articleRepository.findById(id).orElse(null));
    }

        public void insertArticle(ArticleDto dto) {
        Article article = Article.builder()
                        .id(dto.getId())
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .build();

        articleRepository.save(article);
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }
}
