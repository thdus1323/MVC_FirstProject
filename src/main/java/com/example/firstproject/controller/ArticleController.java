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

    //상세보기
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

    //목록보기
    @GetMapping("/articles")
    public String index(Model model){

//        1)db에서 모든 article 데이터 가져오기
            List<Article> articleEntityList = articleRepository.findAll();
//        2)가져온 article 묶음을 모델에 등록하기
            model.addAttribute("articleList", articleEntityList);
//        3)사용자에게 보여 줄 뷰 페이지 설정하기
        return "articles/index";
    }

    //수정하기폼
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //1.  수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //2.모델에 넣기
        model.addAttribute("article", articleEntity);
        //3. 뷰 페이지 설정
        return "articles/edit";
    }

    //수정하기
    @PostMapping("/articles/update")
    public String update(ArticleDTO articleDTO){
        log.info(articleDTO.toString());
        //1. dto를 엔티티로 변환하기
        Article articleEntity = articleDTO.toEntity();
        log.info(articleEntity.toString());
        //2, 엔티티를 db에 저장하기
            //2-1.DB에서 기존 데이터 가져오기
            Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
            //2-2. 기존 데이터 값을 갱신하기
            if (target != null){
                articleRepository.save(articleEntity);
            }
        //3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/"+articleEntity.getId();
    }

}
