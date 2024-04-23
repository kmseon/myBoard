package com.example.myboard.repository;

import com.example.myboard.entity.Article;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;


    @Test
    @Order(value = 4)
    void 자료입력테스트_성공(){
        //Given
        Article expectArticle = new Article(4l, "라라라", "444");
        //When
        Article newArticle = new Article(null, "라라라", "444");
        articleRepository.save(newArticle);
        //Then
        Article acturalArticle = articleRepository.findById(4L).orElse(null);
        //^비교
        assertThat(acturalArticle.toString()).isEqualTo(expectArticle.toString());
    }

    @Test
    @DisplayName("모든자료 검색_성공")
            @Order(value = 3)
//    void findAllTest(){
        void 모든데이터검색_클래스(){
            //Given
            Article article1 = new Article(1l, "가가가","111");
            Article article2 = new Article(2l, "나나나","222");
            Article article3 = new Article(3l, "다다다","333");
            List<Article> expectList = new ArrayList<>();
            expectList.add(article1);
            expectList.add(article2);
            expectList.add(article3);
            //When
            List<Article> resultList = articleRepository.findAll();
            //Then
            assertThat(resultList.toString()).isEqualTo(expectList.toString());
    }

    @Test
    @DisplayName("모든자료 검색_실패")
//    void findAllTest(){
    void 모든데이터검색_클래스_실패(){
        //Given
        Article article1 = new Article(1l, "라라라","111");
        Article article2 = new Article(2l, "나나나","222");
        Article article3 = new Article(3l, "다다다","333");
        List<Article> expectList = new ArrayList<>();
        expectList.add(article1);
        expectList.add(article2);
        expectList.add(article3);
        //When
        List<Article> resultList = articleRepository.findAll();
        //Then
        assertThat(resultList.toString()).isNotEqualTo(expectList.toString());
    }

    @Test
    @Order(value = 5)
    void 전체데이터검색_개수(){
        //Given
        int expectCount = 3;
        //When
        int actualCount = articleRepository.findAll().size();
        //Then
        assertThat(actualCount).isEqualTo(expectCount);
    }

    @Test
    @Order(value=1)
    void 자료삭제_테스트_성공(){
        //Given
        Long deleteId = 4L;
        //When
        articleRepository.deleteById(4L);
        //Then
        Article actualResult = articleRepository.findById(4L).orElse(null);
        assertThat(actualResult).isEqualTo(null);
    }

    @Test
    void 자료수정_테스트(){
        //Given
        Article expectAticle = new Article(1L, "타이틀수정", "컨텐트수정");
        //When
        articleRepository.save(expectAticle);
        Article actualArticle = articleRepository.findById(1L).orElse(null);
        //Then
        assertThat(actualArticle.toString()).isEqualTo(actualArticle.toString());
    }

}