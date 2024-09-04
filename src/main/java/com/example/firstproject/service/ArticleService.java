package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleDTO;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    //post
    public Article create(ArticleDTO articleDTO) {
        Article article = articleDTO.toEntity(); // 엔티티로 변환후 article에 저장
        if (article.getId() != null){
            return null; // 만약 article 아이디 값이 있으면 null을 반환하라-> 수정하지 못하게
        }
        return articleRepository.save(article); // article을 db에 저장
    }

    public Article update(Long id, ArticleDTO articleDTO) {
        //dto -> 수정용 엔티티 생성
        Article article = articleDTO.toEntity();
        log.info("id: {}, article: {}", id, article.toString());
        //db에 대상 엔티티가 있는지 조회하기
        Article target = articleRepository.findById(id).orElse(null);
        //대상 엔티티가 없거나 수정하려는 id가 잘못됐을 때, 처리
        if(target == null || id != article.getId() ){
            log.info("잘못된 요청 ! id : {}, article : {}", id, article.toString());
            return null;
        }
        //대상 엔티티가 있으면, 수정 내용으로 업데이트하고 정상 응답 보내기
        //업데이트 및 정상 응답하기
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        //대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        //잘못된 요청 처리하기
        if (target == null){
            return null;
        }
        //대상 삭제하기
        articleRepository.delete(target);
        return target;
    }


    @Transactional
    public List<Article> createArticles(List<ArticleDTO> articleDTOS) {
        //1. dto 묶음을 엔티티 묶음으로 변환하기_스트림문법
        List<Article> articleList = articleDTOS.stream()
                .map(articleDTO -> articleDTO.toEntity())
                .collect(Collectors.toList());

        //엔티티 묶음을 db에 저장하기
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
        //강제로 에러 발생시키기
        articleRepository.findById(-1L)
                .orElseThrow(()-> new IllegalArgumentException("결제 실패"));
        //결과 값 반환하기
        return articleList;
    }
}
