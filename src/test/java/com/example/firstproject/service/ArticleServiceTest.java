package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleDTO;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        //1. 예상 데이터
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c));
        //2. 실제 데이터
       List<Article> articles = articleService.index();
        //3. 비교 및 검증
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공_존재하는_id_입력() {
        //1. 예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");

        //2. 실제 데이터
        Article article = articleService.show(id);

        //3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    void show_실패_존재하지_않는_id_입력() {
        //1. 예상 데이터
        Long id = -1L;
        Article expected = null;

        //2. 실제 데이터
        Article article = articleService.show(id);

        //3. 비교 및 검증
        assertEquals(expected, article);
    }

    @Transactional
    @Test
    void create_성공_title과_content만_있는_dto_입력() {
        //1. 예상 데이터
        String title = "라라라라";
        String content = "4444";
        ArticleDTO articleDTO = new ArticleDTO(null, title, content);
        Article expected = new Article(4L, title, content);

        //2. 실제 데이터
        Article article = articleService.create(articleDTO);

        //3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Transactional
    @Test
    void create_실패_id가_포함된_dto_입력() {
        //1. 예상한 데이터
        Long id = 4L;
        String title = "라라라라";
        String content = "4444";
        ArticleDTO articleDTO = new ArticleDTO(id, title, content);
        Article expected = null;

        //2. 실제 데이터
        Article article = articleService.create(articleDTO);

        //3. 비교 및 검증
        assertEquals(expected, article);
    }

    @Transactional
    @Test
    void update_성공1_존재하는_id_title_content_dto() {
        //예상 데이터
        Long id = 1L;
        String title = "가나다라";
        String content = "1234";
        ArticleDTO articleDTO = new ArticleDTO(id, title, content);
        Article expected = new Article(id, title, content);

        //실제 데이터
        Article article = articleService.update(id, articleDTO);

        //비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Transactional
    @Test
    void update_성공2_존재하는_id_title_dto() {
        //예상 데이터
        Long id = 1L;
        String title = "AAAA";
        String content = null;
        ArticleDTO articleDTO = new ArticleDTO(id, title, content);
        Article expected = new Article(1L, "AAAA", "1111");

        //실제 데이터
        Article article = articleService.update(id, articleDTO);
        //비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Transactional
    @Test
    void update_실패_존재하지않는_id_dto() {
        //예상 데이터
        Long id = -1L;
        String title = "가나다라";
        String content = "1234";
        ArticleDTO articleDTO = new ArticleDTO(id, title, content);
        Article expected = null;
        // 실제 데이터
        Article article = articleService.update(id, articleDTO);
        //비교 및 검증
        assertEquals(expected, article);
    }

    @Test
    void delete_성공_존재하는_id_입력() {
        //예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");
        //실제 데이터
        Article article = articleService.delete(id);
        //비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void delete_실패_존재하는_id_입력() {
        //예상 데이터
        Long id = -1L;
        Article expected = null;
        //실제 데이터
        Article article = articleService.delete(id);
        //비교 및 검증
         assertEquals(expected, article);
    }
}