package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleDTO;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "/articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleDTO articleDTO){
        log.info(articleDTO.toString());
//        System.out.println(articleDTO.toString());
        //1. DTO를 엔티티로 변환
        Article article = articleDTO.toEntity();
        log.info(article.toString());
//        System.out.println(article.toString());
        //2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
//        System.out.println(saved.toString());
        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);
        //1. id 조회해서 db에서 해당 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //2. 가져온 데이터 모델에 등록하기
        model.addAttribute("article", articleEntity);
        //3. 조회한 데이터를 사용자에게 보여주기 위한 뷰 페이지 만들고 반환하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){

//        1)db에서 모든 article 데이터 가져오기
            List<Article> articleEntityList = articleRepository.findAll();
//        2)가져온 article 묶음을 모델에 등록하기
            model.addAttribute("articleList", articleEntityList);
//        3)사용자에게 보여 줄 뷰 페이지 설정하기
        return "articles/index";
    }
}
